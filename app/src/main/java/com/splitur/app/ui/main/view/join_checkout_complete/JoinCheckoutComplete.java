package com.splitur.app.ui.main.view.join_checkout_complete;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import com.google.gson.JsonObject;
import com.splitur.app.R;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.model.basic_model.BasicModel;
import com.splitur.app.data.model.join_group.JoinGroupModel;
import com.splitur.app.databinding.FragmentJoinCheckoutCompleteBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JoinCheckoutComplete extends Fragment {

   FragmentJoinCheckoutCompleteBinding binding;
   JoinGroupModel joinGroupModel;
   private String groupAdminId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJoinCheckoutCompleteBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.jccToolbar.title.setText("Group Joined");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Glide.with(Split.getAppContext()).load(R.drawable.success_gif).into(binding.successGif);


        if (getArguments() != null){
            String response = getArguments().getString("group_credentials");
            groupAdminId = getArguments().getString("group_admin_id");
            Gson gson = new Gson();
            joinGroupModel = gson.fromJson(response, JoinGroupModel.class);
            setData(joinGroupModel);
        }

        initClickListeners();
    }

    private void initClickListeners() {

        binding.jccToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.copyEmail.setOnClickListener(view -> {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("WordKeeper",binding.groupEmail.getText().toString());
            clipboard.setPrimaryClip(clip);

            Toast.makeText(Split.getAppContext(), "Copied", Toast.LENGTH_SHORT).show();
        });

        binding.copyPass.setOnClickListener(view -> {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("WordKeeper",binding.groupPass.getText().toString());
            clipboard.setPrimaryClip(clip);

            Toast.makeText(Split.getAppContext(), "Copied", Toast.LENGTH_SHORT).show();

        });

        binding.btnHome.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.home2);
        });

        binding.tvRequest.setOnClickListener(view -> {

            Bundle bundle = new Bundle();
            bundle.putString("groupId", String.valueOf(joinGroupModel.getData().getGroupId()));
            bundle.putString("receiverId", groupAdminId);
            bundle.putBoolean("ask_otp", true);
            Navigation.findNavController(view).navigate(R.id.action_joinCheckoutComplete_to_memberChat,bundle);
        });

        binding.workingIcon.setOnClickListener(view -> {
            isGroupWorking(true);
        });

        binding.notWorkingIcon.setOnClickListener(view -> {
            isGroupWorking(false);
        });

        binding.btnAskAdmin.setOnClickListener(this::callAdminContactDialogue);
    }

    private void callAdminContactDialogue(View view) {
        AlertDialog.Builder dialogBuilder;
        AlertDialog alertDialog;
        dialogBuilder = new AlertDialog.Builder(requireContext());
        dialogBuilder.setCancelable(false);
        View layoutView = getLayoutInflater().inflate(R.layout.contact_admin_dialogue, null);
        Button contactBtn = (Button) layoutView.findViewById(R.id.btn_contact_admin);


        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimations;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        contactBtn.setOnClickListener(view1 -> {
            try {
            alertDialog.dismiss();
            Bundle bundle = new Bundle();
            bundle.putString("groupId", String.valueOf(joinGroupModel.getData().getGroupId()));
            bundle.putString("receiverId", groupAdminId);
            bundle.putBoolean("ask_otp", true);
            moveToMemberChat(bundle,view);

            }catch (IllegalStateException e){
                Log.e("MemberChat",e.getMessage());
            }
        });

    }

    private void moveToMemberChat(Bundle bundle, View view) {
        Navigation.findNavController(view).navigate(R.id.action_joinCheckoutComplete_to_memberChat,bundle);

    }

    private void isGroupWorking(boolean status) {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("group_id", String.valueOf(joinGroupModel.getData().getGroupId()));
        object.addProperty("is_working",status);

        Call<BasicModel> call = ApiManager.getRestApiService().checkGroupCredentialsStatus("Bearer "+token,object);
        call.enqueue(new Callback<BasicModel>() {
            @Override
            public void onResponse(@NonNull Call<BasicModel> call, @NonNull Response<BasicModel> response) {
                BasicModel basicModel =  response.body();
              //  if (basicModel.isStatus()){
                    binding.workingIcon.setVisibility(View.GONE);
                    binding.notWorkingIcon.setVisibility(View.GONE);
                    binding.btnAskAdmin.setVisibility(View.VISIBLE);
                //}
            }

            @Override
            public void onFailure(@NonNull Call<BasicModel> call, @NonNull Throwable t) {

            }
        });
    }

    private void setData(JoinGroupModel data) {
        binding.groupEmail.setText(data.getData().getEmail());
        binding.groupPass.setText(data.getData().getPassword());
        if (data.getData().getSubCategory().getValidationType() != null){
            String otpAuth = data.getData().getSubCategory().getValidationType();
            if (otpAuth.equalsIgnoreCase("otp")){
                binding.tvRequest.setVisibility(View.VISIBLE);
                binding.send.setVisibility(View.VISIBLE);
                binding.view1.setVisibility(View.VISIBLE);
            }else {
                binding.tvRequest.setVisibility(View.GONE);
                binding.send.setVisibility(View.GONE);
                binding.view1.setVisibility(View.GONE);
            }
        }

    }
}