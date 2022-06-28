package split.com.app.ui.main.view.group_details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
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
import split.com.app.data.model.group_detail.DataItem;
import split.com.app.data.model.group_detail.GroupDetailModel;
import split.com.app.data.model.home_categories.CategoryDataItems;
import split.com.app.databinding.FragmentJoinGroupPlansBinding;
import split.com.app.ui.main.adapter.HomeSectionAdapter;
import split.com.app.ui.main.adapter.group_detail_adapter.GroupDetailAdapter;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.viewmodel.group_viewmodel.GroupDetailViewModel;
import split.com.app.utils.Split;


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
            binding.title.setText(title);

        }

        viewModel = new GroupDetailViewModel(sub_categoryId,"");
        viewModel.init();
        viewModel.getDetailData().observe(getViewLifecycleOwner(),groupDetailModel -> {
            detailItems = new ArrayList<>();

            if (groupDetailModel.isSuccess()){

                if (groupDetailModel.getData().size() > 0){
                    detailItems.addAll(groupDetailModel.getData());
                    buildRec(detailItems);
                }
            }
        });


        searchTextWatcher();

//        binding.back.setOnClickListener(view1 -> {
//            Navigation.findNavController(view1).clearBackStack(R.id.groupDetail);
//        });

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
    }


    private void sarchBuildRec(List<DataItem> detailItems) {
        binding.searchGroupsDetailList.setVisibility(View.VISIBLE);
        binding.groupsDetailList.setVisibility(View.GONE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.searchGroupsDetailList.setLayoutManager(layoutManager);
        GroupDetailAdapter adapter = new GroupDetailAdapter(Split.getAppContext(), detailItems);
        binding.searchGroupsDetailList.setAdapter(adapter);
    }
}