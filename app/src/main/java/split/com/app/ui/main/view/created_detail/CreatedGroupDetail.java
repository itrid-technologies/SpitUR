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
import split.com.app.ui.main.adapter.group_member.GroupMemberAdapter;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.viewmodel.memebers_viewmodel.GroupMembersViewModel;
import split.com.app.utils.Constants;
import split.com.app.utils.Split;


public class CreatedGroupDetail extends Fragment {

  FragmentCreatedGroupDetailBinding binding;
  DataItem data;

  View fragmentViewBack;

  GroupMembersViewModel membersViewModel;
  private List<split.com.app.data.model.group_member.DataItem> membersList;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreatedGroupDetailBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.cgdToolbar.title.setText("Group Created");
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


        membersViewModel = new GroupMembersViewModel((String.valueOf(data.getId())),"","","",false);
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

        binding.privateLayout.setOnClickListener(view -> {

            binding.publicLayout.setBackgroundResource(0);
            binding.publicNote.setVisibility(View.GONE);
            binding.publicSelected.setVisibility(View.GONE);

            binding.privateLayout.setBackgroundResource(R.drawable.selected_gradient_stroke);
            binding.privateNote.setVisibility(View.VISIBLE);
            binding.privateSelected.setVisibility(View.VISIBLE);

            updateVisibility(false);
        });

        binding.publicLayout.setOnClickListener(view -> {

            binding.privateLayout.setBackgroundResource(0);
            binding.privateNote.setVisibility(View.GONE);
            binding.privateSelected.setVisibility(View.GONE);

            binding.publicLayout.setBackgroundResource(R.drawable.selected_gradient_stroke);
            binding.publicNote.setVisibility(View.VISIBLE);
            binding.publicSelected.setVisibility(View.VISIBLE);

            updateVisibility(true);

        });
    }

    private void updateVisibility(boolean visibility) {
        membersViewModel = new GroupMembersViewModel((String.valueOf(data.getId())), "","","",visibility);
        membersViewModel.initUpdateVisibility();
        membersViewModel.getUpdate_visibility().observe(getViewLifecycleOwner(),basicModel -> {
            if (basicModel.isStatus()){
                Toast.makeText(Split.getAppContext(), basicModel.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateEmailData(String updated_value) {
        membersViewModel = new GroupMembersViewModel((String.valueOf(data.getId())), "",updated_value,"",false);
        membersViewModel.initEmailUpdate();
        membersViewModel.getUpdate_email_data().observe(getViewLifecycleOwner(),basicModel -> {
            if (basicModel.isStatus()){
                Toast.makeText(Split.getAppContext(), basicModel.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePassData(String updated_value) {
        membersViewModel = new GroupMembersViewModel((String.valueOf(data.getId())), "","",updated_value,false);
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

                    displayRemovedDialogue("Group member Removed");

//                    membersViewModel = new GroupMembersViewModel((String.valueOf(data.getId())), String.valueOf(membersList.get(position).getUserId()),"","",false);
//                    membersViewModel.initRemoveMember();
//                    membersViewModel.getMember_remove_data().observe(getViewLifecycleOwner(),basicModel -> {
//                        if (basicModel.isStatus()){
//                            displayRemovedDialogue();
//                        }
//                    });
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
        binding.cgdToolbar.back.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigateUp();
        });

        binding.cgdToolbar.info.setOnClickListener(view1 -> {
            final BottomSheetDialog bt = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
            View carDetailView = LayoutInflater.from(requireContext()).inflate(R.layout.group_options_dialogue, null, false);

            ConstraintLayout chatLayout = carDetailView.findViewById(R.id.options_chat_layout);
            ConstraintLayout deleteLayout = carDetailView.findViewById(R.id.deleteLAYOUT);
            ImageView confirm = deleteLayout.findViewById(R.id.confirm_remove);
            TextView removeText = deleteLayout.findViewById(R.id.tv_remove);


            removeText.setText("Delete Group");

            chatLayout.setOnClickListener(view -> {
                bt.cancel();
                Navigation.findNavController(requireView()).navigate(R.id.chatroom);
            });

            confirm.setOnClickListener(view -> {
                bt.cancel();
                displayRemovedDialogue("Group Removed");
//                membersViewModel = new GroupMembersViewModel((String.valueOf(data.getId())),"","","",false);
//                membersViewModel.initRemoveGroup();
//                membersViewModel.getRemove_data().observe(getViewLifecycleOwner(),basicModel -> {
//                    if (basicModel.isStatus()){
//                         displayRemovedDialogue();
//                    }
//                });
            });


            bt.setContentView(carDetailView);
            bt.show();
        });


        binding.btnGroupChat.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("groupId", String.valueOf(data.getId()));
            Navigation.findNavController(view).navigate(R.id.action_createdGroupDetail_to_chatroom,bundle);
        });

        binding.checkMesageLayout.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("groupId", String.valueOf(data.getId()));
            Navigation.findNavController(view).navigate(R.id.action_createdGroupDetail_to_memberChat,bundle);
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