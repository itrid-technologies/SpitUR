package com.splitur.app.ui.main.view.group_details;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.splitur.app.R;
import com.splitur.app.data.model.group_detail.DataItem;
import com.splitur.app.databinding.FragmentJoinGroupPlansBinding;
import com.splitur.app.ui.main.adapter.group_detail_adapter.GroupDetailAdapter;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.viewmodel.group_viewmodel.GroupDetailViewModel;
import com.splitur.app.utils.Split;

import java.util.ArrayList;
import java.util.List;


public class GroupDetail extends Fragment {

    FragmentJoinGroupPlansBinding binding;
    private GroupDetailViewModel viewModel;
    private List<DataItem> detailItems;
    String sub_categoryId, title;


    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    ProgressDialog progressDialog;
    int currentPage;
    Parcelable recyclerViewState;
    private boolean flag_loading;
    int nextPage = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJoinGroupPlansBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLayoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        if (getArguments() != null) {
            sub_categoryId = getArguments().getString("join_sub_cat_id");
            title = getArguments().getString("join_sub_cat_title");
            binding.groupDetailToolbar.title.setText(title);

        }

        binding.joinPlanSearchView.removeLetter.setOnClickListener(view1 -> {
            binding.joinPlanSearchView.searchField.setText("");
            getAllDataBack(nextPage);
        });

        getAllDataBack(nextPage);


        searchTextWatcher();

        binding.groupDetailToolbar.back.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigateUp();
        });

        binding.groupsDetailList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerViewState = binding.groupsDetailList.getLayoutManager().onSaveInstanceState(); // save recycleView state

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (flag_loading) {

                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount ) {
                            flag_loading = false;
                            nextPage++;
//                            getAllDataBack(nextPage);

                            // Do pagination.. i.e. fetch new data
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//
////                                    showProgressView();
//                                }
//                            }, 1000);

                        }
                    }

            }
        });



    }

    private void searchTextWatcher() {
        binding.joinPlanSearchView.searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    binding.joinPlanSearchView.removeLetter.setVisibility(View.VISIBLE);
//                    binding.planSearchField.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(Split.getAppContext(), R.drawable.search_icon), null, ContextCompat.getDrawable(Split.getAppContext(), R.drawable.ic_close), null);
                } else {
                    getAllDataBack(1);
                    binding.joinPlanSearchView.removeLetter.setVisibility(View.GONE);

//                    binding.planSearchField.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(Split.getAppContext(), R.drawable.search_icon), null, null, null);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable data) {

                if (!data.toString().isEmpty()) {
                    if (isNumeric(data.toString())){
                        getSearchedDataByGroupId(data.toString());
                    }else {

                        if (data.toString().startsWith("@")) {
                            String originalString = data.toString();
                            originalString = originalString.replaceFirst("@", "");
                            getSearchedDataByUserId(originalString);
                        } else {
                            getSearchedData(data.toString());
                        }
                    }
                }
            }
        });
    }

    private void getAllDataBack(int page) {
        viewModel = new GroupDetailViewModel(sub_categoryId, "",String.valueOf(page));
        viewModel.init();
        viewModel.getDetailData().observe(getViewLifecycleOwner(), groupDetailModel -> {
            detailItems = new ArrayList<>();

            if (groupDetailModel.isSuccess()) {

                if (groupDetailModel.getData() != null) {

                    flag_loading = true;
                    
                    if (groupDetailModel.getData().size() > 0) {
                        detailItems.addAll(groupDetailModel.getData());
                        buildRec(detailItems);
                    }
                } else {

                    flag_loading = false;
                }
            }
        });
    }

    private void getSearchedDataByGroupId(String intData) {
        detailItems = new ArrayList<>();
        viewModel = new GroupDetailViewModel(sub_categoryId, intData,"");
        viewModel.initSearchByGroupId();
        viewModel.getDetailSearchDataByGroupId().observe(getViewLifecycleOwner(), groupDetailModel -> {
            if (groupDetailModel.isSuccess()) {

                if (groupDetailModel.getData().size() > 0) {
                    detailItems = new ArrayList<>();
                    detailItems.addAll(groupDetailModel.getData());
                    sarchBuildRec(detailItems);
                }
            }
        });
    }

    private void getSearchedDataByUserId(String originalString) {
        detailItems = new ArrayList<>();
        viewModel = new GroupDetailViewModel(sub_categoryId, originalString,"");
        viewModel.initSearchByUserId();
        viewModel.getDetailSearchDataByUserId().observe(getViewLifecycleOwner(), groupDetailModel -> {
            if (groupDetailModel.isSuccess()) {

                if (groupDetailModel.getData().size() > 0) {
                    detailItems = new ArrayList<>();
                    detailItems.addAll(groupDetailModel.getData());
                    sarchBuildRec(detailItems);
                }
            }
        });
    }

    private void getSearchedData(String data) {
        detailItems = new ArrayList<>();
        viewModel = new GroupDetailViewModel(sub_categoryId, data,"");
        viewModel.initSearch();
        viewModel.getDetailSearchData().observe(getViewLifecycleOwner(), groupDetailModel -> {
            if (groupDetailModel.isSuccess()) {

                if (groupDetailModel.getData().size() > 0) {
                    detailItems = new ArrayList<>();
                    detailItems.addAll(groupDetailModel.getData());
                    sarchBuildRec(detailItems);
                }
            }
        });
    }

    private void buildRec(List<DataItem> detailItems) {
        binding.groupsDetailList.setVisibility(View.VISIBLE);
        binding.searchGroupsDetailList.setVisibility(View.GONE);

        binding.groupsDetailList.setLayoutManager(mLayoutManager);
        GroupDetailAdapter adapter = new GroupDetailAdapter(requireContext(), detailItems);
        adapter.notifyDataSetChanged();
        binding.groupsDetailList.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        binding.groupsDetailList.setAdapter(adapter);

        adapter.setOnGroupSelectListener(position -> {
            Bundle bundle = new Bundle();
            Gson gson = new Gson();
            String groupData = gson.toJson(detailItems.get(position));
            bundle.putString("groupDetail", groupData);
            Navigation.findNavController(requireView()).navigate(R.id.action_groupDetail_to_groupInformation, bundle);
        });
    }


    private void sarchBuildRec(List<DataItem> detailItems) {
        binding.searchGroupsDetailList.setVisibility(View.VISIBLE);
        binding.groupsDetailList.setVisibility(View.GONE);

        binding.searchGroupsDetailList.setLayoutManager(mLayoutManager);
        GroupDetailAdapter adapter = new GroupDetailAdapter(Split.getAppContext(), detailItems);
        binding.searchGroupsDetailList.setAdapter(adapter);
        adapter.setOnGroupSelectListener(position -> {
            Bundle bundle = new Bundle();
            Gson gson = new Gson();
            String groupData = gson.toJson(detailItems.get(position));

            bundle.putString("groupDetail", groupData);
            Navigation.findNavController(requireView()).navigate(R.id.action_groupDetail_to_groupInformation, bundle);
        });
    }

    public static boolean isNumeric(String string) {
        int intValue;

        if(string == null || string.equals("")) {
            System.out.println("String cannot be parsed, it is null or empty.");
            return false;
        }

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
        }
        return false;
    }
}