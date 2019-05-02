package com.pahadisabzidriver1.model.delivery_list_modal;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryListMainModal implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("delivery")
    @Expose
    private List<DeliveryList> delivery = new ArrayList<DeliveryList>();
    public final static Parcelable.Creator<DeliveryListMainModal> CREATOR = new Creator<DeliveryListMainModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DeliveryListMainModal createFromParcel(Parcel in) {
            return new DeliveryListMainModal(in);
        }

        public DeliveryListMainModal[] newArray(int size) {
            return (new DeliveryListMainModal[size]);
        }

    }
            ;

    protected DeliveryListMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.delivery, (DeliveryList.class.getClassLoader()));
    }

    public DeliveryListMainModal() {
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DeliveryList> getDelivery() {
        return delivery;
    }

    public void setDelivery(List<DeliveryList> delivery) {
        this.delivery = delivery;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(delivery);
    }

    public int describeContents() {
        return 0;
    }

}