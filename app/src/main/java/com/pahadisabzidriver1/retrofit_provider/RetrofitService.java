package com.pahadisabzidriver1.retrofit_provider;

import android.app.Dialog;

import com.pahadisabzidriver1.constant.Constant;
import com.pahadisabzidriver1.model.delivery_list_modal.DeliveryListMainModal;
import com.pahadisabzidriver1.model.driver_update_responce.DriverUpdateModel;
import com.pahadisabzidriver1.model.login_responce.LoginModel;
import com.pahadisabzidriver1.model.otp_responce.OtpModel;
import com.pahadisabzidriver1.model.signup_responce.SignUpModel;
import com.pahadisabzidriver1.utils.AppProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    public static RetrofitApiClient client;

    public RetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        client = retrofit.create(RetrofitApiClient.class);
    }

    public static RetrofitApiClient getRxClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();
        return retrofit.create(RetrofitApiClient.class);
    }

    public static RetrofitApiClient getRetrofit() {

        if (client == null)
            new RetrofitService();
        return client;
    }

    public static void getLoginData(final Dialog dialog, final Call<LoginModel> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }


    public static void getOtpData(final Dialog dialog, final Call<OtpModel> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<OtpModel>() {
            @Override
            public void onResponse(Call<OtpModel> call, Response<OtpModel> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<OtpModel> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }


    public static void getProfile(final Dialog dialog, final Call<LoginModel> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void getDeliveryJobData(final Dialog dialog, final Call<DeliveryListMainModal> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<DeliveryListMainModal>() {
            @Override
            public void onResponse(Call<DeliveryListMainModal> call, Response<DeliveryListMainModal> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<DeliveryListMainModal> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void driverUpdate(final Dialog dialog, final Call<DriverUpdateModel> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<DriverUpdateModel>() {
            @Override
            public void onResponse(Call<DriverUpdateModel> call, Response<DriverUpdateModel> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<DriverUpdateModel> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void getSignData(final Dialog dialog, final Call<SignUpModel> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<SignUpModel> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

}