package com.splitur.app.ui.main.view.group_details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import com.splitur.app.R;
import com.splitur.app.data.model.group_detail.DataItem;
import com.splitur.app.databinding.FragmentJoinGroupPlansBinding;
import com.splitur.app.ui.main.adapter.group_detail_adapter.GroupDetailAdapter;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.viewmodel.group_viewmodel.GroupDetailViewModel;
import com.splitur.app.utils.Split;


public class GroupDetail extends Fragment {

    FragmentJoinGroupPlansBinding binding;
    private GroupDetailViewModel viewModel;
    private List<DataItem> detailItems;
    String sub_categoryId , title;



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

        if (getArguments() != null){
            sub_categoryId = getArguments().getString("join_sub_cat_id");
            title = getArguments().getString("join_sub_cat_title");
            binding.groupDetailToolbar.title.setText(title);

        }

        viewModel = new GroupDetailViewModel(sub_categoryId,"");
        viewModel.init();
        viewModel.getDetailData().observe(getViewLifecycleOwner(),groupDetailModel -> {
            detailItems = new ArrayList<>();

            if (groupDetailModel.isSuccess()){

                if (groupDetailModel.getData() != null) {

                    if (groupDetailModel.getData().size() > 0) {
                        detailItems.addAll(groupDetailModel.getData());
                        buildRec(detailItems);
                    }
                }else {

                }
            }
        });


        searchTextWatcher();

        binding.groupDetailToolbar.back.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigateUp();
        });

    }

    private void searchTextWatcher() {
        binding.planSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    binding.planSearchField.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(Split.getAppContext(), R.drawable.search_icon), null, ContextCompat.getDrawable(Split.getAppContext(), R.drawable.ic_close), null);
                } else {
                    binding.planSearchField.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(Split.getAppContext(), R.drawable.search_icon), null, null, null);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable data) {

                if (!data.toString().isEmpty()){
                    getSearchedData(data.toString());
                }

            }
        });
    }

    private void getSearchedData(String data) {
        detailItems = new ArrayList<>();
        viewModel = new GroupDetailViewModel(sub_categoryId,data);
        viewModel.initSearch();
        viewModel.getDetailSearchData().observe(getViewLifecycleOwner(),groupDetailModel -> {
            if (groupDetailModel.isSuccess()){

                if (groupDetailModel.getData().size() > 0){
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.groupsDetailList.setLayoutManager(layoutManager);
        GroupDetailAdapter adapter = new GroupDetailAdapter(Split.getAppContext(), detailItems);
        binding.groupsDetailList.setAdapter(adapter);

        adapter.setOnGroupSelectListener(position -> {
            Bundle bundle = new Bundle();
            Gson gson = new Gson();
            String groupData = gson.toJson(detailItems.get(position));

            bundle.putString("groupDetail",groupData);
            Navigation.findNavController(requireView()).navigate(R.id.action_groupDetail_to_groupInformation,bundle);
        });
    }


    private void sarchBuildRec(List<DataItem> detailItems) {
        binding.searchGroupsDetailList.setVisibility(View.VISIBLE);
        binding.groupsDetailList.setVisibility(View.GONE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.searchGroupsDetailList.setLayoutManager(layoutManager);
        GroupDetailAdapter adapter = new GroupDetailAdapter(Split.getAppContext(), detailItems);
        binding.searchGroupsDetailList.setAdapter(adapter);
        adapter.setOnGroupSelectListener(position -> {
            Bundle bundle = new Bundle();
            Gson gson = new Gson();
            String groupData = gson.toJson(detailItems.get(position));

            bundle.putString("groupDetail",groupData);
            Navigation.findNavController(requireView()).navigate(R.id.action_groupDetail_to_groupInformation,bundle);
        });
    }
}