package com.splitur.app.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.splitur.app.utils.Constants;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class ApiManager {
    //http://192.168.100.19:4000/http://3.6.7.161:4000/
    public static final String URL_BASE= "http://famger.com:4000/";

    private static Retrofit retrofit;

    private static ApiService sRestApi = null;

    //function to get one time service
    public static ApiService getRestApiService() {
        if (sRestApi == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(15, TimeUnit.SECONDS);
            httpClient.connectTimeout(15, TimeUnit.SECONDS);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);


            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.URL_BASE)
//                  .baseUrl("https://testserver.glorek.com/api/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
//                  .addConverterFactory(NullOrEmptyConverterFactory())
                    .client(getUnsafeOkHttpClient())
                    .build();

            sRestApi = retrofit.create(ApiService.class);
        }
        return sRestApi;
    }//end get

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


//            SSLSocketFactory sf = new CustomSSLSocketFactory(trustStore);
//            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            builder.connectTimeout(6000, TimeUnit.SECONDS)
                    .readTimeout(6000, TimeUnit.SECONDS)
                    .writeTimeout(6000, TimeUnit.SECONDS).connectionPool(new ConnectionPool(50, 50000, TimeUnit.SECONDS))
                    .addInterceptor(logging);

            return builder.build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    public static Retrofit getRetrofitInstance() {
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(URL_BASE)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//        }
//        return retrofit;
//    }
//    public static Retrofit getClientAuthentication() {
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//// set your desired log level
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl(URL_BASE);
//        AuthenticationInterceptor interceptor = new AuthenticationInterceptor("");
//        if (!httpClient.interceptors().contains(interceptor)) {
//            httpClient.addInterceptor(interceptor);
//            httpClient.addInterceptor(logging);
//            builder.client(httpClient.build());
//            retrofit = builder
//                     .baseUrl(URL_BASE)
//                    .addConverterFactory(ScalarsConverterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(httpClient.build())
//                    .build();
//
//        }
//
//        return retrofit;
//    }
//
//    public static class AuthenticationInterceptor implements Interceptor {
//
//        private String authToken;
//
//        public AuthenticationInterceptor(String token) {
//            this.authToken = token;
//        }
//
//        @NonNull
//        @Override
//        public okhttp3.Response intercept(Chain chain) throws IOException {
//            Request original = chain.request();
//
//            Request.Builder builder = original.newBuilder()
//                    .header("Authorization", authToken);//Remember header() vs addHeader
//
//            Request request = builder.build();
//            return chain.proceed(request);
//        }
//    }
}
