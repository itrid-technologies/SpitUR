package com.splitur.app.data.api;

import com.google.gson.JsonObject;
import com.splitur.app.data.model.chatwoot_model.ConversationModel;
import com.splitur.app.data.model.chatwoot_model.MessagesModel;
import com.splitur.app.data.model.chatwoot_model.SendSupportMessageModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChatApiService {

    @POST("api/v1/accounts/73801/conversations")
    Call<ConversationModel> createConversation(@Header("api_access_token") String apiKey,
                                               @Body JsonObject object);


    @GET("api/v1/accounts/73801/conversations/{conversation_id}/messages")
    Call<MessagesModel> getSupportChat(@Header("api_access_token") String apiKey,
                                       @Path("conversation_id") int conversation_id);

    @POST("api/v1/accounts/73801/conversations/{conversation_id}/messages")
    Call<SendSupportMessageModel> sendSupportQuery(@Header("api_access_token") String apiKey,
                                                   @Path("conversation_id") int conversation_id,
                                                   @Body JsonObject object);
}
