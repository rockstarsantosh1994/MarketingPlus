package com.soulsoft.marketingplus.services;


import com.soulsoft.marketingplus.model.LoginResponse;
import com.soulsoft.marketingplus.model.advertisment.AdvertismentResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MarketingPlusServices {

    @FormUrlEncoded
    @POST("authenticate.php")
    Call<LoginResponse> login(@FieldMap Map<String, String> params);

    @GET("getadds.php")
    Call<AdvertismentResponse> advertisement();

}
