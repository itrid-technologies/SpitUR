package com.splitur.app.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatwootApiManager {

    //    public static final String CHAT_URL= "https://app.chatwoot.com/";
    public static final String CHAT_URL = "https://unream.com/";

    private static Retrofit retrofit;

    private static ChatApiService sRestApi = null;

    //function to get one time service
    public static ChatApiService getRestApiService() {
        if (sRestApi == null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CHAT_URL)
//                  .baseUrl("https://testserver.glorek.com/api/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
//                  .addConverterFactory(NullOrEmptyConverterFactory())
                    .client(getUnsafeOkHttpClient())
                    .build();

            sRestApi = retrofit.create(ChatApiService.class);
        }
        return sRestApi;
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {

            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.readTimeout(15, TimeUnit.SECONDS);
            builder.connectTimeout(15, TimeUnit.SECONDS);
            builder.addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY));
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier((hostname, session) -> true);

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

