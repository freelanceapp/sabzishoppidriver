package com.sabziwaladriverapp.model;


import com.sabziwaladriverapp.model.otp_responce.OtpModel;

/**
 * Created by Natraj on 7/11/2017.
 */

public class User {

    public static OtpModel user;

    public static OtpModel getUser() {
        return user;
    }

    public static void setUser(OtpModel user) {
        User.user = user;
    }
}
