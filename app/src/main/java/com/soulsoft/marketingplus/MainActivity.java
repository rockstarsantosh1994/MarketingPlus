package com.soulsoft.marketingplus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int SPLASH_DISPLAY_DURATION = 2000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                if (CommonMethods.getPrefrence(MainActivity.this, AllKeys.USERID).equals(AllKeys.DNF)) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    MainActivity.this.startActivity(intent);
                    MainActivity.this.overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
                    MainActivity.this.finish();
                } else {
                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    MainActivity.this.startActivity(intent);
                    MainActivity.this.overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
                    MainActivity.this.finish();
                }

            }
        }, SPLASH_DISPLAY_DURATION);

    }
}