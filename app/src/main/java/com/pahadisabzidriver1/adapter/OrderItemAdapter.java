package com.pahadisabzidriver1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pahadisabzidriver1.R;
import com.pahadisabzidriver1.model.delivery_list_modal.DeliveryOrderDetail;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.MyViewHolder> {

    private List<DeliveryOrderDetail> itemsDataList;
    private Context mContext;
    private View.OnClickListener onClickListener;

    public OrderItemAdapter(List<DeliveryOrderDetail> itemsDataList, Context mContext, View.OnClickListener onClickListener) {
        this.itemsDataList = itemsDataList;
        this.mContext = mContext;
        this.onClickListener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.item_number.setText(""+(position+1));
        holder.item_name.setText(itemsDataList.get(position).getProductName());
        holder.item_price.setText(itemsDataList.get(position).getPrice() + "");

        if (itemsDataList.get(position).getQuantityType().equals("0"))
        {
            holder.item_quantity.setText(itemsDataList.get(position).getQuantity()+" pcs");

        }else if(itemsDataList.get(position).getQuantityType().equals("1")) {
            holder.item_quantity.setText(itemsDataList.get(position).getQuantity()+ " kg");

        }else {

            double qut = Double.parseDouble(itemsDataList.get(position).getQuantity());
            if (qut > 1000)
            {
                double qti1 = qut/1000;
                holder.item_quantity.setText(qti1 + " kg");

            }else {
                holder.item_quantity.setText(itemsDataList.get(position).getQuantity() + " gm");

            }

        }

        if (itemsDataList.get(position).getType().equals("0"))
        {
            holder.item_type.setText("Fruits");
        }else {
            holder.item_type.setText("Vegitable");
        }
    }

    @Override
    public int getItemCount() {
        return itemsDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView  item_number, item_name, item_quantity, item_price, item_type;

        public MyViewHolder(View view) {
            super(view);

            item_number = itemView.findViewById(R.id.item_number);
            item_name = itemView.findViewById(R.id.item_name);
            item_quantity = itemView.findViewById(R.id.item_quantity);
            item_price = itemView.findViewById(R.id.item_price);
            item_type = itemView.findViewById(R.id.item_type);

        }
    }

}
