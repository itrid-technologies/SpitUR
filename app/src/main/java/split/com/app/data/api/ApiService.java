package split.com.app.data.api;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import split.com.app.data.model.HomeContentModel;
import split.com.app.data.model.create_group.CreateGroupModel;
import split.com.app.data.model.create_group.DataRequiredForGroup;
import split.com.app.data.model.group_detail.GroupDetailModel;
import split.com.app.data.model.home_categories.CategoriesModel;
import split.com.app.data.model.join_group.JoinGroupModel;
import split.com.app.data.model.otp_verification.AuthenticationModel;
import split.com.app.data.model.plans.PlanModel;
import split.com.app.data.model.popular_subcategory.PopularSubCategoryModel;
import split.com.app.data.model.register.RegisterModel;
import split.com.app.data.model.get_avatar.AvatarModel;
import split.com.app.data.model.phone_number.NumberModel;
import split.com.app.data.model.basic_model.BasicModel;
import split.com.app.data.model.search_sub_category.SearchSubCatModel;

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

    @GET("groups/get_pouplar_sub_categories")
    Call<PopularSubCategoryModel> getPopularCategories();

    @GET("groups/get_plan/{id}")
    Call<PlanModel> getPlans(@Path("id") String id);


    @FormUrlEncoded
    @POST("groups/create_group")
    Call<CreateGroupModel> createGroup(@Header("Authorization") String token,
                                       @FieldMap Map<String, String> options);

    @GET("groups/get_home_content")
    Call<HomeContentModel> getHomeData(@Header("Authorization") String token);

    @POST("groups/group_details_search")
    Call<GroupDetailModel> getGroupDetails(@Body JsonObject object);

    @POST("groups/group_details_search")
    Call<GroupDetailModel> getGroupDetailsSearch(@Body JsonObject object);

    @FormUrlEncoded
    @POST("groups/join_group")
    Call<JoinGroupModel> joinGroup(@Header("Authorization") String token,
                                   @Field("group_id") String id
                               );

    @GET("groups/get_joined_groups")
    Call<GroupDetailModel> getJoinedGroups(@Header("Authorization") String token);

    Call<GroupDetailModel> getCreatedGroups(@Header("Authorization") String token);

    @GET("groups/search_sub_categories/{data}")
    Call<PopularSubCategoryModel> getsubCat(@Header("Authorization") String token,
                                      @Path("data") String data);
}
