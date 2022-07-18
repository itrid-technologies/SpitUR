package split.com.app.ui.main.view.group_created_and_joined;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import split.com.app.R;
import split.com.app.data.model.all_created_groupx.DataItem;
import split.com.app.data.model.all_joined_groups.AllJoinedGroupModel;
import split.com.app.databinding.FragmentCreatedAndJoinedGroupsBinding;
import split.com.app.ui.main.adapter.all_created_group.AllCreatedGroupAdapter;
import split.com.app.ui.main.adapter.all_joined_group.AllJoinedGroupAdapter;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.view.join_plans.CheckoutActivity;
import split.com.app.ui.main.viewmodel.CheckOutViewModel;
import split.com.app.ui.main.viewmodel.created_and_joined.CreatedAndJoinedViewModel;
import split.com.app.utils.Split;


public class CreatedAndJoinedGroups extends Fragment {

   FragmentCreatedAndJoinedGroupsBinding binding;
    private CreatedAndJoinedViewModel viewModel;
    List<DataItem> data;
    List<split.com.app.data.model.all_joined_groups.DataItem> join_data;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreatedAndJoinedGroupsBinding.inflate(inflater, container, false);
        Dashboard.hideNav(false);
        binding.gToolbar.title.setText("Group Created");
        binding.gToolbar.back.setVisibility(View.GONE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        data = new ArrayList<>();
        binding.createdGroupslist.setVisibility(View.VISIBLE);

        viewModel = new CreatedAndJoinedViewModel();
        viewModel.init();
        viewModel.getData().observe(getViewLifecycleOwner(),allCreatedGroupModel -> {
            if (allCreatedGroupModel.isSuccess()){
                if (allCreatedGroupModel.getData().size() > 0){
                  data.addAll(allCreatedGroupModel.getData());
                  buildRv();
                }else {
                  binding.noGroupLayout.setVisibility(View.VISIBLE);
                  binding.createdGroupslist.setVisibility(View.GONE);
                }
            }
        });

        initClickListeners();

    }

    private void initClickListeners() {
        binding.joinedButton.setOnClickListener(view -> {
            binding.createdGroupslist.setVisibility(View.GONE);
            binding.joinedGroupslist.setVisibility(View.VISIBLE);
            binding.noGroupLayout.setVisibility(View.GONE);


            binding.gToolbar.title.setText("Group Joined");

            binding.joinedButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#246BFD")));
            binding.joinedButton.setTextColor(Color.WHITE);

            binding.createdButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00000000")));
            binding.createdButton.setTextColor(Color.parseColor("#9395A4"));

            viewModel = new CreatedAndJoinedViewModel();
            viewModel.initJoin();
            viewModel.getJoinData().observe(getViewLifecycleOwner(),groupDetailModel -> {
                if (groupDetailModel.isSuccess()){
                    join_data = new ArrayList<>();

                    if (groupDetailModel.getData().size() > 0){

                        binding.noGroupLayout.setVisibility(View.GONE);
                        binding.joinedGroupslist.setVisibility(View.VISIBLE);
                        binding.createdGroupslist.setVisibility(View.GONE);

                        join_data.addAll(groupDetailModel.getData());
                        buildJoinRv(groupDetailModel);
                    }else {
                        binding.noGroupLayout.setVisibility(View.VISIBLE);
                        binding.joinedGroupslist.setVisibility(View.GONE);
                        binding.createdGroupslist.setVisibility(View.GONE);
                    }
                }
            });

        });

        binding.createdButton.setOnClickListener(view -> {
            binding.createdGroupslist.setVisibility(View.VISIBLE);
            binding.joinedGroupslist.setVisibility(View.GONE);
            binding.noGroupLayout.setVisibility(View.GONE);

            binding.gToolbar.title.setText("Group Created");
            binding.createdButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#246BFD")));
            binding.createdButton.setTextColor(Color.WHITE);

            binding.joinedButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00000000")));
            binding.joinedButton.setTextColor(Color.parseColor("#9395A4"));
        });


        binding.joinButtonSearch.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_createdAndJoinedGroups_to_joinSearch);
        });


    }

    private void buildJoinRv(AllJoinedGroupModel groupDetailModel) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.joinedGroupslist.setLayoutManager(layoutManager);
        AllJoinedGroupAdapter adapter = new AllJoinedGroupAdapter(Split.getAppContext(), join_data);
        binding.joinedGroupslist.setAdapter(adapter);

        adapter.setOnJoinedClixkListener(position -> {
            Gson gson = new Gson();
            String joinData = gson.toJson(join_data.get(position));

            Gson gson1 = new Gson();
            String groupDATA = gson1.toJson(groupDetailModel);

            if (join_data.get(position).getPaymentStatus().equalsIgnoreCase("pending")){


                CheckOutViewModel checkOutViewModel = new CheckOutViewModel();
                checkOutViewModel.init();
                checkOutViewModel.getData().observe(getViewLifecycleOwner() ,secretKeyModel -> {
                    if (secretKeyModel.isStatus()) {
                        String secret_key = secretKeyModel.getKey();

                        Intent checkoutIntent = new Intent(requireContext(), CheckoutActivity.class);
                        checkoutIntent.putExtra("group_id", String.valueOf(join_data.get(position).getGroupId()));
                        checkoutIntent.putExtra("upi_id", join_data.get(position).getUpiId());
                        checkoutIntent.putExtra("subscription_id", join_data.get(position).getSubscriptionId());
                        checkoutIntent.putExtra("group_credentials", groupDATA);
                        checkoutIntent.putExtra("secret_key", secret_key);
                        startActivity(checkoutIntent);
                        requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    }
                });


            }
            else {
                Bundle bundle = new Bundle();
                bundle.putString("joinedGroupData",joinData);
                Navigation.findNavController(requireView()).navigate(R.id.action_createdAndJoinedGroups_to_joinedGroupDetail2,bundle);
            }

        });
    }

    private void buildRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.createdGroupslist.setLayoutManager(layoutManager);
        AllCreatedGroupAdapter adapter = new AllCreatedGroupAdapter(Split.getAppContext(), data);
        binding.createdGroupslist.setAdapter(adapter);

        adapter.setOnCreatedGroupClickListener(position -> {
            Gson gson = new Gson();
            String createdGroupData = gson.toJson(data.get(position));
            Bundle bundle = new Bundle();
            bundle.putString("createdGroupData",createdGroupData);
            Navigation.findNavController(requireView()).navigate(R.id.action_createdAndJoinedGroups_to_createdGroupDetail, bundle);

        });
    }
}