package split.com.app.ui.main.viewmodel.phone_number;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.RegisterModel;
import split.com.app.data.model.get_avatar.AvatarModel;
import split.com.app.data.model.phone_number.NumberModel;
import split.com.app.data.repository.avatar.AvatarRepository;
import split.com.app.data.repository.otp_number.PhoneNumberRepository;

public class PhoneNumberViewModel extends ViewModel {

    public ObservableField<String> number = new ObservableField<>("");
    //public ObservableField<String> code = new ObservableField<>("");


    private MutableLiveData<NumberModel> data;
    private PhoneNumberRepository numberRepository;

    public PhoneNumberViewModel() {
        numberRepository = new PhoneNumberRepository();
    }

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
      //  String phone_number = code.get() + number.get();
        data = numberRepository.checkPhoneNumber(number);
    }

    public MutableLiveData<NumberModel> getData() {
        return this.data;
    }


}
