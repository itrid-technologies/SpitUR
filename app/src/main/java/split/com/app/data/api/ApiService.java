package split.com.app.data.api;

import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import split.com.app.data.model.HomeContentModel;
import split.com.app.data.model.all_created_groupx.AllCreatedGroupModel;
import split.com.app.data.model.all_joined_groups.AllJoinedGroupModel;
import split.com.app.data.model.basic_model.BasicModel;
import split.com.app.data.model.basic_model.BasicModel1;
import split.com.app.data.model.create_group.CreateGroupModel;
import split.com.app.data.model.faq.FaqListModel;
import split.com.app.data.model.friend_list.FriendListModel;
import split.com.app.data.model.get_avatar.AvatarModel;
import split.com.app.data.model.getch_memeber_messages.GetMemberMessagesModel;
import split.com.app.data.model.group_detail.GroupDetailModel;
import split.com.app.data.model.group_member.GroupMemberModel;
import split.com.app.data.model.home_categories.CategoriesModel;
import split.com.app.data.model.join_group.JoinGroupModel;
import split.com.app.data.model.otp_verification.AuthenticationModel;
import split.com.app.data.model.phone_number.NumberModel;
import split.com.app.data.model.plans.PlanModel;
import split.com.app.data.model.popular_subcategory.PopularSubCategoryModel;
import split.com.app.data.model.receive_message.GetMessagesModel;
import split.com.app.data.model.register.RegisterModel;
import split.com.app.data.model.send_message.MessageSendModel;
import split.com.app.data.model.total_coins.TotalCoinsModel;
import split.com.app.data.model.update_user_profile.UserUpdateModel;
import split.com.app.data.model.wallet_balance.WalletBalanceModel;

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
    Call<AuthenticationModel> authenticateUser(@Field("fcm_token") String token,
                                               @Field("phone") String number,
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

    @FormUrlEncoded
    @POST("groups/create_custom_group")
    Call<BasicModel> createCustomGroup(@Header("Authorization") String token,
                                             @FieldMap Map<String, String> options);

    @GET("groups/get_home_content")
    Call<HomeContentModel> getHomeData(@Header("Authorization") String token);

    @POST("groups/group_details_search")
    Call<GroupDetailModel> getGroupDetails(@Body JsonObject object);

    @POST("groups/group_details_search")
    Call<GroupDetailModel> getGroupDetailsSearch(@Body JsonObject object);

    @POST("groups/join_group")
    Call<JoinGroupModel> joinGroup(@Header("Authorization") String token,
                                   @Body JsonObject object);

    @GET("groups/get_joined_groups")
    Call<AllJoinedGroupModel> getJoinedGroups(@Header("Authorization") String token);

    @GET("groups/admin_group_list")
    Call<AllCreatedGroupModel> getCreatedGroups(@Header("Authorization") String token);

    @GET("groups/search_sub_categories/{data}")
    Call<PopularSubCategoryModel> getsubCat(@Header("Authorization") String token,
                                            @Path("data") String data);

    @POST("groups/cat_search/{data}")
    Call<PopularSubCategoryModel> getSubCatByCatId(@Header("Authorization") String token,
                                                   @Path("data") String data,
                                                   @Body JsonObject object);


    @GET("groups/get_group_members/{id}")
    Call<GroupMemberModel> getGroupMembers(@Header("Authorization") String token,
                                           @Path("id") String id);

    @GET("groups/delete_group/{id}")
    Call<BasicModel1> deleteGroup(@Header("Authorization") String token,
                                 @Path("id") String id);

    @POST("messages/sendMessageGroupChat")
    Call<MessageSendModel> sendGroupMessage(@Header("Authorization") String token,
                                            @Body JsonObject object);

    @GET("messages/getMessagesGroupChat/{id}")
    Call<GetMessagesModel> getGroupMessages(@Header("Authorization") String token,
                                            @Path("id") String id);


    @POST("messages/sendMessage")
    Call<MessageSendModel> sendMessage(@Header("Authorization") String token,
                                       @Body JsonObject object);

    @GET("messages/getMessages/{id}")
    Call<GetMemberMessagesModel> getMessages(@Header("Authorization") String token,
                                             @Path("id") String id);


    @POST("groups/remove_member")
    Call<BasicModel1> removeMember(@Header("Authorization") String token,
                                   @Body JsonObject object);

    @POST("groups/update_group/{id}")
    Call<BasicModel> updateGroup(@Header("Authorization") String token,
                                 @Path("id") String id,
                                 @Body JsonObject object);

    @PUT("users/update")
    Call<UserUpdateModel> updateProfile(@Header("Authorization") String token,
                                        @Body JsonObject object);

    @GET("faqs/getFAQs")
    Call<FaqListModel> faqList(@Header("Authorization") String token);

    @GET("users/getfriendlist/{data}")
    Call<FriendListModel> friendList(@Header("Authorization") String token,
                                     @Path("data") String data);

    @POST("groups/add_split_score")
    Call<BasicModel> sendScore(@Header("Authorization") String token,
                               @Body JsonObject object);


    @GET("groups/otp_request/{id}")
    Call<BasicModel> request_otp(@Header("Authorization") String token,
                                 @Path("id") String id);


    @GET("groups/left_group/{id}")
    Call<BasicModel1> leftGroup(@Header("Authorization") String token,
                                 @Path("id") String id);

    @POST("users/swap_coins")
    Call<BasicModel> swapCoins(@Header("Authorization") String token,
                               @Body JsonObject object);


    @GET("users/wallet_balance/{id}")
    Call<WalletBalanceModel> getWalletBalance(@Header("Authorization") String token,
                                              @Path("id") String id);

    @GET("users/total_coins/{id}")
    Call<TotalCoinsModel> getTotalCoins(@Header("Authorization") String token,
                                        @Path("id") String id);

    @POST("users/refund")
    Call<BasicModel1> refundAmount(@Header("Authorization") String token,
                                  @Body JsonObject object);
}
