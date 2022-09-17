package com.splitur.app.ui.main.view.notifications;

import android.os.Bundle;
import android.os.Parcelable;
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

import com.splitur.app.R;
import com.splitur.app.data.model.notification.NotificationDataItem;
import com.splitur.app.databinding.FragmentNotificationsBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.view.home.NotificationViewModel;
import com.splitur.app.utils.Split;

import java.util.ArrayList;


public class Notifications extends Fragment {

    FragmentNotificationsBinding binding;
    NotificationViewModel mViewModel;
    ArrayList<NotificationDataItem> notificationData;

    Parcelable recyclerViewState;
    private boolean flag_loading, isApiHit;
    int nextPage = 1;
    private int maxPageLimit;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);

        Dashboard.hideNav(true);
        binding.nToolbar.title.setText("Notifications");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLayoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);

        clickListeners();
        getNotification(nextPage);

        binding.notificationsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerViewState = binding.notificationsList.getLayoutManager().onSaveInstanceState(); // save recycleView state

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
                                getNotification(nextPage);
                                binding.progressBar.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                }
            }
        });

    }

    private void getNotification(int nextPage) {

        mViewModel = new NotificationViewModel(nextPage);
        mViewModel.init();
        mViewModel.getNotifications().observe(getViewLifecycleOwner(), notificationModel -> {
            if (notificationModel.isSuccess()) {
                if (notificationModel.getData() != null) {
                    if (notificationModel.getData().size() > 0) {
                        flag_loading = true;
                        notificationData = new ArrayList<>();
                        notificationData.addAll(notificationModel.getData());
                        maxPageLimit = notificationModel.getPage();
                        buildRv(notificationData);
                        binding.noDataavailable.setVisibility(View.GONE);
                    } else {
                        binding.loadingView.setVisibility(View.GONE);
                        if (nextPage == 1) {
                            binding.noDataavailable.setVisibility(View.VISIBLE);
                        }
                        flag_loading = false;

                    }
                } else {
                    binding.loadingView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void clickListeners() {

        binding.nToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });
    }

    private void buildRv(ArrayList<NotificationDataItem> notificationData) {
        binding.progressBar.setVisibility(View.GONE);
        binding.loadingView.setVisibility(View.GONE);
        binding.notificationsList.setLayoutManager(mLayoutManager);
        NotificationAdapter adapter = new NotificationAdapter(Split.getAppContext(), notificationData);
        adapter.notifyDataSetChanged();
        binding.notificationsList.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        binding.notificationsList.setAdapter(adapter);

        adapter.setOnNotificationClickListener(position -> {
            if (notificationData.get(position).getType().equalsIgnoreCase("memmber_chat")) {
                Bundle bundle = new Bundle();
                bundle.putString("receiverId", String.valueOf(notificationData.get(position).getUserId()));
                bundle.putString("groupId", notificationData.get(position).getGroupId());
                bundle.putBoolean("ask_otp", false);
                Navigation.findNavController(requireView()).navigate(R.id.memberChat, bundle);
            } else if (notificationData.get(position).getType().equalsIgnoreCase("group_chat")) {
                Bundle bundle = new Bundle();
                bundle.putString("groupId", notificationData.get(position).getGroupId());
                Navigation.findNavController(requireView()).navigate(R.id.chatroom, bundle);
            }
        });
    }


}