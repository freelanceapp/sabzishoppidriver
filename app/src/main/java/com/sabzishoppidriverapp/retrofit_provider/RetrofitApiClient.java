package com.sabzishoppidriverapp.retrofit_provider;


import com.sabzishoppidriverapp.constant.Constant;
import com.sabzishoppidriverapp.model.login_responce.LoginModel;
import com.sabzishoppidriverapp.model.otp_responce.OtpModel;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
                                 @Field("mobile_no") String mobile_no, @Field("subject") String subject
            , @Field("message") String message);



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




    @Multipart
    @POST(Constant.UPDATE_PROFILE)
    Call<LoginModel> profileimage(@Part("user_id") RequestBody user_id,
                                  @Part("user_gendar") RequestBody user_gendar,
                                  @Part("user_name") RequestBody user_name,
                                  @Part("user_dob") RequestBody user_dob,
                                  @Part MultipartBody.Part user_profile_picture);







}