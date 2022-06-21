package split.com.app.ui.main.view.created_detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import split.com.app.R;
import split.com.app.data.model.all_created_groupx.DataItem;
import split.com.app.databinding.FragmentCreatedGroupDetailBinding;
import split.com.app.databinding.FragmentCredentialsBinding;
import split.com.app.ui.main.adapter.all_joined_group.AllJoinedGroupAdapter;
import split.com.app.ui.main.adapter.group_member.GroupMemberAdapter;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.viewmodel.memebers_viewmodel.GroupMembersViewModel;
import split.com.app.utils.Constants;
import split.com.app.utils.Split;


public class CreatedGroupDetail extends Fragment {

  FragmentCreatedGroupDetailBinding binding;
  DataItem data;

  GroupMembersViewModel membersViewModel;
  private List<split.com.app.data.model.group_member.DataItem> membersList;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreatedGroupDetailBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.jgdToolbar.title.setText("Group Created");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        membersList = new ArrayList<>();
        initClickEvents();

        try {
            if (getArguments() != null){
                Gson gson = new Gson();
                String groupData = getArguments().getString("createdGroupData");
                data = gson.fromJson(groupData,DataItem.class);

                setProfileData(data);
            }
        }catch (IllegalStateException e){
            Log.e("group_detail",e.getMessage());
        }


        membersViewModel = new GroupMembersViewModel((String.valueOf(data.getId())),"","","");
        membersViewModel.init();
        membersViewModel.getPlan().observe(getViewLifecycleOwner(),groupMemberModel -> {
            if (groupMemberModel.isSuccess()){
                if (groupMemberModel.getData().size() > 0){
                    membersList.addAll(groupMemberModel.getData());
                    buildMembersList(membersList);
                }
            }
        });


        textWatcher();

    }

    private void textWatcher() {
        binding.edUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String updated_value = editable.toString().trim();
                updateEmailData(updated_value);
            }
        });

        binding.edPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String updated_value = editable.toString().trim();
                updatePassData(updated_value);
            }
        });
    }

    private void updateEmailData(String updated_value) {
        membersViewModel = new GroupMembersViewModel((String.valueOf(data.getId())), "",updated_value,"");
        membersViewModel.initEmailUpdate();
        membersViewModel.getUpdate_email_data().observe(getViewLifecycleOwner(),basicModel -> {
            if (basicModel.isStatus()){
                Toast.makeText(Split.getAppContext(), basicModel.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePassData(String updated_value) {
        membersViewModel = new GroupMembersViewModel((String.valueOf(data.getId())), "","",updated_value);
        membersViewModel.initPassUpdate();
        membersViewModel.getUpdate_pass_data().observe(getViewLifecycleOwner(),basicModel -> {
            if (basicModel.isStatus()){
                Toast.makeText(Split.getAppContext(), basicModel.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buildMembersList(List<split.com.app.data.model.group_member.DataItem> membersList) {
        if (membersList.size() > 0){
            LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
            binding.groupMembersList.setLayoutManager(layoutManager);
            GroupMemberAdapter adapter = new GroupMemberAdapter(Split.getAppContext(), membersList);
            binding.groupMembersList.setAdapter(adapter);

            adapter.setOnRemoveListener(position -> {

                final BottomSheetDialog bt = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
                View carDetailView = LayoutInflater.from(requireContext()).inflate(R.layout.delete_layout, null, false);
                ConstraintLayout deleteLayout = carDetailView.findViewById(R.id.delete_layout);
                deleteLayout.setOnClickListener(view -> {

                    membersViewModel = new GroupMembersViewModel((String.valueOf(data.getId())), String.valueOf(membersList.get(position).getUserId()),"","");
                    membersViewModel.initRemoveMember();
                    membersViewModel.getMember_remove_data().observe(getViewLifecycleOwner(),basicModel -> {
                        if (basicModel.isStatus()){
                            displayRemovedDialogue();
                        }
                    });
                });

                bt.setContentView(carDetailView);
                bt.show();
            });

        }
    }

    private void setProfileData(DataItem data) {
        binding.detailProfile.netflix.setText(data.getGroupTitle());
        Glide.with(Split.getAppContext())
                .load(Constants.IMG_PATH + data.getGroupAdmin().getAvatar())
                .placeholder(R.drawable.user)
                .into(binding.detailProfile.userImage);

        binding.detailProfile.userName.setText(data.getGroupAdmin().getUserId());
        binding.detailProfile.count.setText((String.valueOf(data.getSlots()))+ " Slots");

        binding.edUsername.setText(data.getEmail());
        binding.edPassword.setText(data.getPassword());

        //group_admin
        Glide.with(Split.getAppContext())
                .load(Constants.IMG_PATH + data.getGroupAdmin().getAvatar())
                .placeholder(R.color.blue)
                .into(binding.memberImage);

        binding.memberName.setText(data.getGroupAdmin().getUserId());

        if (data.isVisibility()){
            binding.privateLayout.setBackgroundResource(0);
            binding.privateNote.setVisibility(View.GONE);
            binding.privateSelected.setVisibility(View.GONE);

            binding.publicLayout.setBackgroundResource(R.drawable.selected_gradient_stroke);
            binding.publicNote.setVisibility(View.VISIBLE);
            binding.publicSelected.setVisibility(View.VISIBLE);
        }else {
            binding.publicLayout.setBackgroundResource(0);
            binding.publicNote.setVisibility(View.GONE);
            binding.publicSelected.setVisibility(View.GONE);

            binding.privateLayout.setBackgroundResource(R.drawable.selected_gradient_stroke);
            binding.privateNote.setVisibility(View.VISIBLE);
            binding.privateSelected.setVisibility(View.VISIBLE);
        }


    }

    private void initClickEvents() {
        binding.jgdToolbar.back.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigateUp();
        });

        binding.jgdToolbar.info.setOnClickListener(view1 -> {
            final BottomSheetDialog bt = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
            View carDetailView = LayoutInflater.from(requireContext()).inflate(R.layout.group_options_dialogue, null, false);

            ConstraintLayout chatLayout = carDetailView.findViewById(R.id.options_chat_layout);
            ConstraintLayout deleteLayout = carDetailView.findViewById(R.id.deleteLAYOUT);
            TextView removeText = deleteLayout.findViewById(R.id.tv_remove);

            removeText.setText("Remove Group");

            chatLayout.setOnClickListener(view -> {
                bt.cancel();
                Navigation.findNavController(requireView()).navigate(R.id.chatroom);
            });

            deleteLayout.setOnClickListener(view -> {
                bt.cancel();
                membersViewModel = new GroupMembersViewModel((String.valueOf(data.getId())),"","","");
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
            Navigation.findNavController(view).navigate(R.id.action_createdGroupDetail_to_chatroom);
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