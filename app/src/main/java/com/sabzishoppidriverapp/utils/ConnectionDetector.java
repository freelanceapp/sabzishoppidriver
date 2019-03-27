package com.sabzishoppidriverapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import ibt.sabzishoppee.R;


public class ConnectionDetector {
    Context mContext;

    public ConnectionDetector(Context mContext) {
        this.mContext = mContext;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void show(Context context ){
        Toast.makeText(context, context.getResources().getString(R.string.connection), Toast.LENGTH_SHORT).show();
    }
}