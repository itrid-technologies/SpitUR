package com.splitur.app.ui.main.view.joined_detail;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.Slider;
import com.google.gson.Gson;
import com.splitur.app.R;
import com.splitur.app.data.model.all_joined_groups.DataItem;
import com.splitur.app.databinding.FragmentJoinedGroupDetailBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.viewmodel.joined_group_detail.JoinedGroupDetailViewModel;
import com.splitur.app.ui.main.viewmodel.memebers_viewmodel.GroupMembersViewModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.Split;
import com.splitur.app.utils.TimeAgo;


public class JoinedGroupDetail extends Fragment {

    com.splitur.app.databinding.FragmentJoinedGroupDetailBinding binding;
    com.splitur.app.data.model.all_joined_groups.DataItem data;
    GroupMembersViewModel membersViewModel;

    JoinedGroupDetailViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJoinedGroupDetailBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.jgdToolbar.title.setText("Group Joined");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            if (getArguments() != null) {
                Gson gson = new Gson();
                String groupData = getArguments().getString("joinedGroupData");
                data = gson.fromJson(groupData, com.splitur.app.data.model.all_joined_groups.DataItem.class);

                setProfileData(data);
            }
        } catch (IllegalStateException e) {
            Log.e("join_group_detail", e.getMessage());
        }

        initClickListeners();
        getScore();
    }

    private void getScore() {
        viewModel = new JoinedGroupDetailViewModel(String.valueOf(data.getGroupId()), "", "");
        viewModel.initScore();
        viewModel.getScoreData().observe(getViewLifecycleOwner(), getScoreModel -> {
            if (getScoreModel.isSuccess()) {
                binding.scoreValue.setText(String.valueOf(getScoreModel.getSplitScore()));
                binding.slider.setValue(Float.valueOf(getScoreModel.getSplitScore()));
            }
        });
    }

    private void setProfileData(DataItem data) {

        try {

            binding.joinedProfile.netflix.setText(data.getGroup().getGroupTitle());
//            Glide.with(Split.getAppContext())
//                    .load(Constants.IMG_PATH + data.getGroup().getGroupAdmin().getAvatar())
//                    .placeholder(R.color.images_placeholder)
//                    .into(binding.joinedProfile.userImage);

            binding.joinedProfile.userImage.setImageResource(Constants.getAvatarIcon(requireContext(), Integer.parseInt(data.getGroup().getGroupAdmin().getAvatar())));


            binding.joinedProfile.userName.setText("@" + data.getGroup().getGroupAdmin().getUserId());
            String coin = String.valueOf(data.getGroup().getCostPerMember());
            double coinFloat = Double.parseDouble(coin);
            String value = String.valueOf(Math.round(((coinFloat * 30) / 100) + coinFloat));
            binding.joinedProfile.count.setText(String.format("%s %s", value, getString(R.string.inr_sign)));
            binding.tvScoreValue.setText(String.valueOf(data.getGroup().getGroupAdmin().getSpliturScore()));


            if (data.getGroup().getGroupAdmin().getLastActive() != null) {
                TimeAgo timeAgo = new TimeAgo();
                String last_seen = timeAgo.covertTimeToText(data.getGroup().getGroupAdmin().getLastActive());
                binding.tvLastActive.setText(last_seen);
            }
//          binding.tvLastActive.setText(Constants.getDate(data.getGroup().getGroupAdmin().getLastActive()));
//          binding.tvDaysValue.setText(Constants.getDate(data.getGroup().getGroupAdmin().getCreatedAt()));

            if (data.getGroup().getGroupAdmin().getCreatedAt() != null) {
                TimeAgo timeAgo = new TimeAgo();
                String last_seen = timeAgo.covertTimeToText(data.getGroup().getGroupAdmin().getCreatedAt());
                binding.tvDaysValue.setText(last_seen);
            }
            binding.groupEmail.setText(data.getGroup().getEmail());
            binding.groupPass.setText(data.getGroup().getPassword());

            binding.tvUserid.setText(data.getGroup().getGroupAdmin().getUserId());
            if (data.getGroup().getGroupAdmin().isOnlineOflineStatus()) {
                binding.tvOnline.setText("Online");
                binding.onlineIcon.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0FB900")));
            } else {
                binding.tvOnline.setText("Offline");
                binding.onlineIcon.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
            }

//            if (data.getGroup().getSubCategory().getValidationType() != null){
//                String otpAuth = data.getGroup().getSubCategory().getValidationType();
//                if (otpAuth.equalsIgnoreCase("otp")){
//                    binding.tvRequest.setVisibility(View.VISIBLE);
//                    binding.send.setVisibility(View.VISIBLE);
//                    binding.view1.setVisibility(View.VISIBLE);
//                }else {
//                    binding.tvRequest.setVisibility(View.GONE);
//                    binding.send.setVisibility(View.GONE);
//                    binding.view1.setVisibility(View.GONE);
//                }
//            }

        } catch (NullPointerException e) {
            Log.e("null", e.getMessage());
        }
    }

    private void initClickListeners() {

        binding.jgdToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.jgdToolbar.info.setOnClickListener(view1 -> {
            final BottomSheetDialog bt = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
            View carDetailView = LayoutInflater.from(requireContext()).inflate(R.layout.group_options_dialogue, null, false);

            ConstraintLayout chatLayout = carDetailView.findViewById(R.id.options_chat_layout);
            ConstraintLayout deleteLayout = carDetailView.findViewById(R.id.deleteLAYOUT);
            ImageView confirm = deleteLayout.findViewById(R.id.confirm_remove);
            TextView removeText = deleteLayout.findViewById(R.id.tv_remove);
            ConstraintLayout confirm_layout = carDetailView.findViewById(R.id.delete_layout);

            deleteLayout.setOnClickListener(view -> {
                if (confirm_layout.getTag().equals("hidden")) {
                    confirm_layout.setVisibility(View.VISIBLE);
                    confirm_layout.setTag("visible");
                } else {
                    confirm_layout.setVisibility(View.GONE);
                    confirm_layout.setTag("hidden");
                }

            });

            removeText.setText("Leave Group");


            chatLayout.setOnClickListener(view -> {
                bt.cancel();
                Navigation.findNavController(requireView()).navigate(R.id.chatroom);
            });

            confirm.setOnClickListener(view -> {
                bt.cancel();
                membersViewModel = new GroupMembersViewModel((String.valueOf(data.getGroup().getId())), "", "", "", false);
                membersViewModel.initLeftGroup();
                membersViewModel.getLeft_data().observe(getViewLifecycleOwner(), basicModel -> {
                    if (basicModel.isStatus().equalsIgnoreCase("true")) {
                        displayRemovedDialogue("You left the Group");
                    }
                });
            });


            bt.setContentView(carDetailView);
            bt.show();
        });


        binding.tvChatWithAdmin.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("receiverId", String.valueOf(data.getGroup().getUserId()));
            bundle.putString("groupId", String.valueOf(data.getGroup().getId()));
            bundle.putBoolean("ask_otp", true);

            Navigation.findNavController(requireView()).navigate(R.id.action_joinedGroupDetail2_to_memberChat, bundle);
        });

        binding.slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                binding.scoreValue.setText(String.valueOf(Math.round(value)));
                viewModel = new JoinedGroupDetailViewModel(String.valueOf(data.getGroupId()),
                        String.valueOf(data.getGroup().getGroupAdmin().getId()),
                        String.valueOf(value));

                viewModel.init();
                viewModel.getData().observe(getViewLifecycleOwner(), basicModel -> {
                    if (basicModel.isStatus()) {
                        Toast.makeText(Split.getAppContext(), "Updated Score " + String.valueOf(value), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.copyEmail1.setOnClickListener(view -> {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("WordKeeper", binding.groupEmail.getText().toString());
            clipboard.setPrimaryClip(clip);

            Toast.makeText(Split.getAppContext(), "Copied", Toast.LENGTH_SHORT).show();
        });


        binding.copyPass1.setOnClickListener(view -> {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("WordKeeper", binding.groupPass.getText().toString());
            clipboard.setPrimaryClip(clip);

            Toast.makeText(Split.getAppContext(), "Copied", Toast.LENGTH_SHORT).show();
        });


        binding.btnGroupChat.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("groupId", String.valueOf(data.getGroup().getId()));
            Navigation.findNavController(view).navigate(R.id.action_joinedGroupDetail2_to_chatroom, bundle);
        });

        binding.send.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("receiverId", String.valueOf(data.getGroup().getUserId()));
            bundle.putString("groupId", String.valueOf(data.getGroup().getId()));
            bundle.putBoolean("ask_otp", true);

            Navigation.findNavController(requireView()).navigate(R.id.action_joinedGroupDetail2_to_memberChat, bundle);
        });
    }

    private void displayRemovedDialogue(String data) {
        final BottomSheetDialog bt = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
        View view1 = LayoutInflater.from(requireContext()).inflate(R.layout.removed_dialogue, null, false);

        Button cancel = view1.findViewById(R.id.btn_return_to_group);
        TextView removeText = view1.findViewById(R.id.tv_removed);


        removeText.setText(data);

        cancel.setOnClickListener(view -> {
            bt.cancel();
            getActivity().onBackPressed();

        });


        bt.setContentView(view1);
        bt.show();
    }
}