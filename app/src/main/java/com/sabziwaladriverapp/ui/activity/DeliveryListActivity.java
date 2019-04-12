package com.sabziwaladriverapp.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.sabziwaladriverapp.R;
import com.sabziwaladriverapp.adapter.DeliveryListAdapter;
import com.sabziwaladriverapp.constant.Constant;
import com.sabziwaladriverapp.model.delivery_list_modal.DeliveryList;
import com.sabziwaladriverapp.model.delivery_list_modal.DeliveryListMainModal;
import com.sabziwaladriverapp.retrofit_provider.RetrofitService;
import com.sabziwaladriverapp.retrofit_provider.WebResponse;
import com.sabziwaladriverapp.ui.map_activity.MapsActivity;
import com.sabziwaladriverapp.utils.Alerts;
import com.sabziwaladriverapp.utils.AppPreference;
import com.sabziwaladriverapp.utils.AppProgressDialog;
import com.sabziwaladriverapp.utils.BaseActivity;
import com.sabziwaladriverapp.utils.GpsTracker;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class DeliveryListActivity extends BaseActivity implements View.OnClickListener {


    private double latitude = 0.0;
    private double longitude = 0.0;

    private Dialog dialog, dialogPaid;
    private DeliveryListAdapter deliveryListAdapter;
    private List<DeliveryList> itemsDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_list);
        dialog = new Dialog(mContext);
        init();
    }

    private void init() {
        RecyclerView recyclerViewDelivery = findViewById(R.id.recyclerViewDelivery);
        recyclerViewDelivery.setHasFixedSize(true);
        recyclerViewDelivery.setLayoutManager(new LinearLayoutManager(mContext));
        deliveryListAdapter = new DeliveryListAdapter(itemsDataList, mContext, this);
        recyclerViewDelivery.setAdapter(deliveryListAdapter);
        deliveryListAdapter.notifyDataSetChanged();

        getLatLong();
    }

    private void getLatLong() {
        GpsTracker gpsTracker = new GpsTracker(mContext);
        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();
        refreshLocation();
    }

    private void refreshLocation() {
        AppProgressDialog.show(dialog);
        if (latitude == 0) {
            AppProgressDialog.show(dialog);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getLatLong();
                }
            }, 3000);
        } else {
            deliveryJobApi();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgNext:
                int pos = Integer.parseInt(view.getTag().toString());
                Intent intent = new Intent(mContext, MapsActivity.class);
                intent.putExtra("delivery_data", (Parcelable) itemsDataList.get(pos));
                startActivity(intent);
                break;
            case R.id.imgCustomerInfo:
                payDialog(view);
                break;
        }
    }

    private void deliveryJobApi() {
        String strId = AppPreference.getStringPreference(mContext, Constant.User_Id);
        String strLatitude = String.valueOf(latitude);
        String strLongitude = String.valueOf(longitude);

        if (cd.isNetWorkAvailable()) {
            RetrofitService.getDeliveryJobData(null, retrofitApiClient.deliveryJobData(
                    strId, strLatitude, strLongitude), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) throws JSONException {
                    AppProgressDialog.hide(dialog);
                    DeliveryListMainModal mainModal = (DeliveryListMainModal) result.body();
                    itemsDataList.clear();
                    if (mainModal != null) {
                        if (mainModal.getDelivery().size() > 0) {
                            itemsDataList.addAll(mainModal.getDelivery());
                        } else {
                            Alerts.show(mContext, "No data found...!!!");
                        }
                    } else {
                        Alerts.show(mContext, "No data found...!!!");
                    }
                    deliveryListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onResponseFailed(String error) {
                    AppProgressDialog.hide(dialog);
                    Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }
    }

    private void payDialog(View view) {
        int posData = Integer.parseInt(view.getTag().toString());

        dialogPaid = new Dialog(mContext);
        dialogPaid.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPaid.setContentView(R.layout.dialog_paid);

        dialogPaid.setCanceledOnTouchOutside(true);
        dialogPaid.setCancelable(true);
        if (dialogPaid.getWindow() != null)
            dialogPaid.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ((EditText) dialogPaid.findViewById(R.id.edtName)).setText(itemsDataList.get(posData).getCustomer().getCustomerName());
        ((EditText) dialogPaid.findViewById(R.id.edtPhone)).setText(itemsDataList.get(posData).getCustomer().getCustomerContact());

        dialogPaid.findViewById(R.id.txtOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPaid.dismiss();
            }
        });

        Window window = dialogPaid.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialogPaid.show();
    }


}
