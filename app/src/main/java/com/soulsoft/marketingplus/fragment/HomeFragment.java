package com.soulsoft.marketingplus.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.Toast;
import com.soulsoft.marketingplus.R;
import com.soulsoft.marketingplus.adapters.DACustomHeaderAdapter;
import com.soulsoft.marketingplus.model.advertisment.AdvertisementBO;
import com.soulsoft.marketingplus.model.advertisment.AdvertismentResponse;
import com.soulsoft.marketingplus.services.ApiRequestHelper;
import com.soulsoft.marketingplus.services.MarketingPlus;
import com.soulsoft.marketingplus.widgets.LoopViewPager;
import java.lang.reflect.Field;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    private MarketingPlus marketingPlus;
    private static ArrayList<AdvertisementBO> advertisementBOArrayList=new ArrayList<>();
    private String TAG="HomeFragment";
    @BindView(R.id.header_viewpager)
    LoopViewPager headerViewPager;
    @BindView(R.id.ll_header_ads)
    LinearLayout llHeaderAds;
    private static final int MESSAGE_SCROLL = 123;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        marketingPlus=(MarketingPlus) requireActivity().getApplication();

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);

        //load advertisement data....
        if (advertisementBOArrayList.size() == 0) {
            loadAdvertisementData();
        }else{
          setData();
        }

        return view;
    }

    private void loadAdvertisementData() {
        final ProgressDialog progress = new ProgressDialog(getContext());
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);
        marketingPlus.getApiRequestHelper().advertisment(new ApiRequestHelper.OnRequestComplete() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccess(Object object) {
                progress.dismiss();
                AdvertismentResponse advertisementResponse = (AdvertismentResponse) object;
                Log.e(TAG, "onSuccess: " + advertisementResponse.getResponsecode());
                Log.e(TAG, "onSuccess: " + advertisementResponse.getMessage());
                if (advertisementResponse.getResponsecode() == 200) {

                    advertisementBOArrayList=advertisementResponse.getData();

                    //set Data to pagers
                   setData();
                } else {
                    Toast.makeText(getContext(), advertisementResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(getContext(), apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setData(){
        //header ads running....
        if (advertisementBOArrayList.size() > 0) {
            llHeaderAds.setVisibility(View.VISIBLE);
            DACustomHeaderAdapter daCustomHeaderAdapter = new DACustomHeaderAdapter(getContext(), advertisementBOArrayList);
            headerViewPager.setAdapter(daCustomHeaderAdapter);
        } else {
            llHeaderAds.setVisibility(View.GONE);
        }

        if (advertisementBOArrayList.size() > 1) {
            set_slider_animation_header();
        } else {
            try {
                stopAutoPlay();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("ClickableViewAccessibility")
    private void set_slider_animation_header() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            int animationDuration = 1500;
            FixedSpeedScroller scroller = new FixedSpeedScroller(headerViewPager.getContext(), new AccelerateDecelerateInterpolator(), animationDuration);
            mScroller.set(headerViewPager, scroller);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if (headerViewPager != null && headerViewPager.getAdapter() != null && headerViewPager.getAdapter().getCount() > 1)
            startAutoPlay();
        else
            stopAutoPlay();

        headerViewPager.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    stopAutoPlay();
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (headerViewPager != null && headerViewPager.getAdapter() != null && headerViewPager.getAdapter().getCount() > 1)
                        startAutoPlay();
                    else
                        stopAutoPlay();
                    break;

            }
            return false;
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_SCROLL) {
                if (headerViewPager != null) {
                    headerViewPager.setCurrentItem(headerViewPager.getCurrentItem() + 1);
                    startAutoPlay();
                }
            }
        }
    };

    public void stopAutoPlay() {
        handler.removeMessages(MESSAGE_SCROLL);
    }

    public void startAutoPlay() {
        stopAutoPlay();
        int homeColumnScrollInterval = 4;
        handler.sendEmptyMessageDelayed(MESSAGE_SCROLL, homeColumnScrollInterval * 1000);
    }

    public static class FixedSpeedScroller extends Scroller {
        int duration;

        FixedSpeedScroller(Context context, Interpolator interpolator, int duration) {
            super(context, interpolator);
            this.duration = duration;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, this.duration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, this.duration);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}