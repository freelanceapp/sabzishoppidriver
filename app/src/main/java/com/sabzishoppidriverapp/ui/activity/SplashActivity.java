package com.sabzishoppidriverapp.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.sabzishoppidriverapp.R;
import com.sabzishoppidriverapp.constant.Constant;
import com.sabzishoppidriverapp.model.User;
import com.sabzishoppidriverapp.model.login_responce.LoginModel;
import com.sabzishoppidriverapp.model.otp_responce.OtpModel;
import com.sabzishoppidriverapp.utils.AppPreference;
import com.sabzishoppidriverapp.utils.BaseActivity;
import com.sabzishoppidriverapp.utils.ConnectionDirector;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        cd = new ConnectionDirector(mContext);

        if (cd.isNetWorkAvailable()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (AppPreference.getBooleanPreference(mContext , Constant.Is_Login)) {
                        Gson gson = new Gson();
                        String userData = AppPreference.getStringPreference(mContext, Constant.User_Data);
                        OtpModel loginModal = gson.fromJson(userData, OtpModel.class);
                        User.setUser(loginModal);

                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }
            }, 2000);
        } else {
            cd.show(mContext);
        }
    }
}
