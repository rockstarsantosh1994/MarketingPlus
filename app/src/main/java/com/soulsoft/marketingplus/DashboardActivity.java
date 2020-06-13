package com.soulsoft.marketingplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.soulsoft.marketingplus.fragment.ConsumerFragment;
import com.soulsoft.marketingplus.fragment.ContactUsFragment;
import com.soulsoft.marketingplus.fragment.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.ibrahimsn.lib.SmoothBottomBar;

public class DashboardActivity extends AppCompatActivity {

    @BindView(R.id.bottomBar)
    SmoothBottomBar smoothBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        //load default fragment...
        HomeFragment homeFragment=new HomeFragment();
        loadFragment(homeFragment);

        smoothBottomBar.setItemActiveIndex(1);

        smoothBottomBar.setOnItemSelectedListener(i -> {
            switch (i){
                case 0:
                    ConsumerFragment consumerFragment=new ConsumerFragment();
                    loadFragment(consumerFragment);
                    break;

                case 1:
                    HomeFragment homeFragment1=new HomeFragment();
                    loadFragment(homeFragment1);
                    break;

                case 2:
                    ContactUsFragment contactUsFragment=new ContactUsFragment();
                    loadFragment(contactUsFragment);
                    break;
            }
            return false;
        });
    }

    public void loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }
}