package split.com.app.data.api;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import split.com.app.utils.Constants;


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
            httpClient.addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY));

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.URL_BASE)
//                  .baseUrl("https://testserver.glorek.com/api/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
//                  .addConverterFactory(NullOrEmptyConverterFactory())
                    .client(httpClient.build())
                    .build();

            sRestApi = retrofit.create(ApiService.class);
        }
        return sRestApi;
    }//end get

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
