package com.splitur.app.ui.main.view.search_create;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.splitur.app.R;
import com.splitur.app.data.model.popular_subcategory.DataItem;
import com.splitur.app.databinding.FragmentSearchBinding;
import com.splitur.app.ui.main.adapter.search.SearchListAdapter;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.viewmodel.plan_viewmodel.PlansViewModel;
import com.splitur.app.ui.main.viewmodel.search_create_viewmodel.SearchCreateViewModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.Split;

import java.util.ArrayList;
import java.util.List;


public class Search extends Fragment {

    FragmentSearchBinding binding;
    private SearchCreateViewModel mViewModel;
    private List<DataItem> popularSubCategoryList;
    private List<DataItem> pagingDetailItems;

    private PlansViewModel plansViewModel;
    private int maxPageLimit;


    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    ProgressDialog progressDialog;
    int currentPage;
    Parcelable recyclerViewState;
    private boolean flag_loading , isApiHit;
    int nextPage = 1;
    private int searchDataCurrentPage = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        Dashboard.hideNav(false);

        binding.cSearchToolbar.title.setText("Search");
        binding.cSearchToolbar.back.setVisibility(View.GONE);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//      popularSubCategoryList = new ArrayList<>();
        pagingDetailItems = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);

        binding.loadingView.setVisibility(View.VISIBLE);

        getPopularSubCategory(nextPage);
        initClickListeners();
        searchTextWatcher();


        binding.searchLis.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerViewState = binding.searchLis.getLayoutManager().onSaveInstanceState(); // save recycleView state

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
                                getPopularSubCategory(nextPage);
                                binding.progressBar.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                }
            }
        });
    }

    private void getPopularSubCategory(int page) {
        isApiHit = true;

        binding.customCreateView.customLayout.setVisibility(View.GONE);

        mViewModel = new SearchCreateViewModel(null, null,String.valueOf(page));
        mViewModel.initPopularById();
        mViewModel.getPopularCategoryData().observe(getViewLifecycleOwner(), popularSubCategoryModel -> {
            if (popularSubCategoryModel.isSuccess()) {
                if (popularSubCategoryModel.getData().size() > 0) {

                    flag_loading = true;
                    isApiHit = false;


                    binding.popular.setVisibility(View.VISIBLE);
                  //  popularSubCategoryList = new ArrayList<>();
                    pagingDetailItems.addAll(popularSubCategoryModel.getData());
                    maxPageLimit = popularSubCategoryModel.getPage();
                    buildCategoryRv(pagingDetailItems);

                } else {
                    binding.popular.setVisibility(View.GONE);
                    binding.loadingView.setVisibility(View.GONE);

                    flag_loading = false;
                }
            } else {
                binding.popular.setVisibility(View.GONE);
                binding.loadingView.setVisibility(View.GONE);

            }
        });
    }


    @SuppressLint("ClickableViewAccessibility")
    private void searchTextWatcher() {
        binding.categorySearchView.searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    binding.categorySearchView.removeLetter.setVisibility(View.VISIBLE);
//                    binding.planSearchField.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(Split.getAppContext(), R.drawable.search_icon), null, ContextCompat.getDrawable(Split.getAppContext(), R.drawable.ic_close), null);
                } else {
                    if (!isApiHit){
                        pagingDetailItems.clear();
                        getPopularSubCategory(1);
                    }
                    binding.customCreateView.customLayout.setVisibility(View.GONE);
                    binding.categorySearchView.removeLetter.setVisibility(View.GONE);
                }
//                    binding.searchField.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(Split.getAppContext(), R.drawable.search_icon), null, null, null);
//                    binding.searchField.setOnTouchListener((v, event) -> {
//                        final int DRAWABLE_LEFT = 0;
//                        final int DRAWABLE_TOP = 1;
//                        final int DRAWABLE_RIGHT = 2;
//                        final int DRAWABLE_BOTTOM = 3;
//
//                        if (event.getAction() == MotionEvent.ACTION_UP) {
//                            if (event.getRawX() >= (binding.searchField.getRight() - binding.searchField.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                                // your action here
//                                binding.searchField.getText().clear();
//                                binding.searchField.clearFocus();
//                                getPopularSubCategory();
//                                return true;
//                            }
//                        }
//                        return false;
//                    });
//                } else {
//                    binding.searchField.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(Split.getAppContext(), R.drawable.search_icon), null, null, null);
//                    binding.searchField.setOnTouchListener(null);
//                    getPopularSubCategory();
//
//                }
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
        mViewModel = new SearchCreateViewModel(data, null, String.valueOf(nextPage));
        mViewModel.initSearch();
        mViewModel.getSearchData().observe(getViewLifecycleOwner(), searchSubCatModel -> {
            if (searchSubCatModel.isSuccess()) {
                if (searchSubCatModel.getData().size() == 0) {
                    binding.customCreateView.customLayout.setVisibility(View.VISIBLE);
                    binding.searchLis.setVisibility(View.GONE);

                } else {
                    binding.searchLis.setVisibility(View.VISIBLE);
                    binding.customCreateView.customLayout.setVisibility(View.GONE);
                    popularSubCategoryList = new ArrayList<>();
                    popularSubCategoryList.addAll(searchSubCatModel.getData());
                    buildCategoryRv(popularSubCategoryList);
                }

            }
        });
    }

    private void initClickListeners() {
        binding.cSearchToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.categorySearchView.removeLetter.setOnClickListener(view -> {
            binding.categorySearchView.searchField.setText("");
        });

        binding.customCreateView.customCreate.setOnClickListener(view -> {
            Constants.SUB_CAT_TITLE = "Custom Services";
            Navigation.findNavController(requireView()).navigate(R.id.action_search2_to_subscriptionName);
        });
    }

    private void buildCategoryRv(List<DataItem> popularSubCategoryList) {
        binding.progressBar.setVisibility(View.GONE);

        binding.searchLis.setVisibility(View.VISIBLE);
        binding.customCreateView.customLayout.setVisibility(View.GONE);


        binding.searchLis.setLayoutManager(mLayoutManager);
        SearchListAdapter adapter = new SearchListAdapter(Split.getAppContext(), popularSubCategoryList);
        adapter.notifyDataSetChanged();
        binding.searchLis.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        binding.searchLis.setAdapter(adapter);
        binding.loadingView.setVisibility(View.GONE);



        adapter.setOnCreateClickListener(position -> {

            //need to get plans for search category

            Constants.SUB_CATEGORY_ID = String.valueOf(popularSubCategoryList.get(position).getId());
            Bundle bundle = new Bundle();
            bundle.putString("SUB_CATEGORY_ID", String.valueOf(popularSubCategoryList.get(position).getId()));
            Constants.SUB_CAT_TITLE = String.valueOf(popularSubCategoryList.get(position).getTitle());


            plansViewModel = new PlansViewModel(String.valueOf(popularSubCategoryList.get(position).getId()));
            plansViewModel.init();
            plansViewModel.getPlan().observe(getViewLifecycleOwner(), planModel -> {
                if (planModel.isSuccess()) {
                    if (planModel.getData().size() > 0) {

                        Gson gson = new Gson();
                        String plansModelData = gson.toJson(planModel);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("plansDATA", plansModelData);
                        Navigation.findNavController(requireView()).navigate(R.id.action_search2_to_plans2, bundle1);

                    } else {
                        Constants.PLAN_ID = "";
                        Navigation.findNavController(requireView()).navigate(R.id.action_search2_to_visibility2);
                    }

                }
            });


//            Constants.SUB_CATEGORY_ID = String.valueOf(popularSubCategoryList.get(position).getId());
////            Bundle bundle = new Bundle();
////            bundle.putString("SUB_CATEGORY_ID", String.valueOf(popularSubCategoryList.get(position).getId()));
//            Constants.SUB_CAT_TITLE = String.valueOf(popularSubCategoryList.get(position).getTitle());
//
////sub cat id
//            plansViewModel = new PlansViewModel(String.valueOf(popularSubCategoryList.get(position).getId()));
//            plansViewModel.init();
//            plansViewModel.getPlan().observe(getViewLifecycleOwner(), planModel -> {
//                if (planModel.isSuccess()) {
//                    if (planModel.getData().size() > 0) {
//
//                        Gson gson = new Gson();
//                        String plansModelData = gson.toJson(planModel);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("plansDATA", plansModelData);
//                        Navigation.findNavController(requireView()).navigate(R.id.action_search2_to_plans2, bundle);
//
//                    } else {
//                        Constants.PLAN_ID = "";
//                        Navigation.findNavController(requireView()).navigate(R.id.action_search2_to_visibility2);
//
//                    }
//
//                }
//            });

        });
    }

    private void buildCategoryRv1(List<DataItem> popularSubCategoryList) {

//        binding.popularList.setVisibility(View.GONE);
        binding.searchLis.setVisibility(View.VISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.searchLis.setLayoutManager(layoutManager);
        SearchListAdapter adapter1 = new SearchListAdapter(Split.getAppContext(), popularSubCategoryList);
        binding.searchLis.setAdapter(adapter1);

        adapter1.setOnCreateClickListener(position -> {

            //need to get plans for search category

            Constants.SUB_CATEGORY_ID = String.valueOf(popularSubCategoryList.get(position).getId());
            Bundle bundle = new Bundle();
            bundle.putString("SUB_CATEGORY_ID", String.valueOf(popularSubCategoryList.get(position).getId()));
            Constants.SUB_CAT_TITLE = String.valueOf(popularSubCategoryList.get(position).getTitle());


            plansViewModel = new PlansViewModel(String.valueOf(popularSubCategoryList.get(position).getId()));
            plansViewModel.init();
            plansViewModel.getPlan().observe(getViewLifecycleOwner(), planModel -> {
                if (planModel.isSuccess()) {
                    if (planModel.getData().size() > 0) {

                        Gson gson = new Gson();
                        String plansModelData = gson.toJson(planModel);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("plansDATA", plansModelData);
                        Navigation.findNavController(requireView()).navigate(R.id.action_search2_to_plans2, bundle1);

                    } else {
                        Constants.PLAN_ID = "";
                        Navigation.findNavController(requireView()).navigate(R.id.action_search2_to_visibility2);
                    }

                }
            });


//            Bundle bundle = new Bundle();
//            bundle.putString("SUB_CATEGORY_ID",String.valueOf(popularSubCategoryList.get(position).getId()));
//
//            MySharedPreferences pm = new MySharedPreferences(Split.getAppContext());
//            pm.saveData(Split.getAppContext(), "SUB_CATEGORY_ID", String.valueOf(popularSubCategoryList.get(position).getId()));
//
//            Navigation.findNavController(requireView()).navigate(R.id.action_search2_to_subscriptionName);

        });
    }

}