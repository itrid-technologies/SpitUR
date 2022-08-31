package com.splitur.app.ui.main.view.join_group_search;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.splitur.app.R;
import com.splitur.app.data.model.popular_subcategory.DataItem;
import com.splitur.app.databinding.FragmentJoinSearchBinding;
import com.splitur.app.ui.main.adapter.join_search.JoinSearchListAdapter;
import com.splitur.app.ui.main.adapter.search.SearchListAdapter;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.viewmodel.search_create_viewmodel.SearchCreateViewModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.Split;


public class JoinSearch extends Fragment {

    FragmentJoinSearchBinding binding;
    private SearchCreateViewModel mViewModel;
    private List<DataItem> list;
    String catID = null ;
    String category = null;
    private List<DataItem> popularSubCategoryList;
    private List<DataItem> pagingDetailItems;

    private int maxPageLimit;


    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    ProgressDialog progressDialog;
    int currentPage;
    Parcelable recyclerViewState;
    private boolean flag_loading , isApiHit;
    int nextPage = 1;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJoinSearchBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.jsToolbar.title.setText("Search");
        binding.customCreateView.customLayout.setVisibility(View.GONE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            catID = getArguments().getString("CurrentCatId");
            category = getArguments().getString("category_name");
        }
        pagingDetailItems = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);

//        if (category.isEmpty()){
//            getPopularSubCategory();
//        }else {
//            getSearchedData(category);
//        }

        getPopularSubCategory(catID,nextPage);


        initClickListeners();
        searchTextWatcher();

        binding.joinSearchList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerViewState = binding.joinSearchList.getLayoutManager().onSaveInstanceState(); // save recycleView state

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {

                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (flag_loading) {

                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            flag_loading = false;
                            nextPage++;

                            if (nextPage > maxPageLimit) {
                                Toast.makeText(requireContext(), "No more data!", Toast.LENGTH_SHORT).show();
                            } else {
                                getPopularSubCategory(catID,nextPage);
                                binding.progressBar.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                }
            }
        });


    }

    private void getPopularSubCategory(String catId, int page) {
        isApiHit = true;

        binding.customCreateView.customLayout.setVisibility(View.GONE);
        mViewModel = new SearchCreateViewModel(null, catId, String.valueOf(page));
        mViewModel.initPopularById();
        mViewModel.getPopularCategoryData().observe(getViewLifecycleOwner(), popularSubCategoryModel -> {
            if (popularSubCategoryModel.isSuccess()) {
                if (popularSubCategoryModel.getData().size() > 0) {

                    flag_loading = true;
                    isApiHit = false;

                    binding.joinPopular.setVisibility(View.VISIBLE);
                    pagingDetailItems.addAll(popularSubCategoryModel.getData());
                    maxPageLimit = popularSubCategoryModel.getPage();
                    buildCategoryRv(pagingDetailItems);

                } else {
                    binding.joinPopular.setVisibility(View.GONE);
                    binding.customCreateView.customLayout.setVisibility(View.VISIBLE);
                }
            } else {
                binding.joinPopular.setVisibility(View.GONE);
            }


        });
    }

    private void searchTextWatcher() {
        binding.joinSearchView.searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    binding.joinSearchView.removeLetter.setVisibility(View.VISIBLE);
//                    binding.joinSearchField.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(Split.getAppContext(), R.drawable.search_icon), null, ContextCompat.getDrawable(Split.getAppContext(), R.drawable.ic_close), null);
                } else {
                    if (!isApiHit){
                        pagingDetailItems.clear();
                        getPopularSubCategory(catID, 1);
                    }
                    binding.customCreateView.customLayout.setVisibility(View.GONE);
                    binding.joinSearchView.removeLetter.setVisibility(View.GONE);
//                    binding.joinSearchField.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(Split.getAppContext(), R.drawable.search_icon), null, null, null);
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
        if (catID != null) {

            mViewModel = new SearchCreateViewModel(data, catID, String.valueOf(nextPage));
            mViewModel.initSearchByCat();
            mViewModel.getCatSearchData().observe(getViewLifecycleOwner(), searchSubCatModel -> {
                if (searchSubCatModel.isSuccess()) {
                    list = new ArrayList<>();
                    list.addAll(searchSubCatModel.getData());
                    buildCategoryRv(list);
                }
            });

        } else {

            mViewModel = new SearchCreateViewModel(data, null, String.valueOf(nextPage));
            mViewModel.initSearch();
            mViewModel.getSearchData().observe(getViewLifecycleOwner(), searchSubCatModel -> {

                if (searchSubCatModel.isSuccess()) {
                    if (searchSubCatModel.getData().size() == 0) {
                        binding.customCreateView.customLayout.setVisibility(View.VISIBLE);
                        binding.joinSearchList.setVisibility(View.GONE);

                    } else {
                        binding.joinSearchList.setVisibility(View.VISIBLE);
                        binding.customCreateView.customLayout.setVisibility(View.GONE);
                        list = new ArrayList<>();
                        list.addAll(searchSubCatModel.getData());
                        buildCategoryRv(list);
                    }
                }
//                if (searchSubCatModel.isSuccess()) {
//                    list = new ArrayList<>();
//                    list.addAll(searchSubCatModel.getData());
//                    buildCategoryRv(list);
//                }
            });
        }

    }

    private void initClickListeners() {
        binding.jsToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.joinSearchView.removeLetter.setOnClickListener(view -> {
            binding.joinSearchView.searchField.setText("");
            getPopularSubCategory(catID, nextPage);
        });

        binding.customCreateView.customCreate.setOnClickListener(view -> {
            Constants.SUB_CAT_TITLE = "Custom Services";
            Navigation.findNavController(requireView()).navigate(R.id.action_joinSearch_to_subscriptionName);
        });
    }

    private void buildCategoryRv(List<DataItem> popularSubCategoryList) {
        binding.progressBar.setVisibility(View.GONE);

        binding.joinSearchList.setVisibility(View.VISIBLE);
        binding.customCreateView.customLayout.setVisibility(View.GONE);


        binding.joinSearchList.setLayoutManager(mLayoutManager);
        JoinSearchListAdapter adapter = new JoinSearchListAdapter(Split.getAppContext(), popularSubCategoryList);
        adapter.notifyDataSetChanged();
        binding.joinSearchList.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        binding.joinSearchList.setAdapter(adapter);

        adapter.setOnCreateClickListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putString("join_sub_cat_id", String.valueOf(popularSubCategoryList.get(position).getId()));
            Navigation.findNavController(requireView()).navigate(R.id.action_joinSearch_to_groupDetail, bundle);
        });
    }
}