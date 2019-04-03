package com.sabzishoppidriverapp.model.delivery_list_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryCustomerDetail implements Parcelable {

    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_contact")
    @Expose
    private String customerContact;
    public final static Parcelable.Creator<DeliveryCustomerDetail> CREATOR = new Creator<DeliveryCustomerDetail>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DeliveryCustomerDetail createFromParcel(Parcel in) {
            return new DeliveryCustomerDetail(in);
        }

        public DeliveryCustomerDetail[] newArray(int size) {
            return (new DeliveryCustomerDetail[size]);
        }

    };

    protected DeliveryCustomerDetail(Parcel in) {
        this.customerName = ((String) in.readValue((String.class.getClassLoader())));
        this.customerContact = ((String) in.readValue((String.class.getClassLoader())));
    }

    public DeliveryCustomerDetail() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(customerName);
        dest.writeValue(customerContact);
    }

    public int describeContents() {
        return 0;
    }

}