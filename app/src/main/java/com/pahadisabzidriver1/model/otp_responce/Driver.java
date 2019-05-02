
package com.pahadisabzidriver1.model.otp_responce;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Driver implements Parcelable
{

    @SerializedName("driver_id")
    @Expose
    private String driverId;
    @SerializedName("driver_name")
    @Expose
    private String driverName;
    @SerializedName("driver_contact")
    @Expose
    private String driverContact;
    @SerializedName("driver_vehicle_number")
    @Expose
    private String driverVehicleNumber;
    @SerializedName("driver_created_date")
    @Expose
    private String driverCreatedDate;
    public final static Creator<Driver> CREATOR = new Creator<Driver>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Driver createFromParcel(Parcel in) {
            return new Driver(in);
        }

        public Driver[] newArray(int size) {
            return (new Driver[size]);
        }

    }
    ;

    protected Driver(Parcel in) {
        this.driverId = ((String) in.readValue((String.class.getClassLoader())));
        this.driverName = ((String) in.readValue((String.class.getClassLoader())));
        this.driverContact = ((String) in.readValue((String.class.getClassLoader())));
        this.driverVehicleNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.driverCreatedDate = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Driver() {
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public Driver withDriverId(String driverId) {
        this.driverId = driverId;
        return this;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Driver withDriverName(String driverName) {
        this.driverName = driverName;
        return this;
    }

    public String getDriverContact() {
        return driverContact;
    }

    public void setDriverContact(String driverContact) {
        this.driverContact = driverContact;
    }

    public Driver withDriverContact(String driverContact) {
        this.driverContact = driverContact;
        return this;
    }

    public String getDriverVehicleNumber() {
        return driverVehicleNumber;
    }

    public void setDriverVehicleNumber(String driverVehicleNumber) {
        this.driverVehicleNumber = driverVehicleNumber;
    }

    public Driver withDriverVehicleNumber(String driverVehicleNumber) {
        this.driverVehicleNumber = driverVehicleNumber;
        return this;
    }

    public String getDriverCreatedDate() {
        return driverCreatedDate;
    }

    public void setDriverCreatedDate(String driverCreatedDate) {
        this.driverCreatedDate = driverCreatedDate;
    }

    public Driver withDriverCreatedDate(String driverCreatedDate) {
        this.driverCreatedDate = driverCreatedDate;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(driverId);
        dest.writeValue(driverName);
        dest.writeValue(driverContact);
        dest.writeValue(driverVehicleNumber);
        dest.writeValue(driverCreatedDate);
    }

    public int describeContents() {
        return  0;
    }

}
