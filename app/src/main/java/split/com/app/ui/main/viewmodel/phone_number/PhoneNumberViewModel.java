package split.com.app.ui.main.viewmodel.phone_number;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.phone_number.NumberModel;
import split.com.app.data.model.basic_model.BasicModel;
import split.com.app.data.repository.otp_number.PhoneNumberRepository;

public class PhoneNumberViewModel extends ViewModel {

    public ObservableField<String> number = new ObservableField<>();
    String phone_number;


    private MutableLiveData<NumberModel> data;
    private MutableLiveData<BasicModel> otp_data;

    private PhoneNumberRepository numberRepository;

    public PhoneNumberViewModel(String number_) {
        this.phone_number = number_;
        numberRepository = new PhoneNumberRepository();
    }


    public void init() {
        if (this.data != null) {

            return;
        }else {
            data = numberRepository.checkPhoneNumber(phone_number);
        }
    }

    public void initOtp() {
        if (this.otp_data != null) {

            return;
        }else {
            otp_data = numberRepository.sendOtp(phone_number);
        }
    }

    public MutableLiveData<NumberModel> getData() {
        return this.data;
    }

    public MutableLiveData<BasicModel> getOtp_data() {
        return otp_data;
    }
}
