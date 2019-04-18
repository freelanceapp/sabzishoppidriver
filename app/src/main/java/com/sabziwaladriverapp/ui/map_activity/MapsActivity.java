package com.sabziwaladriverapp.ui.map_activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sabziwaladriverapp.R;
import com.sabziwaladriverapp.adapter.DeliveryListAdapter;
import com.sabziwaladriverapp.adapter.OrderItemAdapter;
import com.sabziwaladriverapp.constant.Constant;
import com.sabziwaladriverapp.model.delivery_list_modal.DeliveryList;
import com.sabziwaladriverapp.model.delivery_list_modal.DeliveryOrderDetail;
import com.sabziwaladriverapp.model.driver_update_responce.DriverUpdateModel;
import com.sabziwaladriverapp.retrofit_provider.RetrofitService;
import com.sabziwaladriverapp.retrofit_provider.WebResponse;
import com.sabziwaladriverapp.utils.Alerts;
import com.sabziwaladriverapp.utils.AppPreference;
import com.sabziwaladriverapp.utils.AppProgressDialog;
import com.sabziwaladriverapp.utils.BaseActivity;
import com.sabziwaladriverapp.utils.GpsTracker;
import com.sabziwaladriverapp.utils.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Response;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, View.OnClickListener {

    private DeliveryList deliveryData;
    private Dialog dialog, dialogPaid;

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;

    private LatLng latLngPick;
    private double endLatitude = 0.0;
    private double endLongitude = 0.0;
    private double startLatitude = 0.0;
    private double startLongitude = 0.0;

    private GoogleMap mMap;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private LatLng latLng;
    private Marker marker;


    private String mainUrl = "";
    private String strDriverId, strDeliveryId;
    private Button btn_cancel, btn_complete, btn_order_detail;


    BottomSheetBehavior behavior;
    CoordinatorLayout coordinatorLayout;

    RecyclerView order_item_list;
    List<DeliveryOrderDetail> orderDetailList = new ArrayList<>();
    OrderItemAdapter adapter;
    TextView tv_c_number, tv_c_name;
    ImageView btn_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        init();

    }

    private void init() {
        dialog = new Dialog(mContext);
        deliveryData = getIntent().getParcelableExtra("delivery_data");
        getLatLong();
        strDeliveryId = deliveryData.getDeliveryId();
        strDriverId = AppPreference.getStringPreference(mContext, Constant.User_Id);
        driverStatus(strDeliveryId, "", "0");

        order_item_list = findViewById(R.id.order_item_list);
        tv_c_number = findViewById(R.id.tv_c_number);
        tv_c_name = findViewById(R.id.tv_c_name);
        btn_call = findViewById(R.id.btn_call);


        tv_c_name.setText(deliveryData.getCustomer().getCustomerName());
        tv_c_number.setText(deliveryData.getCustomer().getCustomerContact());

        orderDetailList.addAll(deliveryData.getOrderDetails());

        endLatitude = Double.parseDouble(deliveryData.getLang());
        endLongitude = Double.parseDouble(deliveryData.getLati());

        order_item_list.setHasFixedSize(true);
        order_item_list.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new OrderItemAdapter(orderDetailList, mContext, this);
        order_item_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        View bottomSheet = findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {

            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_complete = findViewById(R.id.btn_complete);
        btn_order_detail = findViewById(R.id.btn_order_detail);
        btn_cancel.setOnClickListener(this);
        btn_complete.setOnClickListener(this);
        btn_order_detail.setOnClickListener(this);
        btn_call.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Alerts.show(mContext, "Please enable location permission...!!!");
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        getLatLong();
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLngPick) {
            }
        });

        if (latitude > 0) {
            LatLng latLng = new LatLng(latitude, longitude);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
            mMap.animateCamera(cameraUpdate);
        }

        MarkerOptions markerOptions = new MarkerOptions();


        /*endLatitude = 22.7820662;
        endLongitude = 75.8579348;*/

        startLatitude = latitude;
        startLongitude = longitude;

        latLngPick = new LatLng(endLatitude, endLongitude);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        markerOptions.position(latLngPick);
        //mMap.animateCamera(CameraUpdateFactory.newLatLng(latLngPick));
        markerOptions.title((deliveryData.getDeliveryDistance() / 1000)+"km" );
        mMap.addMarker(markerOptions);

        if (startLatitude > 0 && endLatitude > 0) {
            mainUrl = makeURL(startLatitude, startLongitude, endLatitude, endLongitude);
            new connectAsyncTask().execute(mainUrl);
        }
    }

    private void getLatLong() {
        GpsTracker gpsTracker = new GpsTracker(mContext);
        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();
        latLng = new LatLng(latitude, longitude);
        getAddressList();
    }

    private void getAddressList() {
        AppProgressDialog.show(dialog);
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                AppProgressDialog.hide(dialog);
            } else {
                AppProgressDialog.show(dialog);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getLatLong();
                    }
                }, 3000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /************************************************/
    public String makeURL(double startLat, double startLong, double endLat, double endLong) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(startLat));
        urlString.append(",");
        urlString.append(Double.toString(startLong));
        urlString.append("&destination=");// to
        urlString.append(Double.toString(endLat));
        urlString.append(",");
        urlString.append(Double.toString(endLong));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key=" + getResources().getString(R.string.map_api));
        return urlString.toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                commentDialog(view, "Order Cancel", "3");
                break;
            case R.id.btn_complete:
                commentDialog(view, "Order Complete", "2");
                break;

            case R.id.btn_order_detail:
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;

            case R.id.btn_call:

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(deliveryData.getCustomer().getCustomerContact()));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);

                break;
        }
    }

    private class connectAsyncTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... strings) {
            JSONParser jParser = new JSONParser();
            String json = jParser.getJSONFromUrl(mainUrl);
            return json;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Fetching route, Please wait...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.hide();
            if (result != null) {
                drawPath(result);
                driverStatus(strDeliveryId, "", "1");
            }
        }
    }

    public void drawPath(String result) {
        try {
            //Tranform the string into a json object
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);
            mMap.addPolyline(new PolylineOptions()
                    .addAll(list)
                    .width(12)
                    .color(Color.parseColor("#05b1fb"))//Google maps blue color
                    .geodesic(true)
            );
           /*for(int z = 0; z<list.size()-1;z++){
                LatLng src= list.get(z);
                LatLng dest= list.get(z+1);
                Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(src.latitude, src.longitude), new LatLng(dest.latitude,   dest.longitude))
                .width(2)
                .color(Color.BLUE).geodesic(true));
            }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    /****************************************************************************/
    /*
     * Location update
     * */
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void driverStatus(String strDeliveryId, String comment, final String status) {
        String strId = strDeliveryId;
        String strComment = comment;
        String strStatus = status;

        if (cd.isNetWorkAvailable()) {
            RetrofitService.driverUpdate(null, retrofitApiClient.driverUpdate(strId, strComment, strStatus), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) throws JSONException {
                    AppProgressDialog.hide(dialog);
                    DriverUpdateModel driverUpdateModel = (DriverUpdateModel) result.body();

                    if (!driverUpdateModel.getResult())
                    {
                        if (status.equals("2") )
                        {
                            finish();
                        }else if (status.equals("3"))
                        {
                            finish();
                        }else {
                        }

                    }else {
                        Alerts.show(mContext, driverUpdateModel.getMessage());
                    }
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


    private void commentDialog(View view, String strTitle, final String status) {

        dialogPaid = new Dialog(mContext);
        dialogPaid.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPaid.setContentView(R.layout.dialog_order_comment);

        dialogPaid.setCanceledOnTouchOutside(true);
        dialogPaid.setCancelable(true);
        if (dialogPaid.getWindow() != null)
            dialogPaid.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final EditText etComment = (EditText) dialogPaid.findViewById(R.id.et_comment);
        TextView tvComment = (TextView) dialogPaid.findViewById(R.id.tv_comment_title);
        Button btn_submit = (Button) dialogPaid.findViewById(R.id.btn_submit);
        tvComment.setText(strTitle);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strComment = etComment.getText().toString();
                driverStatus(strDeliveryId, strComment, status);
                dialogPaid.dismiss();

            }
        });

        Window window = dialogPaid.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialogPaid.show();
    }

}
