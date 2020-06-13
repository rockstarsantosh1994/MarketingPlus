package com.soulsoft.marketingplus;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.soulsoft.marketingplus.model.LoginResponse;
import com.soulsoft.marketingplus.services.ApiRequestHelper;
import com.soulsoft.marketingplus.services.MarketingPlus;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.et_email)
    TextInputEditText etEmail;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    private String TAG = "LoginActivity";
    private MarketingPlus marketingPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        marketingPlus=(MarketingPlus) getApplication();

        btnLogin.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_login){
            if(isValidated()){
                authenticateUser();
            }
        }
    }

    private void authenticateUser(){
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Logging in....");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        Map<String, String> params = new HashMap<>();
        params.put("usrname", etEmail.getText().toString());
        params.put("passwrd",etPassword.getText().toString());

        marketingPlus.getApiRequestHelper().login(params, new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                LoginResponse loginResponse = (LoginResponse) object;
                Log.e(TAG, "onSuccess: " + loginResponse.getResponsecode());
                Log.e(TAG, "onSuccess: " + loginResponse.getMessage());

                if (loginResponse.getResponsecode() == 200) {

                    progress.dismiss();
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    CommonMethods.setPreference(LoginActivity.this, AllKeys.USERID, String.valueOf(loginResponse.getData().getAdminid()));
                    CommonMethods.setPreference(LoginActivity.this, AllKeys.FNAME, loginResponse.getData().getFname());
                    CommonMethods.setPreference(LoginActivity.this, AllKeys.LNAME, loginResponse.getData().getLname());
                    CommonMethods.setPreference(LoginActivity.this, AllKeys.EMAILID, loginResponse.getData().getEmailid());

                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
                    finish();

                } else {
                    progress.dismiss();
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(LoginActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private Boolean isValidated() {

        if (Objects.requireNonNull(etEmail.getText()).toString().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Email Required!", Toast.LENGTH_SHORT).show();
            etEmail.setFocusable(true);
            return false;
        }

        if (Objects.requireNonNull(etPassword.getText()).toString().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Password Required!", Toast.LENGTH_SHORT).show();
            etPassword.setFocusable(true);
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            Toast.makeText(LoginActivity.this, "Invalid Email!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}