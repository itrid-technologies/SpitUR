package com.splitur.app.data.api;

import com.google.gson.JsonObject;
import com.splitur.app.data.model.ChatWootAccountIdModel;
import com.splitur.app.data.model.GetScoreModel;
import com.splitur.app.data.model.HomeContentModel;
import com.splitur.app.data.model.SecretKeyModel;
import com.splitur.app.data.model.TransactionsModel;
import com.splitur.app.data.model.active_user.ActiveUserModel;
import com.splitur.app.data.model.all_created_groupx.AllCreatedGroupModel;
import com.splitur.app.data.model.all_joined_groups.AllJoinedGroupModel;
import com.splitur.app.data.model.basic_model.BasicModel;
import com.splitur.app.data.model.basic_model.BasicModel1;
import com.splitur.app.data.model.create_group.CreateGroupModel;
import com.splitur.app.data.model.faq.FaqListModel;
import com.splitur.app.data.model.friend_list.FriendListModel;
import com.splitur.app.data.model.get_avatar.AvatarModel;
import com.splitur.app.data.model.getch_memeber_messages.GetMemberMessagesModel;
import com.splitur.app.data.model.group_detail.GroupDetailModel;
import com.splitur.app.data.model.group_member.GroupMemberModel;
import com.splitur.app.data.model.group_score.GroupScoreModel;
import com.splitur.app.data.model.home_categories.CategoriesModel;
import com.splitur.app.data.model.join_group.JoinGroupModel;
import com.splitur.app.data.model.otp_request.AllOtpRequestModel;
import com.splitur.app.data.model.otp_verification.AuthenticationModel;
import com.splitur.app.data.model.phone_number.NumberModel;
import com.splitur.app.data.model.plans.PlanModel;
import com.splitur.app.data.model.popular_subcategory.PopularSubCategoryModel;
import com.splitur.app.data.model.promo.PromoResponse;
import com.splitur.app.data.model.receive_message.GetMessagesModel;
import com.splitur.app.data.model.register.RegisterModel;
import com.splitur.app.data.model.send_message.MessageSendModel;
import com.splitur.app.data.model.settings.SettingsResponse;
import com.splitur.app.data.model.total_coins.TotalCoinsModel;
import com.splitur.app.data.model.update_user_profile.UserUpdateModel;
import com.splitur.app.data.model.wallet_balance.WalletBalanceModel;

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

public interface ApiService {


    @GET("users/getAvatar")
    Call<AvatarModel> getAvatar();

    @GET("groups/get_urls")
    Call<SettingsResponse> getSettings();

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

    @GET("users/is_email_exist/{Id}")
    Call<BasicModel> checkUserEmailExistence(@Path("Id") String Id);

    @GET("groups/get_categories")
    Call<CategoriesModel> getAllCategories(@Header("Authorization") String token);

    @POST("groups/get_pouplar_sub_categories")
    Call<PopularSubCategoryModel> getPopularCategoriesHome(@Body JsonObject object);

    @POST("groups/get_pouplar_sub_categories")
    Call<PopularSubCategoryModel> getPopularCategoriesById(@Body JsonObject object);

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
    Call<GroupDetailModel> getGroupDetails(@Header("Authorization") String token,
                                           @Body JsonObject object);

    @POST("groups/group_details_search")
    Call<GroupDetailModel> getGroupDetailsSearch(@Header("Authorization") String token,
                                                 @Body JsonObject object);

    @POST("groups/join_group")
    Call<JoinGroupModel> joinGroup(@Header("Authorization") String token,
                                   @Body JsonObject object);

    @POST("groups/group_payment")
    Call<JsonObject> sendPaymentSuccess(@Body JsonObject object);

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

    @POST("messages/getMessages")
    Call<GetMemberMessagesModel> getMessages(@Header("Authorization") String token,
                                             @Body JsonObject object);


    @POST("groups/remove_member")
    Call<BasicModel1> removeMember(@Header("Authorization") String token,
                                   @Body JsonObject object);

    @POST("groups/update_group/{id}")
    Call<BasicModel1> updateGroup(@Header("Authorization") String token,
                                  @Path("id") String id,
                                  @Body JsonObject object);

    @POST("groups/update_group/{id}")
    Call<BasicModel> updateGroupVisibility(@Header("Authorization") String token,
                                           @Path("id") String id,
                                           @Body JsonObject object);

    @PUT("users/update")
    Call<UserUpdateModel> updateProfile(@Header("Authorization") String token,
                                        @Body JsonObject object);

    @GET("faqs/getFAQs")
    Call<FaqListModel> faqList(@Header("Authorization") String token);

    @GET("groups/get_all_users")
    Call<FriendListModel> friendList(@Header("Authorization") String token);

    @POST("groups/add_split_score")
    Call<BasicModel> sendScore(@Header("Authorization") String token,
                               @Body JsonObject object);

    @GET("groups/get_posted_score/{id}")
    Call<GetScoreModel> getScore(@Header("Authorization") String token,
                                 @Path("id") String id);


    @GET("groups/otp_request/{id}")
    Call<BasicModel> request_otp(@Header("Authorization") String token,
                                 @Path("id") String id);

    @GET("groups/getGroupAmount/{id}")
    Call<JsonObject> getGroupAmount(@Path("id") int groupId);


    @GET("groups/left_group/{id}")
    Call<BasicModel1> leftGroup(@Header("Authorization") String token,
                                @Path("id") String id);

    @POST("users/swap_coins")
    Call<BasicModel> swapCoins(@Header("Authorization") String token,
                               @Body JsonObject object);

    @GET("users/lastActive/{id}")
    Call<JsonObject> updateLastActive(@Path("id") int userId);

    @GET("users/wallet_balance/{id}")
    Call<WalletBalanceModel> getWalletBalance(@Header("Authorization") String token,
                                              @Path("id") String id);

    @GET("groups/get_group_score/{id}")
    Call<GroupScoreModel> getGroupScore(@Header("Authorization") String token,
                                        @Path("id") String id);

    @GET("users/total_coins/{id}")
    Call<TotalCoinsModel> getTotalCoins(@Header("Authorization") String token,
                                        @Path("id") String id);

    @POST("users/refund")
    Call<BasicModel1> refundAmount(@Header("Authorization") String token,
                                   @Body JsonObject object);

    @GET("users/current")
    Call<ActiveUserModel> getUserData(@Header("Authorization") String token);

    @GET("users/logout/{id}")
    Call<BasicModel1> logoutUser(@Header("Authorization") String token,
                                 @Path("id") String id);

    @GET("groups/get_payment_transaction")
    Call<TransactionsModel> getAllTransactions(@Header("Authorization") String token);

    @GET("promotions/get_promo_code_details/{code}")
    Call<PromoResponse> applyPromoCode(@Path("code") String code);

    @GET("groups/get_razor_pay_secret")
    Call<SecretKeyModel> getRozarSecretKey();

    @GET("users/user_active_status/{status}")
    Call<BasicModel1> setUserOnlineStatus(@Header("Authorization") String token,
                                          @Path("status") int status);

    @POST("refer/referal")
    Call<BasicModel1> sendReferral(@Header("Authorization") String token,
                                   @Body JsonObject object);


    @GET("groups/get_account_id")
    Call<ChatWootAccountIdModel> getAccountId();

    @GET("groups/getAllOtpRequests/{group_id}")
    Call<AllOtpRequestModel> getAllOtpRequests(@Header("Authorization") String token,
                                               @Path("group_id") String group_id);
}
