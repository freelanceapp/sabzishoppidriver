package com.sabziwaladriverapp.model.driver_update_responce;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverUpdateModel implements Parcelable
{

    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Parcelable.Creator<DriverUpdateModel> CREATOR = new Creator<DriverUpdateModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DriverUpdateModel createFromParcel(Parcel in) {
            return new DriverUpdateModel(in);
        }

        public DriverUpdateModel[] newArray(int size) {
            return (new DriverUpdateModel[size]);
        }

    }
            ;

    protected DriverUpdateModel(Parcel in) {
        this.result = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    public DriverUpdateModel() {
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public DriverUpdateModel withResult(Boolean result) {
        this.result = result;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DriverUpdateModel withMessage(String message) {
        this.message = message;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(result);
        dest.writeValue(message);
    }

    public int describeContents() {
        return 0;
    }

}