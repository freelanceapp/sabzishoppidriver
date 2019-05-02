package com.pahadisabzidriver1.retrofit_provider;


import com.pahadisabzidriver1.constant.Constant;
import com.pahadisabzidriver1.model.delivery_list_modal.DeliveryListMainModal;
import com.pahadisabzidriver1.model.driver_update_responce.DriverUpdateModel;
import com.pahadisabzidriver1.model.login_responce.LoginModel;
import com.pahadisabzidriver1.model.otp_responce.OtpModel;
import com.pahadisabzidriver1.model.signup_responce.SignUpModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitApiClient {


    @FormUrlEncoded
    @POST(Constant.PROFILE)
    Call<LoginModel> getprofile(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST(Constant.OTP_API)
    Call<OtpModel> otpApi(@Field("contact") String contact,
                          @Field("otp_number") String otp_number);

    @FormUrlEncoded
    @POST(Constant.LOGIN_API)
    Call<LoginModel> loginData(@Field("contact") String contact);


    @FormUrlEncoded
    @POST(Constant.CONTACT_US)
    Call<ResponseBody> contactUs(@Field("name") String name, @Field("email") String email,
                                 @Field("mobile_no") String mobile_no, @Field("subject") String subject,
                                 @Field("message") String message);

    /*driver_id,lati,lang*/
    @FormUrlEncoded
    @POST(Constant.DRIVER_JOB)
    Call<DeliveryListMainModal> deliveryJobData(@Field("driver_id") String driver_id, @Field("lati") String lati,
                                                @Field("lang") String lang);


    @FormUrlEncoded
    @POST(Constant.ORDER_API)
    Call<ResponseBody> order(
            @Field("user_id") String user_id,
            @Field("first_name") String first_name,
            @Field("company_name") String company_name,
            @Field("user_email") String user_email,
            @Field("address") String address,
            @Field("phone_number") String phone_number,
            @Field("state") String state,
            @Field("city") String city,
            @Field("zip_code") String zip_code,
            @Field("product_details") String product_details);

    @FormUrlEncoded
    @POST(Constant.TOKEN_UPDATE)
    Call<SignUpModel> updateToken(@Field("user_id") String user_id,
                                  @Field("token") String token,
                                  @Field("user_ip") String user_ip,
                                  @Field("type") String type);

    @Multipart
    @POST(Constant.UPDATE_PROFILE)
    Call<LoginModel> profileimage(@Part("user_id") RequestBody user_id,
                                  @Part("user_gendar") RequestBody user_gendar,
                                  @Part("user_name") RequestBody user_name,
                                  @Part("user_dob") RequestBody user_dob,
                                  @Part MultipartBody.Part user_profile_picture);


    @FormUrlEncoded
    @POST(Constant.DRIVER_UPDATE)
    Call<DriverUpdateModel> driverUpdate(@Field("delivery_id") String delivery_id,
                                         @Field("comment") String comment,
                                         @Field("status") String status);


}