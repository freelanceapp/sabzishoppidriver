package com.pahadisabzidriver1.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pahadisabzidriver1.R;
import com.pahadisabzidriver1.constant.Constant;
import com.pahadisabzidriver1.model.User;
import com.pahadisabzidriver1.model.otp_responce.OtpModel;
import com.pahadisabzidriver1.utils.Alerts;
import com.pahadisabzidriver1.utils.AppPreference;
import com.pahadisabzidriver1.utils.BaseActivity;
import com.pahadisabzidriver1.utils.ConnectionDirector;

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
        if ((ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
                ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) &&
                    (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) &&
                    (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))) {
                Alerts.show(mContext, "Permission granted");
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION,
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

                    if (AppPreference.getBooleanPreference(mContext, Constant.Is_Login)) {
                        Gson gson = new Gson();
                        String userData = AppPreference.getStringPreference(mContext, Constant.User_Data);
                        Log.e("Userid", AppPreference.getStringPreference(mContext, Constant.User_Id));
                        OtpModel loginModal = gson.fromJson(userData, OtpModel.class);
                        User.setUser(loginModal);

                        //Intent intent = new Intent(SplashActivity.this, MapsActivity.class);
                        Intent intent = new Intent(SplashActivity.this, DeliveryListActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
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
