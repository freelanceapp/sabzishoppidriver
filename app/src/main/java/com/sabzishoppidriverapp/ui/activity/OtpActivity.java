package com.sabzishoppidriverapp.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sabzishoppidriverapp.R;
import com.sabzishoppidriverapp.constant.Constant;
import com.sabzishoppidriverapp.model.User;
import com.sabzishoppidriverapp.model.otp_responce.OtpModel;
import com.sabzishoppidriverapp.retrofit_provider.RetrofitService;
import com.sabzishoppidriverapp.retrofit_provider.WebResponse;
import com.sabzishoppidriverapp.ui.map_activity.MapsActivity;
import com.sabzishoppidriverapp.utils.Alerts;
import com.sabzishoppidriverapp.utils.AppPreference;
import com.sabzishoppidriverapp.utils.BaseActivity;
import com.sabzishoppidriverapp.utils.ConnectionDirector;
import com.sabzishoppidriverapp.utils.pinview.Pinview;

import retrofit2.Response;

public class OtpActivity extends BaseActivity implements View.OnClickListener {
    private View rootview;
    private Button btn_fplogin;
    private TextView otpTime;
    private LinearLayout resendLayout;
    private Pinview pinview1;
    private String strMobile , strOtp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        init();
    }

    private void init()
    {
        mContext = this;
        cd = new ConnectionDirector(mContext);
        retrofitApiClient = RetrofitService.getRetrofit();
        strMobile = getIntent().getStringExtra("Mobile_Number");

        ((Button)findViewById(R.id.btn_fplogin)).setOnClickListener(this);
        btn_fplogin = findViewById(R.id.btn_fplogin);
        pinview1 = findViewById(R.id.pinview1);
        otpTime = (TextView)findViewById(R.id.otpTime);
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

        switch (view.getId()){
            case R.id.btn_fplogin:
                otpApi();
                //startFragment(Constant.SignUpFragment,new SignUpFragment());
                break;
        }
    }

    private void otpApi() {
        if (cd.isNetWorkAvailable()) {
            //strMobile = ((EditText) rootview.findViewById(R.id.et_login_email)).getText().toString();
            strOtp = pinview1.getValue();

            RetrofitService.getOtpData(new Dialog(mContext), retrofitApiClient.otpApi(strMobile, strOtp), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    OtpModel loginModel = (OtpModel) result.body();

                    if (!loginModel.getError())
                    {
                        Alerts.show(mContext, loginModel.getMessage());

                        AppPreference.setBooleanPreference(mContext, Constant.LOGIN_API , true);
                        AppPreference.setStringPreference(mContext, Constant.User_Id , loginModel.getDriver().getDriverId());

                        Gson gson = new GsonBuilder().setLenient().create();
                        String data = gson.toJson(loginModel);
                        AppPreference.setStringPreference(mContext, Constant.User_Data, data);
                        User.setUser(loginModel);
                        Intent intent = new Intent(mContext , MainActivity.class);
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
