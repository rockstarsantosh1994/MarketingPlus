package com.soulsoft.marketingplus.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import com.soulsoft.marketingplus.R;
import com.soulsoft.marketingplus.model.advertisment.AdvertisementBO;

import java.util.ArrayList;

public class DACustomHeaderAdapter extends PagerAdapter {

    private final int widthPixels;
    private final float scale;
    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<AdvertisementBO> advertisements;
    public static final String TAG="CustomPagerAdapter";
    int flag = 0;

    public DACustomHeaderAdapter(Context mContext, ArrayList<AdvertisementBO> advertisements) {
        this.mContext = mContext;
        this.advertisements = advertisements;
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        widthPixels = displayMetrics.widthPixels;
        scale = displayMetrics.density;
    }

    @Override
    public int getCount() {
        return advertisements.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.viewpager_dashboard_header, container, false);
        ImageView iv_banner = itemView.findViewById(R.id.iv_banner_image);
        TextView tvBannerTitle = itemView.findViewById(R.id.tv_banner_title);
       // TextView tvArticleTitle=itemView.findViewById(R.id.tv_article_title);
        TextView tvArticleDescription=itemView.findViewById(R.id.tv_article_description);



        AdvertisementBO advertisement = advertisements.get(position);

       /* if (advertisement.getImageurl() != null && !TextUtils.isEmpty(advertisement.getImageurl())) {
            Glide.with(mContext).load(advertisement.getImageurl()).into(iv_banner);
        }*/
        tvBannerTitle.setText(advertisement.getAdtitle());
        //tvArticleTitle.setText(advertisement.getArticletitle());
        tvArticleDescription.setText(advertisement.getAddesc());

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
