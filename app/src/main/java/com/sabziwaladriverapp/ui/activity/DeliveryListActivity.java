package com.sabziwaladriverapp.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.florent37.viewtooltip.ViewTooltip;
import com.sabziwaladriverapp.R;
import com.sabziwaladriverapp.adapter.DeliveryListAdapter;
import com.sabziwaladriverapp.constant.Constant;
import com.sabziwaladriverapp.model.User;
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

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;

public class DeliveryListActivity extends BaseActivity implements View.OnClickListener {


    private double latitude = 0.0;
    private double longitude = 0.0;

    private Dialog dialog, dialogPaid;
    private DeliveryListAdapter deliveryListAdapter;
    private List<DeliveryList> itemsDataList = new ArrayList<>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    ImageView driver_detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_list);
        dialog = new Dialog(mContext);
        init();
    }

    private void init() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        driver_detail = findViewById(R.id.driver_detail);
        RecyclerView recyclerViewDelivery = findViewById(R.id.recyclerViewDelivery);
        recyclerViewDelivery.setHasFixedSize(true);
        recyclerViewDelivery.setLayoutManager(new LinearLayoutManager(mContext));
        deliveryListAdapter = new DeliveryListAdapter(itemsDataList, mContext, this);
        recyclerViewDelivery.setAdapter(deliveryListAdapter);
        deliveryListAdapter.notifyDataSetChanged();
        getLatLong();


        Log.e("Driver Name "," : "+User.getUser().getDriver().getDriverName());
        Log.e("Number "," : "+User.getUser().getDriver().getDriverVehicleNumber());

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLatLong();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        driver_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewTooltip
                        .on(driver_detail)
                        // .customView(customView)
                        .position(ViewTooltip.Position.BOTTOM)
                        .arrowSourceMargin(10)
                        .arrowTargetMargin(10)
                        .padding(10,10,10,10)
                        .text("Name : "+User.getUser().getDriver().getDriverName()+" , Vehicle Number : "+User.getUser().getDriver().getDriverVehicleNumber())
                        .clickToHide(true)
                        .autoHide(true, 4000)
                        .color(getResources().getColor(R.color.white))
                        .textColor(getResources().getColor(R.color.black))
                        .animation(new ViewTooltip.FadeTooltipAnimation(500))
                        .onDisplay(new ViewTooltip.ListenerDisplay() {
                            @Override
                            public void onDisplay(View view) {
                                Log.d("ViewTooltip", "onDisplay");
                            }
                        })
                        .onHide(new ViewTooltip.ListenerHide() {
                            @Override
                            public void onHide(View view) {
                                Log.d("ViewTooltip", "onHide");
                            }
                        })
                        .show();
            }
        });

    }

    private Paint createPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(new LinearGradient(0, 0, 0, 600, BLUE, GREEN, Shader.TileMode.CLAMP));
        paint.setStyle(Paint.Style.FILL);
        return paint;
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

        ((TextView) dialogPaid.findViewById(R.id.edtName)).setText(itemsDataList.get(posData).getCustomer().getCustomerName());
        ((TextView) dialogPaid.findViewById(R.id.edtPhone)).setText(itemsDataList.get(posData).getCustomer().getCustomerContact());

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


    @Override
    protected void onResume() {
        super.onResume();
        getLatLong();
    }
}
