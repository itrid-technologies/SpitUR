package split.com.app.ui.main.viewmodel.search_create_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.popular_subcategory.PopularSubCategoryModel;
import split.com.app.data.model.search_sub_category.SearchSubCatModel;
import split.com.app.data.repository.create_search.SearchCreateRepository;

public class SearchCreateViewModel extends ViewModel {

    private MutableLiveData<PopularSubCategoryModel> data;
    private MutableLiveData<PopularSubCategoryModel> searchData;

    private SearchCreateRepository searchCreateRepository;
    String value;

    public SearchCreateViewModel(String data) {
        this.value = data;
        searchCreateRepository = new SearchCreateRepository();
    }

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = searchCreateRepository.getPopularCategories();
    }

    public void initSearch() {
        if (this.searchData != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        searchData = searchCreateRepository.getSearchedSubCategory(value);
    }

    public MutableLiveData<PopularSubCategoryModel> getPopularCategoryData() {
        return this.data;
    }

    public MutableLiveData<PopularSubCategoryModel> getSearchData() {
        return this.searchData;
    }



}
