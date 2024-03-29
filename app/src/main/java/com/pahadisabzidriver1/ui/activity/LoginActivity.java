package com.pahadisabzidriver1.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pahadisabzidriver1.R;
import com.pahadisabzidriver1.model.login_responce.LoginModel;
import com.pahadisabzidriver1.retrofit_provider.RetrofitService;
import com.pahadisabzidriver1.retrofit_provider.WebResponse;
import com.pahadisabzidriver1.utils.Alerts;
import com.pahadisabzidriver1.utils.BaseActivity;

import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_login;
    private EditText et_login_phone;
    private String strMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        btn_login = findViewById(R.id.btn_login);
        et_login_phone = findViewById(R.id.et_login_phone);

        btn_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_login:
                strMobile = et_login_phone.getText().toString();
                if (strMobile.length() != 10) {
                    otpApi();
                } else {
                    Alerts.show(mContext, "Enter Mobile Number");
                }
                break;
        }
    }

    private void otpApi() {
        if (cd.isNetWorkAvailable()) {
            RetrofitService.getLoginData(new Dialog(mContext), retrofitApiClient.loginData(strMobile), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    LoginModel loginModel = (LoginModel) result.body();
                    if (!loginModel.getError()) {
                        Alerts.show(mContext, loginModel.getMessage());
                        Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                        intent.putExtra("Mobile_Number", strMobile);
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
