package split.com.app.ui.main.view.join_group_search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import split.com.app.R;
import split.com.app.data.model.popular_subcategory.DataItem;
import split.com.app.databinding.FragmentJoinSearchBinding;
import split.com.app.ui.main.adapter.join_search.JoinSearchListAdapter;
import split.com.app.ui.main.adapter.search.SearchListAdapter;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.viewmodel.search_create_viewmodel.SearchCreateViewModel;
import split.com.app.utils.NotificationsUtils;
import split.com.app.utils.Split;


public class JoinSearch extends Fragment {

   FragmentJoinSearchBinding binding;
    private SearchCreateViewModel mViewModel;
    private List<DataItem> list;
    String catID = null;
    private List<DataItem> popularSubCategoryList;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJoinSearchBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.jsToolbar.title.setText("Search");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null){
            catID = getArguments().getString("CurrentCatId");
        }

        getPopularSubCategory();

        initClickListeners();
        searchTextWatcher();
    }

    private void getPopularSubCategory() {

        mViewModel = new SearchCreateViewModel(null,null);
        mViewModel.init();
        mViewModel.getPopularCategoryData().observe(getViewLifecycleOwner(), popularSubCategoryModel -> {
            if (popularSubCategoryModel.isSuccess()) {
                if (popularSubCategoryModel.getData().size() > 0) {
                    binding.joinPopular.setVisibility(View.VISIBLE);
                    popularSubCategoryList = new ArrayList<>();
                    popularSubCategoryList.addAll(popularSubCategoryModel.getData());
                    buildCategoryRv(popularSubCategoryList);

                }else {
                    binding.joinPopular.setVisibility(View.GONE);
                }
            }else {
                binding.joinPopular.setVisibility(View.GONE);
            }


        });
    }

    private void searchTextWatcher() {
        binding.joinSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    binding.joinSearchField.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(Split.getAppContext(), R.drawable.search_icon), null, ContextCompat.getDrawable(Split.getAppContext(), R.drawable.ic_close), null);
                } else {
                    binding.joinSearchField.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(Split.getAppContext(), R.drawable.search_icon), null, null, null);
                  //  getPopularSubCategory();

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable data) {

                if (!data.toString().isEmpty()) {
                    getSearchedData(data.toString());
                }
            }
        });
    }

    private void getSearchedData(String data) {
        if (catID != null){

            mViewModel = new SearchCreateViewModel(data,catID);
            mViewModel.initSearchByCat();
            mViewModel.getCatSearchData().observe(getViewLifecycleOwner(), searchSubCatModel -> {
                if (searchSubCatModel.isSuccess()) {
                    list = new ArrayList<>();
                    list.addAll(searchSubCatModel.getData());
                    buildCategoryRv(list);
                }

            });

        }else {

            mViewModel = new SearchCreateViewModel(data,null);
            mViewModel.initSearch();
            mViewModel.getSearchData().observe(getViewLifecycleOwner(), searchSubCatModel -> {
                if (searchSubCatModel.isSuccess()) {
                    list = new ArrayList<>();
                    list.addAll(searchSubCatModel.getData());
                    buildCategoryRv(list);
                }
            });
        }

    }

    private void initClickListeners() {
        binding.jsToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });
    }

    private void buildCategoryRv(List<DataItem> popularSubCategoryList) {

        binding.joinSearchList.setVisibility(View.VISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.joinSearchList.setLayoutManager(layoutManager);
        JoinSearchListAdapter adapter = new JoinSearchListAdapter(Split.getAppContext(), popularSubCategoryList);
        binding.joinSearchList.setAdapter(adapter);

        adapter.setOnCreateClickListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putString("join_sub_cat_id",String.valueOf(popularSubCategoryList.get(position).getId()));
            Navigation.findNavController(requireView()).navigate(R.id.action_joinSearch_to_groupDetail,bundle);
        });
    }
}