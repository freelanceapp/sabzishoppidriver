package com.sabzishoppidriverapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sabzishoppidriverapp.R;
import com.sabzishoppidriverapp.model.delivery_list_modal.DeliveryList;

import java.util.List;

public class DeliveryListAdapter extends RecyclerView.Adapter<DeliveryListAdapter.MyViewHolder> {

    private List<DeliveryList> itemsDataList;
    private Context mContext;
    private View.OnClickListener onClickListener;

    public DeliveryListAdapter(List<DeliveryList> itemsDataList, Context mContext, View.OnClickListener onClickListener) {
        this.itemsDataList = itemsDataList;
        this.mContext = mContext;
        this.onClickListener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_delivery_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txtOrderNumber.setText(itemsDataList.get(position).getOrderNumber());
        holder.txtHouseNo.setText(itemsDataList.get(position).getSippingHouseNumber());
        holder.txtDistance.setText(itemsDataList.get(position).getDeliveryDistance() + "");
        holder.txtLandmark.setText(itemsDataList.get(position).getShippingLandmark());
        holder.txtAddress.setText(itemsDataList.get(position).getAddressType());
        holder.txtPaymentType.setText(itemsDataList.get(position).getPaymentType());
        holder.txtAmount.setText("Rs." + itemsDataList.get(position).getAmount());

        holder.imgCustomerInfo.setTag(position);
        holder.imgCustomerInfo.setOnClickListener(onClickListener);
        holder.imgNext.setTag(position);
        holder.imgNext.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return itemsDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtOrderNumber, txtHouseNo, txtDistance, txtLandmark, txtAddress,
                txtPaymentType, txtAmount;
        private ImageView imgCustomerInfo, imgNext;

        public MyViewHolder(View view) {
            super(view);

            imgNext = itemView.findViewById(R.id.imgNext);
            imgCustomerInfo = itemView.findViewById(R.id.imgCustomerInfo);
            txtOrderNumber = itemView.findViewById(R.id.txtOrderNumber);
            txtHouseNo = itemView.findViewById(R.id.txtHouseNo);
            txtDistance = itemView.findViewById(R.id.txtDistance);
            txtLandmark = itemView.findViewById(R.id.txtLandmark);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtPaymentType = itemView.findViewById(R.id.txtPaymentType);
            txtAmount = itemView.findViewById(R.id.txtAmount);
        }
    }

}
