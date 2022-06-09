package split.com.app.ui.main.viewmodel.search_create_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.popular_subcategory.PopularSubCategoryModel;
import split.com.app.data.repository.create_search.SearchCreateRepository;

public class SearchCreateViewModel extends ViewModel {

    private MutableLiveData<PopularSubCategoryModel> data;
    private SearchCreateRepository createRepository;

    public SearchCreateViewModel() {
        createRepository = new SearchCreateRepository();
    }

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = createRepository.getPopularCategories();
    }

    public MutableLiveData<PopularSubCategoryModel> getPopularCategoryData() {
        return this.data;
    }


}
