package com.soulsoft.marketingplus.services;

import android.text.Html;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.soulsoft.marketingplus.AllKeys;
import com.soulsoft.marketingplus.model.LoginResponse;
import com.soulsoft.marketingplus.model.advertisment.AdvertismentResponse;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequestHelper {

    public interface OnRequestComplete {
        void onSuccess(Object object);

        void onFailure(String apiResponse);
    }

    private static ApiRequestHelper instance;
    private MarketingPlus application;
    private MarketingPlusServices marketingPlusServices;
    static Gson gson;

    public static synchronized ApiRequestHelper init(MarketingPlus application) {
        if (null == instance) {
            instance = new ApiRequestHelper();
            instance.setApplication(application);
            gson = new GsonBuilder()
                    .setLenient()
                    .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
//                    .setExclusionStrategies(new ExclusionStrategy() {
//                        @Override
//                        public boolean shouldSkipField(FieldAttributes f) {
//                            return f.getDeclaringClass().equals(RealmObject.class);
//                        }
//
//                        @Override
//                        public boolean shouldSkipClass(Class<?> clazz) {
//                            return false;
//                        }
//                    })
                    .create();
            instance.createRestAdapter();
        }
        return instance;
    }


    public void login(Map<String, String> params, final OnRequestComplete onRequestComplete) {
        Call<LoginResponse> call = marketingPlusServices.login(params);
        login_api(onRequestComplete, call);
    }

    public void advertisment( final OnRequestComplete onRequestComplete) {
        Call<AdvertismentResponse> call = marketingPlusServices.advertisement();
        advertisment_api(onRequestComplete, call);
    }

    private void advertisment_api(final OnRequestComplete onRequestComplete, Call<AdvertismentResponse> call) {
        call.enqueue(new Callback<AdvertismentResponse>() {
            @Override
            public void onResponse(Call<AdvertismentResponse> call, Response<AdvertismentResponse> response) {
                if (response.isSuccessful()) {
                    onRequestComplete.onSuccess(response.body());
                } else {
                    try {
                        onRequestComplete.onFailure(Html.fromHtml(response.errorBody().string()) + "");
                    } catch (IOException e) {
                        onRequestComplete.onFailure("Unproper Response");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AdvertismentResponse> call, Throwable t) {
                handle_fail_response(t, onRequestComplete);
            }
        });
    }

    private void login_api(final OnRequestComplete onRequestComplete, Call<LoginResponse> call) {
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    onRequestComplete.onSuccess(response.body());
                } else {
                    try {
                        onRequestComplete.onFailure(Html.fromHtml(response.errorBody().string()) + "");
                    } catch (IOException e) {
                        onRequestComplete.onFailure("Unproper Response");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                handle_fail_response(t, onRequestComplete);
            }
        });
    }



    private void handle_fail_response(Throwable t, OnRequestComplete onRequestComplete) {
        if (t.getMessage() != null) {
            if (t.getMessage().contains("Unable to resolve host")) {
                onRequestComplete.onFailure(AllKeys.NO_INTERNET_AVAILABLE);
            } else {
                onRequestComplete.onFailure(Html.fromHtml(t.getLocalizedMessage()) + "");
            }
        } else
            onRequestComplete.onFailure(AllKeys.UNPROPER_RESPONSE);
    }

    private static class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringAdapter();
        }
    }

    public static class StringAdapter extends TypeAdapter<String> {
        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }

        public void write(JsonWriter writer, String value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }

    /**
     * REST Adapter Configuration
     */
    private void createRestAdapter() {
        //        ObjectMapper objectMapper = new ObjectMapper();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.interceptors().add(logging);
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(AllKeys.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = builder.client(httpClient.build()).build();
        marketingPlusServices = retrofit.create(MarketingPlusServices.class);
    }

    /**
     * End REST Adapter Configuration
     */


    public MarketingPlus getApplication() {
        return application;
    }

    public void setApplication(MarketingPlus application) {
        this.application = application;
    }

}
