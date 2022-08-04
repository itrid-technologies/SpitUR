package com.splitur.app.ui.main.view.created_detail;

import android.content.Context;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import com.splitur.app.R;
import com.splitur.app.data.model.all_created_groupx.DataItem;
import com.splitur.app.databinding.FragmentCreatedGroupDetailBinding;
import com.splitur.app.ui.main.adapter.group_member.GroupMemberAdapter;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.viewmodel.memebers_viewmodel.GroupMembersViewModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.Split;


public class CreatedGroupDetail extends Fragment {

    FragmentCreatedGroupDetailBinding binding;
    DataItem data;

    View fragmentViewBack;

    GroupMembersViewModel membersViewModel;
    private List<com.splitur.app.data.model.group_member.DataItem> membersList;


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
            if (getArguments() != null) {
                Gson gson = new Gson();
                String groupData = getArguments().getString("createdGroupData");
                data = gson.fromJson(groupData, DataItem.class);

                setProfileData(data);
            }
        } catch (IllegalStateException e) {
            Log.e("group_detail", e.getMessage());
        }


        membersViewModel = new GroupMembersViewModel((String.valueOf(data.getId())), "", "", "", false);
        membersViewModel.init();
        membersViewModel.getPlan().observe(getViewLifecycleOwner(), groupMemberModel -> {
            if (groupMemberModel.isSuccess()) {
                if (groupMemberModel.getData().size() > 0) {
                    for (int i=0; i<= groupMemberModel.getData().size()-1; i++){
                        if (groupMemberModel.getData().get(i).getUser() != null){
                            membersList.add(groupMemberModel.getData().get(i));
                        }
                    }
//                    membersList.addAll(groupMemberModel.getData());
                    buildMembersList(membersList);
                } else {
                    binding.noMemberLayout.setVisibility(View.VISIBLE);
//                    binding.adminAsMember.setVisibility(View.GONE);
                    binding.groupMembersList.setVisibility(View.GONE);
                }
            }
        });


        textWatcher();

    }

    private void textWatcher() {

        binding.edPassword.setEnabled(false);
        binding.edUsername.setEnabled(false);

        binding.editUsername.setOnClickListener(view -> {

            if (binding.editUsername.getText().toString().equalsIgnoreCase("Edit")) {
                binding.edUsername.setEnabled(true);
                binding.editUsername.setText("Save");
                binding.editUsername.setTextColor(Color.parseColor("#9F9DF3"));
            }else if (binding.editUsername.getText().toString().equalsIgnoreCase("Save")){
                String updated_value = binding.edUsername.getText().toString().trim();
                updateEmailData(updated_value);
            }else {

            }
        });

        binding.editPass.setOnClickListener(view -> {
            if (binding.editPass.getText().toString().equalsIgnoreCase("Edit")) {
                binding.edPassword.setEnabled(true);
                binding.editPass.setText("Save");
                binding.editPass.setTextColor(Color.parseColor("#9F9DF3"));
            }else if (binding.editPass.getText().toString().equalsIgnoreCase("Save")){
                //String updated_value = binding.editPass.getText().toString().trim();
                String updated_value = binding.edPassword.getText().toString().trim();
                updatePassData(updated_value);
            }
        });



        binding.privateLayout.setOnClickListener(view -> {

            binding.publicLayout.setBackgroundResource(R.drawable.only_grey_stroke);
            binding.publicSelected.setVisibility(View.GONE);

            binding.privateLayout.setBackgroundResource(R.drawable.selected_gradient_stroke);
            binding.privateSelected.setVisibility (View.VISIBLE);

            updateVisibility(false);
        });

        binding.publicLayout.setOnClickListener(view -> {

            binding.privateLayout.setBackgroundResource(R.drawable.only_grey_stroke);
            binding.privateSelected.setVisibility(View.GONE);

            binding.publicLayout.setBackgroundResource(R.drawable.selected_gradient_stroke);
            binding.publicSelected.setVisibility(View.VISIBLE);

            updateVisibility(true);

        });

        binding.copyLink.setOnClickListener(view -> {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("WordKeeper", binding.profileLink.getText().toString());
            clipboard.setPrimaryClip(clip);

            Toast.makeText(Split.getAppContext(), "Copied", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateVisibility(boolean visibility) {
        membersViewModel = new GroupMembersViewModel((String.valueOf(data.getId())), "", "", "", visibility);
        membersViewModel.initUpdateVisibility();
        membersViewModel.getUpdate_visibility().observe(getViewLifecycleOwner(), basicModel -> {
            if (basicModel.isStatus()) {
                Toast.makeText(Split.getAppContext(), basicModel.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateEmailData(String updated_value) {
        membersViewModel = new GroupMembersViewModel((String.valueOf(data.getId())), "", updated_value, "", false);
        membersViewModel.initEmailUpdate();
        membersViewModel.getUpdate_email_data().observe(getViewLifecycleOwner(), basicModel -> {
            if (basicModel.isStatus().equalsIgnoreCase("true")) {
                Toast.makeText(Split.getAppContext(), basicModel.getMessage(), Toast.LENGTH_SHORT).show();
                binding.edUsername.setEnabled(false);
                binding.editUsername.setText("Edit");
                binding.editUsername.setTextColor(Color.parseColor("#F7931A"));
            }
        });
    }

    private void updatePassData(String updated_value) {
        membersViewModel = new GroupMembersViewModel((String.valueOf(data.getId())), "", "", updated_value, false);
        membersViewModel.initPassUpdate();
        membersViewModel.getUpdate_pass_data().observe(getViewLifecycleOwner(), basicModel -> {
            if (basicModel.isStatus().equalsIgnoreCase("true")) {
                Toast.makeText(Split.getAppContext(), basicModel.getMessage(), Toast.LENGTH_SHORT).show();
                binding.edPassword.setEnabled(false);
                binding.editPass.setText("Edit");
                binding.editPass .setTextColor(Color.parseColor("#F7931A"));
            }
        });
    }

    private void buildMembersList(List<com.splitur.app.data.model.group_member.DataItem> membersList) {
        if (membersList.size() > 0) {

            binding.noMemberLayout.setVisibility(View.GONE);
//            binding.adminAsMember.setVisibility(View.VISIBLE);
            binding.groupMembersList.setVisibility(View.VISIBLE);

            LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
            binding.groupMembersList.setLayoutManager(layoutManager);
            GroupMemberAdapter adapter = new GroupMemberAdapter(Split.getAppContext(), membersList);
            binding.groupMembersList.setAdapter(adapter);

            adapter.setOnRemoveListener(position -> {

                final BottomSheetDialog bt = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
                View carDetailView = LayoutInflater.from(requireContext()).inflate(R.layout.ask_to_remove_dialogue, null, false);
                ConstraintLayout deleteLayout = carDetailView.findViewById(R.id.deleteLAYOUT);
                TextView remove = carDetailView.findViewById(R.id.tv_remove);
                ImageView delete = carDetailView.findViewById(R.id.confirm_remove);
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

                remove.setText("Remove Member");


                delete.setOnClickListener(view -> {


                    membersViewModel = new GroupMembersViewModel((String.valueOf(data.getId())), String.valueOf(membersList.get(position).getUserId()), "", "", false);
                    membersViewModel.initRemoveMember();
                    membersViewModel.getMember_remove_data().observe(getViewLifecycleOwner(), basicModel -> {
                        if (basicModel.isStatus().equalsIgnoreCase("true")) {
                            adapter.removeAt(position);
                            bt.cancel();
                            displayRemovedDialogue("Group member Removed");
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
//        Glide.with(Split.getAppContext())
//                .load(Constants.IMG_PATH + data.getGroupAdmin().getAvatar())
//                .placeholder(R.color.images_placeholder)
//                .into(binding.detailProfile.userImage);
        binding.detailProfile.userImage.setImageResource(Constants.getAvatarIcon(requireContext(), Integer.parseInt(data.getGroupAdmin().getAvatar())));

        binding.detailProfile.userName.setText("@" + data.getGroupAdmin().getUserId());
        binding.detailProfile.count.setText((String.valueOf(data.getSlots())) + " Slots");

        binding.edUsername.setText(data.getEmail());
        binding.edPassword.setText(data.getPassword());

//        //group_admin
//        Glide.with(Split.getAppContext())
//                .load(Constants.IMG_PATH + data.getGroupAdmin().getAvatar())
//                .placeholder(R.color.images_placeholder)
//                .into(binding.memberImage);
//
//        binding.memberName.setText(data.getGroupAdmin().getUserId());

        if (data.isVisibility()) {
            binding.privateLayout.setBackgroundResource(R.drawable.only_grey_stroke);
            binding.privateSelected.setVisibility(View.GONE);

            binding.publicLayout.setBackgroundResource(R.drawable.selected_gradient_stroke);
            binding.publicSelected.setVisibility(View.VISIBLE);
        } else {
            binding.publicLayout.setBackgroundResource(R.drawable.only_grey_stroke);
            binding.publicSelected.setVisibility(View.GONE);

            binding.privateLayout.setBackgroundResource(R.drawable.selected_gradient_stroke);
            binding.privateSelected.setVisibility(View.VISIBLE);
        }


    }

    private void initClickEvents() {

        binding.profileLink.setText("https://play.google.com/store/apps/details?id=split.com.app&referrer="+Constants.ID);

        binding.cgdToolbar.back.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigateUp();
        });

        binding.cgdToolbar.info.setOnClickListener(view1 -> {
            final BottomSheetDialog bt = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
            View carDetailView = LayoutInflater.from(requireContext()).inflate(R.layout.group_options_dialogue, null, false);

            ConstraintLayout chatLayout = carDetailView.findViewById(R.id.options_chat_layout);
            ConstraintLayout deleteLayout = carDetailView.findViewById(R.id.deleteLAYOUT);
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

            ImageView confirm = deleteLayout.findViewById(R.id.confirm_remove);
            TextView removeText = deleteLayout.findViewById(R.id.tv_remove);


            removeText.setText("Delete Group");

            chatLayout.setOnClickListener(view -> {
                bt.cancel();
                Navigation.findNavController(requireView()).navigate(R.id.chatroom);
            });

            confirm.setOnClickListener(view -> {
                bt.cancel();
                membersViewModel = new GroupMembersViewModel((String.valueOf(data.getId())), "", "", "", false);
                membersViewModel.initRemoveGroup();
                membersViewModel.getRemove_data().observe(getViewLifecycleOwner(), basicModel -> {
                    if (basicModel.isStatus().equalsIgnoreCase("true")) {
                        displayRemovedDialogue("Group Deleted");
                    }
                });
            });


            bt.setContentView(carDetailView);
            bt.show();
        });


        binding.btnGroupChat.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("groupId", String.valueOf(data.getId()));
            Navigation.findNavController(view).navigate(R.id.action_createdGroupDetail_to_chatroom, bundle);
        });

        binding.checkMesageLayout.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("receiverId", String.valueOf(data.getUserId()));
            bundle.putString("groupId", String.valueOf(data.getId()));
            bundle.putBoolean("ask_otp", false);

            Navigation.findNavController(view).navigate(R.id.action_createdGroupDetail_to_memberChat, bundle);
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