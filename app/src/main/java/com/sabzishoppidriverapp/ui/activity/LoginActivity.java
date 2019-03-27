package com.sabzishoppidriverapp.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sabzishoppidriverapp.R;
import com.sabzishoppidriverapp.utils.Alerts;
import com.sabzishoppidriverapp.utils.BaseActivity;

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
                    Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                    intent.putExtra("Mobile_Number", strMobile);
                }else {
                    Alerts.show(mContext,"Enter Mobile Number");
                }
                break;
        }
    }
}
