package com.sabzishoppidriverapp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sabzishoppidriverapp.R;
import com.sabzishoppidriverapp.constant.Constant;
import com.sabzishoppidriverapp.model.User;
import com.sabzishoppidriverapp.model.login_responce.LoginModel;
import com.sabzishoppidriverapp.model.otp_responce.OtpModel;
import com.sabzishoppidriverapp.ui.map_activity.MapsActivity;
import com.sabzishoppidriverapp.utils.Alerts;
import com.sabzishoppidriverapp.utils.AppPreference;
import com.sabzishoppidriverapp.utils.BaseActivity;
import com.sabzishoppidriverapp.utils.ConnectionDirector;

public class SplashActivity extends BaseActivity {

    private static final int REQUEST_PERMISSIONS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        cd = new ConnectionDirector(mContext);

        fn_checkpermission();
    }

    /*********************************************************************************/
    /*
    * Check permission
    * */
    private void fn_checkpermission() {
        if ((ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
                ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) &&
                    (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) &&
                    (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))) {
                Alerts.show(mContext, "Permission granted");
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
            }
        } else {
            turnGPSOn();
        }
    }

    public void turnGPSOn() {
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.contains("gps")) { //if gps is disabled
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 102);
        } else {
            handlerTimer();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 102:
                turnGPSOn();
                break;
            case REQUEST_PERMISSIONS:
                turnGPSOn();
                break;
        }
    }

    private void handlerTimer() {
        if (cd.isNetWorkAvailable()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (AppPreference.getBooleanPreference(mContext , Constant.Is_Login)) {
                        Gson gson = new Gson();
                        String userData = AppPreference.getStringPreference(mContext, Constant.User_Data);
                        OtpModel loginModal = gson.fromJson(userData, OtpModel.class);
                        User.setUser(loginModal);

                        Intent intent = new Intent(SplashActivity.this, MapsActivity.class);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        turnGPSOn();
                    } else {
                        Toast.makeText(mContext, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}
