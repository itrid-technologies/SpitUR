package split.com.app.ui.main.viewmodel.home_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.home_categories.CategoriesModel;
import split.com.app.data.model.otp_verification.AuthenticationModel;
import split.com.app.data.repository.home.HomeRepository;
import split.com.app.data.repository.otp_verification.OtpVerificationRepository;

public class HomeViewModel extends ViewModel {


    private MutableLiveData<CategoriesModel> category_data;
    private HomeRepository homeRepository;

    public HomeViewModel() {
        homeRepository = new HomeRepository();
    }

    public void init() {
        if (this.category_data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        category_data = homeRepository.getCategories();
    }

    public MutableLiveData<CategoriesModel> getCategoryData() {
        return this.category_data;
    }


}
