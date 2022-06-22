package split.com.app.ui.main.view.joined_detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import split.com.app.R;
import split.com.app.data.model.all_joined_groups.DataItem;
import split.com.app.databinding.FragmentJoinedGroupDetailBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.viewmodel.memebers_viewmodel.GroupMembersViewModel;
import split.com.app.utils.Constants;
import split.com.app.utils.Split;


public class JoinedGroupDetail extends Fragment {

    FragmentJoinedGroupDetailBinding binding;
    split.com.app.data.model.all_joined_groups.DataItem data;
    GroupMembersViewModel membersViewModel;


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
            if (getArguments() != null){
                Gson gson = new Gson();
                String groupData = getArguments().getString("joinedGroupData");
                data = gson.fromJson(groupData, split.com.app.data.model.all_joined_groups.DataItem.class);

                setProfileData(data);
            }
        }catch (IllegalStateException e){
            Log.e("join_group_detail",e.getMessage());
        }

        initClickListeners();
    }

    private void setProfileData(DataItem data) {

        try {

            binding.joinedProfile.netflix.setText(data.getGroup().getGroupTitle());
            Glide.with(Split.getAppContext())
                    .load(Constants.IMG_PATH + data.getGroup().getGroupAdmin().getAvatar())
                    .placeholder(R.drawable.user)
                    .into(binding.joinedProfile.userImage);

            binding.joinedProfile.userName.setText(data.getGroup().getGroupAdmin().getUserId());
            String coin = String.valueOf(data.getGroup().getCostPerMember());
            Double coinFloat = Double.parseDouble(coin);
            String value = String.valueOf(((coinFloat * 30) / 100) + coinFloat);
            binding.joinedProfile.count.setText(value + " Coins");
            binding.tvScoreValue.setText(String.valueOf(data.getGroup().getGroupAdmin().getSpliturScore()));
            binding.tvLastActive.setText(Constants.getDate(data.getGroup().getGroupAdmin().getLastActive()));
            binding.groupEmail.setText(data.getGroup().getEmail());
            binding.groupPass.setText(data.getGroup().getPassword());

        }catch (NullPointerException e){
            Log.e("null",e.getMessage());
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
            TextView removeText = deleteLayout.findViewById(R.id.tv_remove);

            removeText.setText("Leave Group");

            chatLayout.setOnClickListener(view -> {
                bt.cancel();
                Navigation.findNavController(requireView()).navigate(R.id.chatroom);
            });

            deleteLayout.setOnClickListener(view -> {
                bt.cancel();
                membersViewModel = new GroupMembersViewModel((String.valueOf(data.getId())),"","","","");
                membersViewModel.initRemoveGroup();
                membersViewModel.getRemove_data().observe(getViewLifecycleOwner(),basicModel -> {
                    if (basicModel.isStatus()){
                        displayRemovedDialogue();
                    }
                });
            });


            bt.setContentView(carDetailView);
            bt.show();
        });


        binding.btnGroupChat.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("groupId", String.valueOf(data.getId()));
            Navigation.findNavController(view).navigate(R.id.action_createdGroupDetail_to_chatroom,bundle);
        });
    }

    private void displayRemovedDialogue() {
        final BottomSheetDialog bt = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
        View carDetailView = LayoutInflater.from(requireContext()).inflate(R.layout.removed_dialogue, null, false);

        Button cancel = carDetailView.findViewById(R.id.btn_return_to_group);


        cancel.setOnClickListener(view -> {
            bt.cancel();
        });


        bt.setContentView(carDetailView);
        bt.show();
    }
}