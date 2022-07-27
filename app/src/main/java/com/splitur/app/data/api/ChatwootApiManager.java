package com.splitur.app.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.splitur.app.data.model.chatwoot_model.ConversationModel;
import com.splitur.app.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public class ChatwootApiManager {

    public static final String CHAT_URL= "https://app.chatwoot.com/";

    private static Retrofit retrofit;

    private static ChatApiService sRestApi = null;

    //function to get one time service
    public static ChatApiService getRestApiService() {
        if (sRestApi == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(15, TimeUnit.SECONDS);
            httpClient.connectTimeout(15, TimeUnit.SECONDS);
            httpClient.addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY));

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CHAT_URL)
//                  .baseUrl("https://testserver.glorek.com/api/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
//                  .addConverterFactory(NullOrEmptyConverterFactory())
                    .client(httpClient.build())
                    .build();

            sRestApi = retrofit.create(ChatApiService.class);
        }
        return sRestApi;
    }

}
