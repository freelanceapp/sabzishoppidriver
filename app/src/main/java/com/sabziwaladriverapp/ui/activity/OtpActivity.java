package com.sabziwaladriverapp.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sabziwaladriverapp.R;
import com.sabziwaladriverapp.constant.Constant;
import com.sabziwaladriverapp.model.User;
import com.sabziwaladriverapp.model.otp_responce.OtpModel;
import com.sabziwaladriverapp.retrofit_provider.RetrofitService;
import com.sabziwaladriverapp.retrofit_provider.WebResponse;
import com.sabziwaladriverapp.utils.Alerts;
import com.sabziwaladriverapp.utils.AppPreference;
import com.sabziwaladriverapp.utils.BaseActivity;
import com.sabziwaladriverapp.utils.ConnectionDirector;
import com.sabziwaladriverapp.utils.pinview.Pinview;

import retrofit2.Response;

public class OtpActivity extends BaseActivity implements View.OnClickListener {
    private View rootview;
    private Button btn_fplogin;
    private TextView otpTime;
    private LinearLayout resendLayout;
    private Pinview pinview1;
    private String strMobile, strOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        init();
    }

    private void init() {
        mContext = this;
        cd = new ConnectionDirector(mContext);
        retrofitApiClient = RetrofitService.getRetrofit();
        strMobile = getIntent().getStringExtra("Mobile_Number");

        ((Button) findViewById(R.id.btn_fplogin)).setOnClickListener(this);
        btn_fplogin = findViewById(R.id.btn_fplogin);
        pinview1 = findViewById(R.id.pinview1);
        otpTime = (TextView) findViewById(R.id.otpTime);
        resendLayout = (LinearLayout) findViewById(R.id.resendLayout);
        btn_fplogin.setOnClickListener(this);

        otptime();
    }


    private void otptime() {
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                otpTime.setVisibility(View.VISIBLE);
                otpTime.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                otpTime.setVisibility(View.GONE);
                resendLayout.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_fplogin:
                otpApi();
                //startFragment(Constant.SignUpFragment,new SignUpFragment());
                break;
        }
    }

    private void otpApi() {
        if (cd.isNetWorkAvailable()) {
            strOtp = pinview1.getValue();

            RetrofitService.getOtpData(new Dialog(mContext), retrofitApiClient.otpApi(strMobile, strOtp), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    OtpModel loginModel = (OtpModel) result.body();

                    if (!loginModel.getError()) {
                        Alerts.show(mContext, loginModel.getMessage());

                        AppPreference.setBooleanPreference(mContext, Constant.Is_Login, true);
                        AppPreference.setStringPreference(mContext, Constant.User_Id, loginModel.getDriver().getDriverId());

                        Gson gson = new GsonBuilder().setLenient().create();
                        String data = gson.toJson(loginModel);
                        AppPreference.setStringPreference(mContext, Constant.User_Data, data);
                        User.setUser(loginModel);
                        //Intent intent = new Intent(mContext, MapsActivity.class);
                        Intent intent = new Intent(mContext, DeliveryListActivity.class);
                        mContext.startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });

        } else {
            cd.show(mContext);
        }
    }
}