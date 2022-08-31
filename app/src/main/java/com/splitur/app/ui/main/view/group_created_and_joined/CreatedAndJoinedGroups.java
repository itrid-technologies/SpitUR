package com.splitur.app.ui.main.view.group_created_and_joined;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import com.splitur.app.data.api.ChatwootApiManager;
import com.splitur.app.data.model.all_created_groupx.DataItem;
import com.splitur.app.data.model.all_joined_groups.AllJoinedGroupModel;
import com.splitur.app.data.model.chatwoot_model.MessagesModel;
import com.splitur.app.databinding.FragmentCreatedAndJoinedGroupsBinding;
import com.splitur.app.ui.main.adapter.all_created_group.AllCreatedGroupAdapter;
import com.splitur.app.ui.main.adapter.all_joined_group.AllJoinedGroupAdapter;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.view.join_plans.CheckoutActivity;
import com.splitur.app.ui.main.viewmodel.CheckOutViewModel;
import com.splitur.app.ui.main.viewmodel.created_and_joined.CreatedAndJoinedViewModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreatedAndJoinedGroups extends Fragment {

    FragmentCreatedAndJoinedGroupsBinding binding;
    private CreatedAndJoinedViewModel viewModel;
    List<DataItem> data;
    List<com.splitur.app.data.model.all_joined_groups.DataItem> join_data;
    private boolean shouldGoToSupportChat = false;


    private String msgs;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreatedAndJoinedGroupsBinding.inflate(inflater, container, false);
        Dashboard.hideNav(false);
        binding.gToolbar.title.setText("Groups Created");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (getArguments() != null) {
            Dashboard.hideNav(true);
            shouldGoToSupportChat = getArguments().getBoolean("isFromChat");
            if (shouldGoToSupportChat){
                binding.gToolbar.back.setVisibility(View.VISIBLE);
            }else {
                binding.gToolbar.back.setVisibility(View.GONE);
            }


        }else {
            binding.gToolbar.back.setVisibility(View.GONE);
        }

        binding.gToolbar.back.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigateUp();
        });


        data = new ArrayList<>();
        binding.createdGroupslist.setVisibility(View.VISIBLE);

        viewModel = new CreatedAndJoinedViewModel();
        viewModel.init();
        viewModel.getData().observe(getViewLifecycleOwner(), allCreatedGroupModel -> {
            if (allCreatedGroupModel.isSuccess()) {
                if (allCreatedGroupModel.getData().size() > 0) {
                    data.addAll(allCreatedGroupModel.getData());
                    buildRv();
                } else {
                    binding.noGroupLayout.setVisibility(View.VISIBLE);
                    binding.createdGroupslist.setVisibility(View.GONE);
                }
            }
        });

        initClickListeners();

    }

    @Override
    public void onStart() {
        super.onStart();
//        if (getArguments() != null) {
//            Dashboard.hideNav(true);
//            shouldGoToSupportChat = getArguments().getBoolean("isFromChat");
//            if (shouldGoToSupportChat){
//                binding.gToolbar.back.setVisibility(View.VISIBLE);
//            }else {
//                binding.gToolbar.back.setVisibility(View.GONE);
//            }
//
//            binding.gToolbar.back.setOnClickListener(view1 -> {
//                Navigation.findNavController(view1).navigateUp();
//            });
//        }
    }

    private void initClickListeners() {
        binding.joinedButton.setOnClickListener(view -> {
            binding.createdGroupslist.setVisibility(View.GONE);
            binding.joinedGroupslist.setVisibility(View.VISIBLE);
            binding.noGroupLayout.setVisibility(View.GONE);


            binding.gToolbar.title.setText("Groups Joined");



            binding.joinedButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#246BFD")));
            binding.joinedButton.setTextColor(Color.WHITE);

            binding.createdButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00000000")));
            binding.createdButton.setTextColor(Color.parseColor("#9395A4"));

            viewModel = new CreatedAndJoinedViewModel();
            viewModel.initJoin();
            viewModel.getJoinData().observe(getViewLifecycleOwner(), groupDetailModel -> {
                if (groupDetailModel.isSuccess()) {
                    join_data = new ArrayList<>();

                    if (groupDetailModel.getData().size() > 0) {

                        binding.noGroupLayout.setVisibility(View.GONE);
                        binding.joinedGroupslist.setVisibility(View.VISIBLE);
                        binding.createdGroupslist.setVisibility(View.GONE);

                        for (int i = 0; i < groupDetailModel.getData().size()-1; i++) {
                            if (groupDetailModel.getData().get(i).getGroup() != null){
                                join_data.add(groupDetailModel.getData().get(i));
                            }

                        }
                        buildJoinRv(join_data, groupDetailModel);
                    } else {
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

            binding.gToolbar.title.setText("Groups Created");
            binding.createdButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#246BFD")));
            binding.createdButton.setTextColor(Color.WHITE);

            binding.joinedButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00000000")));
            binding.joinedButton.setTextColor(Color.parseColor("#9395A4"));


            viewModel = new CreatedAndJoinedViewModel();
            viewModel.init();
            viewModel.getData().observe(getViewLifecycleOwner(), allCreatedGroupModel -> {
                if (allCreatedGroupModel.isSuccess()) {
                    data = new ArrayList<>();

                    if (allCreatedGroupModel.getData().size() > 0) {
                        data.addAll(allCreatedGroupModel.getData());
                        buildRv();
                    } else {
                        binding.noGroupLayout.setVisibility(View.VISIBLE);
                        binding.createdGroupslist.setVisibility(View.GONE);
                    }
                }
            });

        });


        binding.joinButtonSearch.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_createdAndJoinedGroups_to_joinSearch);
        });


    }



    private void buildJoinRv(List<com.splitur.app.data.model.all_joined_groups.DataItem> join_data, AllJoinedGroupModel groupDetailModel) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.joinedGroupslist.setLayoutManager(layoutManager);
        AllJoinedGroupAdapter adapter = new AllJoinedGroupAdapter(Split.getAppContext(), join_data);
        binding.joinedGroupslist.setAdapter(adapter);

        adapter.setOnJoinedClixkListener(position -> {
            Gson gson = new Gson();
            String joinData = gson.toJson(join_data.get(position));

            Gson gson1 = new Gson();
            String groupDATA = gson1.toJson(groupDetailModel);

//          shouldGoToSupportChat
            if (shouldGoToSupportChat) {
                Bundle bundle = new Bundle();
                bundle.putString("support_chat", msgs);
                Navigation.findNavController(requireView()).navigate(R.id.action_createdAndJoinedGroups_to_supportChat, bundle);

            } else {//old flow
                if (join_data.get(position).getPaymentStatus().equalsIgnoreCase("pending")) {


                    CheckOutViewModel checkOutViewModel = new CheckOutViewModel();
                    checkOutViewModel.init();
                    checkOutViewModel.getData().observe(getViewLifecycleOwner(), secretKeyModel -> {
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


                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("joinedGroupData", joinData);
                    Navigation.findNavController(requireView()).navigate(R.id.action_createdAndJoinedGroups_to_joinedGroupDetail2, bundle);
                }
            }

        });
    }

    private void buildRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.createdGroupslist.setLayoutManager(layoutManager);
        AllCreatedGroupAdapter adapter = new AllCreatedGroupAdapter(Split.getAppContext(), data , shouldGoToSupportChat);
        binding.createdGroupslist.setAdapter(adapter);

        adapter.setOnCreatedGroupClickListener(position -> {
            //shouldGoToSupportChat
            if (shouldGoToSupportChat) {

                getSupportMessages(data.get(position).getId());;

            } else {//old flow
                Gson gson = new Gson();
                String createdGroupData = gson.toJson(data.get(position));
                Bundle bundle = new Bundle();
                bundle.putString("createdGroupData", createdGroupData);
                Navigation.findNavController(requireView()).navigate(R.id.action_createdAndJoinedGroups_to_createdGroupDetail, bundle);
            }
        });
    }

    private void getSupportMessages(int id) {
        binding.loadingView.setVisibility(View.VISIBLE);
        MySharedPreferences sharedPreferences = new MySharedPreferences(Split.getAppContext());
        String conversation_id = sharedPreferences.getData(Split.getAppContext(),"unique_conversation_id");

        int account_id = Constants.AccountId;
        Call<MessagesModel> call = ChatwootApiManager.getRestApiService().getSupportChat(Constants.ChatApiKey,account_id, Integer.parseInt(conversation_id));
        call.enqueue(new Callback<MessagesModel>() {
            @Override
            public void onResponse(@NonNull Call<MessagesModel> call, @NonNull Response<MessagesModel> response) {
                if (response.body() != null) {
                    MessagesModel messagesModel = response.body();
                    if (messagesModel.getPayload() != null) {
                        binding.loadingView.setVisibility(View.GONE);

                        Gson gson = new Gson();
                        msgs = gson.toJson(messagesModel);
                        if (Constants.AccountId != 0) {
                            Bundle bundle = new Bundle();
                            bundle.putString("support_chat", msgs);
                            bundle.putString("chat_group_id", String.valueOf(id));

                            Navigation.findNavController(requireView()).navigate(R.id.action_createdAndJoinedGroups_to_supportChat, bundle);

                        }
                    }
                } else if (response.code() == 400) {
                    if (response.errorBody() != null) {
                        Constants.getApiError(Split.getAppContext(),response.errorBody());

                    }
                } else if (response.code() == 500) {
                    if (response.errorBody() != null) {
                        Constants.getApiError(Split.getAppContext(),response.errorBody());

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessagesModel> call, @NonNull Throwable t) {
                Log.e("Support Chat Error", t.getMessage());
                binding.loadingView.setVisibility(View.GONE);

            }
        });
    }
}