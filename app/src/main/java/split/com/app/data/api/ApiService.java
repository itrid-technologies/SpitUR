package split.com.app.data.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import split.com.app.data.model.home_categories.CategoriesModel;
import split.com.app.data.model.otp_verification.AuthenticationModel;
import split.com.app.data.model.register.RegisterModel;
import split.com.app.data.model.get_avatar.AvatarModel;
import split.com.app.data.model.phone_number.NumberModel;
import split.com.app.data.model.basic_model.BasicModel;

public interface ApiService {

    @GET("users/getAvatar")
    Call<AvatarModel> getAvatar();

    @FormUrlEncoded
    @POST("users/phoneAlreadyExist")
    Call<NumberModel> checkNumberExistence(@Field("phone") String number);


    @FormUrlEncoded
    @POST("users/register")
    Call<RegisterModel> register(@Field("phone") String number,
                                 @Field("name") String name,
                                 @Field("avatar") String avatar,
                                 @Field("userId") String id);
    @FormUrlEncoded
    @POST("users/sendOtp")
    Call<BasicModel> sendOTP(@Field("phone") String number);

    @FormUrlEncoded
    @POST("users/authentication")
    Call<AuthenticationModel> authenticateUser(@Field("phone") String number,
                                               @Field("otp") String otp);
    @FormUrlEncoded
    @POST("users/usernameExists")
    Call<BasicModel> checkUserIdExistence(@Field("userId") String id);

    @GET("groups/get_categories")
    Call<CategoriesModel> getAllCategories(@Header("Authorization") String token);
}
