package com.sabziwaladriverapp.model.delivery_list_modal;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryList implements Parcelable {

    @SerializedName("delivery_id")
    @Expose
    private String deliveryId;
    @SerializedName("delivery_distance")
    @Expose
    private Integer deliveryDistance;
    @SerializedName("order_number")
    @Expose
    private String orderNumber;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("lati")
    @Expose
    private String lati;
    @SerializedName("sipping_house_number")
    @Expose
    private String sippingHouseNumber;
    @SerializedName("shipping_street_name")
    @Expose
    private String shippingStreetName;
    @SerializedName("shipping_landmark")
    @Expose
    private String shippingLandmark;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("address_type")
    @Expose
    private String addressType;
    @SerializedName("user_note")
    @Expose
    private String userNote;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("order_details")
    @Expose
    private List<DeliveryOrderDetail> orderDetails = new ArrayList<DeliveryOrderDetail>();
    @SerializedName("customer")
    @Expose
    private DeliveryCustomerDetail customer;
    public final static Parcelable.Creator<DeliveryList> CREATOR = new Creator<DeliveryList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DeliveryList createFromParcel(Parcel in) {
            return new DeliveryList(in);
        }

        public DeliveryList[] newArray(int size) {
            return (new DeliveryList[size]);
        }

    };

    protected DeliveryList(Parcel in) {
        this.deliveryId = ((String) in.readValue((String.class.getClassLoader())));
        this.deliveryDistance = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.orderNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.amount = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentType = ((String) in.readValue((String.class.getClassLoader())));
        this.location = ((String) in.readValue((String.class.getClassLoader())));
        this.lang = ((String) in.readValue((String.class.getClassLoader())));
        this.lati = ((String) in.readValue((String.class.getClassLoader())));
        this.sippingHouseNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.shippingStreetName = ((String) in.readValue((String.class.getClassLoader())));
        this.shippingLandmark = ((String) in.readValue((String.class.getClassLoader())));
        this.city = ((String) in.readValue((String.class.getClassLoader())));
        this.state = ((String) in.readValue((String.class.getClassLoader())));
        this.zipcode = ((String) in.readValue((String.class.getClassLoader())));
        this.addressType = ((String) in.readValue((String.class.getClassLoader())));
        this.userNote = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.orderDetails, (DeliveryOrderDetail.class.getClassLoader()));
        this.customer = ((DeliveryCustomerDetail) in.readValue((DeliveryCustomerDetail.class.getClassLoader())));
    }

    public DeliveryList() {
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Integer getDeliveryDistance() {
        return deliveryDistance;
    }

    public void setDeliveryDistance(Integer deliveryDistance) {
        this.deliveryDistance = deliveryDistance;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public String getSippingHouseNumber() {
        return sippingHouseNumber;
    }

    public void setSippingHouseNumber(String sippingHouseNumber) {
        this.sippingHouseNumber = sippingHouseNumber;
    }

    public String getShippingStreetName() {
        return shippingStreetName;
    }

    public void setShippingStreetName(String shippingStreetName) {
        this.shippingStreetName = shippingStreetName;
    }

    public String getShippingLandmark() {
        return shippingLandmark;
    }

    public void setShippingLandmark(String shippingLandmark) {
        this.shippingLandmark = shippingLandmark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public List<DeliveryOrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<DeliveryOrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public DeliveryCustomerDetail getCustomer() {
        return customer;
    }

    public void setCustomer(DeliveryCustomerDetail customer) {
        this.customer = customer;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(deliveryId);
        dest.writeValue(deliveryDistance);
        dest.writeValue(orderNumber);
        dest.writeValue(amount);
        dest.writeValue(paymentType);
        dest.writeValue(location);
        dest.writeValue(lang);
        dest.writeValue(lati);
        dest.writeValue(sippingHouseNumber);
        dest.writeValue(shippingStreetName);
        dest.writeValue(shippingLandmark);
        dest.writeValue(city);
        dest.writeValue(state);
        dest.writeValue(zipcode);
        dest.writeValue(addressType);
        dest.writeValue(userNote);
        dest.writeValue(createdDate);
        dest.writeList(orderDetails);
        dest.writeValue(customer);
    }

    public int describeContents() {
        return 0;
    }

}