package com.sabzishoppidriverapp.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sabzishoppidriverapp.R;
import com.sabzishoppidriverapp.constant.Constant;
import com.sabzishoppidriverapp.model.login_responce.LoginModel;
import com.sabzishoppidriverapp.retrofit_provider.RetrofitService;
import com.sabzishoppidriverapp.retrofit_provider.WebResponse;
import com.sabzishoppidriverapp.utils.Alerts;
import com.sabzishoppidriverapp.utils.AppPreference;
import com.sabzishoppidriverapp.utils.BaseActivity;

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

    private void init()
    {
        btn_login = findViewById(R.id.btn_login);
        et_login_phone = findViewById(R.id.et_login_phone);

        btn_login.setOnClickListener(this);
        strMobile = et_login_phone.getText().toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_login :
                if (!strMobile.isEmpty()) {
                    otpApi();
                }else {
                    Alerts.show(mContext,"Enter Mobile Number");
                }
                break;
        }
    }


     private void otpApi() {
        if (cd.isNetWorkAvailable()) {
            //strMobile = ((EditText) rootview.findViewById(R.id.et_login_email)).getText().toString();

                RetrofitService.getLoginData(new Dialog(mContext), retrofitApiClient.loginData(strMobile), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        LoginModel loginModel = (LoginModel) result.body();

                        if (!loginModel.getError())
                        {
                            Alerts.show(mContext, loginModel.getMessage());

                            AppPreference.setBooleanPreference(mContext, Constant.LOGIN_API , true);
                            //AppPreference.setStringPreference(mContext, Constant.User_Id , loginModel.getUser().getId());
                            Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                            intent.putExtra("Mobile_Number", strMobile);
                            mContext.startActivity(intent);
                        }
                    }

                    @Override
                    public void onResponseFailed(String error) {
                        Alerts.show(mContext, error);
                    }
                });

        }else {
            cd.show(mContext);
        }
    }
}
