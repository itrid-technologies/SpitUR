package com.splitur.app.ui.main.viewmodel.category_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.home_categories.CategoriesModel;
import com.splitur.app.data.repository.categories.CategoryRepository;

public class CategoryViewModel extends ViewModel {


    private MutableLiveData<CategoriesModel> category_data;
    private CategoryRepository categoryRepository;

    public CategoryViewModel() {
        categoryRepository = new CategoryRepository();
    }

    public void init() {
        if (this.category_data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        category_data = categoryRepository.getCategories();
    }

    public MutableLiveData<CategoriesModel> getCategoryData() {
        return this.category_data;
    }


}
