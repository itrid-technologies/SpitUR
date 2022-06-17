package split.com.app.ui.main.view.search_create;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import split.com.app.R;
import split.com.app.data.model.popular_subcategory.DataItem;
import split.com.app.databinding.FragmentSearchBinding;
import split.com.app.ui.main.adapter.search.SearchListAdapter;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.viewmodel.search_create_viewmodel.SearchCreateViewModel;
import split.com.app.utils.Split;


public class Search extends Fragment {

    FragmentSearchBinding binding;
    private SearchCreateViewModel mViewModel;
    private List<DataItem> popularSubCategoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        Dashboard.hideNav(false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//      popularSubCategoryList = new ArrayList<>();

        mViewModel = new SearchCreateViewModel(null);
        getPopularSubCategory();
        initClickListeners();
        searchTextWatcher();
    }

    private void getPopularSubCategory() {
        popularSubCategoryList = new ArrayList<>();

        mViewModel.init();
        mViewModel.getPopularCategoryData().observe(getViewLifecycleOwner(), popularSubCategoryModel -> {
            if (popularSubCategoryModel.isSuccess()) {
                if (popularSubCategoryModel.getData().size() > 0) {
                    popularSubCategoryList.addAll(popularSubCategoryModel.getData());
                }

                buildCategoryRv();
            }
        });
    }


    private void searchTextWatcher() {
        binding.searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    binding.searchField.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(Split.getAppContext(), R.drawable.search_icon), null, ContextCompat.getDrawable(Split.getAppContext(), R.drawable.ic_close), null);
                } else {
                    binding.searchField.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(Split.getAppContext(), R.drawable.search_icon), null, null, null);
                    getPopularSubCategory();

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable data) {

                getSearchedData(data.toString());

            }
        });
    }

    private void getSearchedData(String data) {
        mViewModel = new SearchCreateViewModel(data);
        mViewModel.initSearch();
        mViewModel.getSearchData().observe(getViewLifecycleOwner(), searchSubCatModel -> {
            if (searchSubCatModel.isSuccess()) {
                popularSubCategoryList = new ArrayList<>();
                popularSubCategoryList.addAll(searchSubCatModel.getData());
                buildCategoryRv();
            }
        });
    }

    private void initClickListeners() {
        binding.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });
    }

    private void buildCategoryRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.searchLis.setLayoutManager(layoutManager);
        SearchListAdapter adapter = new SearchListAdapter(Split.getAppContext(), popularSubCategoryList);
        binding.searchLis.setAdapter(adapter);
    }

}