package com.sabziwaladriverapp.model.delivery_list_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryOrderDetail implements Parcelable {

    @SerializedName("order_details_id")
    @Expose
    private String orderDetailsId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("type")
    @Expose
    private String type;
    public final static Parcelable.Creator<DeliveryOrderDetail> CREATOR = new Creator<DeliveryOrderDetail>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DeliveryOrderDetail createFromParcel(Parcel in) {
            return new DeliveryOrderDetail(in);
        }

        public DeliveryOrderDetail[] newArray(int size) {
            return (new DeliveryOrderDetail[size]);
        }

    };

    protected DeliveryOrderDetail(Parcel in) {
        this.orderDetailsId = ((String) in.readValue((String.class.getClassLoader())));
        this.productName = ((String) in.readValue((String.class.getClassLoader())));
        this.quantity = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.discount = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
    }

    public DeliveryOrderDetail() {
    }

    public String getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(String orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(orderDetailsId);
        dest.writeValue(productName);
        dest.writeValue(quantity);
        dest.writeValue(price);
        dest.writeValue(discount);
        dest.writeValue(type);
    }

    public int describeContents() {
        return 0;
    }

}
