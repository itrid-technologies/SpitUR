package split.com.app.ui.main.viewmodel.home_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.HomeContentModel;
import split.com.app.data.model.home_categories.CategoriesModel;
import split.com.app.data.repository.categories.CategoryRepository;
import split.com.app.data.repository.home.HomeContentRepository;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<HomeContentModel> home_data;
    private HomeContentRepository homeContentRepository;

    public HomeViewModel() {
        homeContentRepository = new HomeContentRepository();
    }

    public void init() {
        if (this.home_data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        home_data = homeContentRepository.getHomeContent();
    }

    public MutableLiveData<HomeContentModel> getHomeData() {
        return this.home_data;
    }

}
