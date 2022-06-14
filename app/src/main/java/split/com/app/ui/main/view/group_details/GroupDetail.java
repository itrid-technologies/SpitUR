package split.com.app.ui.main.view.group_details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJoinGroupPlansBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.title.setText("Netflix Premium");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailItems = new ArrayList<>();

        viewModel = new GroupDetailViewModel();
        viewModel.init();
        viewModel.getDetailData().observe(getViewLifecycleOwner(),groupDetailModel -> {
            if (groupDetailModel.isSuccess()){

                if (groupDetailModel.getData().size() > 0){
                    detailItems.addAll(groupDetailModel.getData());
                    buildRec();
                }
            }
        });



    }

    private void buildRec() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.groupsDetailList.setLayoutManager(layoutManager);
        GroupDetailAdapter adapter = new GroupDetailAdapter(Split.getAppContext(), detailItems);
        binding.groupsDetailList.setAdapter(adapter);
    }
}