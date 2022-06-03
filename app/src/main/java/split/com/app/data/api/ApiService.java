package split.com.app.data.api;

import androidx.databinding.ObservableField;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import split.com.app.data.model.RegisterModel;
import split.com.app.data.model.get_avatar.AvatarModel;
import split.com.app.data.model.phone_number.NumberModel;

public interface ApiService {

    @GET("users/getAvatar")
    Call<AvatarModel> getAvatar();

    @FormUrlEncoded
    @POST("users/phoneAlreadyExist")
    Call<NumberModel> checkNumberExistence(@Field("phone") ObservableField<String> number);


}
