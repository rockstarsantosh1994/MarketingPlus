package com.soulsoft.marketingplus.fragment;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.soulsoft.marketingplus.R;
import com.soulsoft.marketingplus.adapters.ConsumerAdapter;
import com.soulsoft.marketingplus.model.consumer.ConsumerBO;
import com.soulsoft.marketingplus.model.consumer.ConsumerResponse;
import com.soulsoft.marketingplus.services.ApiRequestHelper;
import com.soulsoft.marketingplus.services.MarketingPlus;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ConsumerFragment extends Fragment {

    @BindView(R.id.rv_consumer)
    RecyclerView rvConsumer;
    private MarketingPlus marketingPlus;
    private static ArrayList<ConsumerBO> consumerBOArrayList=new ArrayList<>();
    private String TAG="ConsumerFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_consumer, container, false);
        ButterKnife.bind(this,view);
        marketingPlus=(MarketingPlus) requireActivity().getApplication();

        //set Layout manager..
        rvConsumer.setLayoutManager(new LinearLayoutManager(getContext()));

        //load consumer data()....
        if(consumerBOArrayList.size()==0){
            loadConsumerData();
        }else{
            setData();
        }

        return view;
    }

    private void loadConsumerData(){
        final ProgressDialog progress = new ProgressDialog(getContext());
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);
        marketingPlus.getApiRequestHelper().getConsumerData(new ApiRequestHelper.OnRequestComplete() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccess(Object object) {
                progress.dismiss();
                ConsumerResponse consumerResponse = (ConsumerResponse) object;
                Log.e(TAG, "onSuccess: " + consumerResponse.getResponsecode());
                Log.e(TAG, "onSuccess: " + consumerResponse.getMessage());
                if (consumerResponse.getResponsecode() == 200) {

                    consumerBOArrayList=consumerResponse.getData();

                    //set Data to pagers
                    setData();
                } else {
                    Toast.makeText(getContext(), consumerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(getContext(), apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(){
        if(consumerBOArrayList.size()>0){
            ConsumerAdapter consumerAdapter=new ConsumerAdapter(getContext(),consumerBOArrayList);
            rvConsumer.setAdapter(consumerAdapter);
        }
    }
}