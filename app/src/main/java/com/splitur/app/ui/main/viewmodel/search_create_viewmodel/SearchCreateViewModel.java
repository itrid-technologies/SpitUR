package com.splitur.app.ui.main.viewmodel.search_create_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.popular_subcategory.PopularSubCategoryModel;
import com.splitur.app.data.repository.create_search.SearchCreateRepository;

public class SearchCreateViewModel extends ViewModel {

//    private MutableLiveData<PopularSubCategoryModel> data;
    private MutableLiveData<PopularSubCategoryModel> searchData;
    private MutableLiveData<PopularSubCategoryModel> catSearchData;
    private MutableLiveData<PopularSubCategoryModel> popularByCatId;

    private SearchCreateRepository searchCreateRepository;
    String value, cat_id,pageNo;

    public SearchCreateViewModel(String data, String id, String page) {
        this.value = data;
        this.cat_id = id;
        this.pageNo = page;
        searchCreateRepository = new SearchCreateRepository();
    }

//    public void init() {
//        if (this.data != null) {
//            // ViewModel is created per Fragment so
//            // we know the userId won't change
//            return;
//        }
//        data = searchCreateRepository.getPopularCategories();
//    }

    public void initSearch() {
        if (this.searchData != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        searchData = searchCreateRepository.getSearchedSubCategory(value,pageNo);
    }

    public void initSearchByCat() {
        if (this.catSearchData != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        catSearchData = searchCreateRepository.getSearchedSubCategoryByCatId(value,cat_id,pageNo);
    }

    public void initPopularById() {
        if (this.popularByCatId != null) {
            return;
        }
        popularByCatId = searchCreateRepository.getPopularCategoriesById(cat_id,pageNo);
    }

    public MutableLiveData<PopularSubCategoryModel> getPopularCategoryData() {
//        return this.data;
        return this.popularByCatId;
    }

    public MutableLiveData<PopularSubCategoryModel> getSearchData() {
        return this.searchData;
    }

    public MutableLiveData<PopularSubCategoryModel> getCatSearchData() {
        return this.catSearchData;
    }


}
