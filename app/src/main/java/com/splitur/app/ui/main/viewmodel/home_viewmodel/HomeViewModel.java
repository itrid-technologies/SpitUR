package com.splitur.app.ui.main.viewmodel.home_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.HomeContentModel;
import com.splitur.app.data.model.popular_subcategory.PopularSubCategoryModel;
import com.splitur.app.data.repository.create_search.SearchCreateRepository;
import com.splitur.app.data.repository.home.HomeContentRepository;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<PopularSubCategoryModel> popularData;

    private MutableLiveData<HomeContentModel> home_data;
    private HomeContentRepository homeContentRepository;
    private SearchCreateRepository searchCreateRepository;

    public HomeViewModel() {
        homeContentRepository = new HomeContentRepository();
        searchCreateRepository = new SearchCreateRepository();
    }

    public void init() {
        if (this.home_data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        home_data = homeContentRepository.getHomeContent();
    }

    public void initPopular() {
        if (this.popularData != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        popularData = searchCreateRepository.getPopularCategories();
    }

    public MutableLiveData<HomeContentModel> getHomeData() {
        return this.home_data;
    }

    public MutableLiveData<PopularSubCategoryModel> getPopularCategoryData() {
        return this.popularData;
    }

}
