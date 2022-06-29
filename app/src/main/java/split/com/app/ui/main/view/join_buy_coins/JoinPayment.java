package split.com.app.ui.main.view.join_buy_coins;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import split.com.app.R;
import split.com.app.databinding.FragmentJoinPaymentBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.viewmodel.join_group.JoinGroupViewModel;
import split.com.app.utils.ActivityUtil;
import split.com.app.utils.Constants;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;


public class JoinPayment extends Fragment {

    FragmentJoinPaymentBinding binding;
    JoinGroupViewModel viewModel;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJoinPaymentBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.jpToolbar.title.setText("Buy Coins");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.jpToolbar.back.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigateUp();
        });

        binding.dJoin.setOnClickListener(view1 -> {

            String upi_id = binding.edUpi.getText().toString().trim();
            if (upi_id.isEmpty()){
                binding.dErrorMessage.setText("Enter Upi id");
                binding.dErrorMessage.setVisibility(View.VISIBLE);
            }else {


                MySharedPreferences mySharedPreferences = new MySharedPreferences(Split.getAppContext());

                String group_id = mySharedPreferences.getData(Split.getAppContext(), "GroupID");
                if (!group_id.isEmpty()) {
                    viewModel = new JoinGroupViewModel(group_id, Constants.JoinEmail, upi_id);
                    viewModel.init();
                    viewModel.getData().observe(getViewLifecycleOwner(), joinGroupModel -> {
                        if (joinGroupModel.isSuccess()) {
                            Bundle bundle = new Bundle();
                            Gson gson = new Gson();
                            String groupDATA = gson.toJson(joinGroupModel);
                            bundle.putString("group_credentials", groupDATA);
                            bundle.putString("plan_url", joinGroupModel.getSubscriptions().getShortUrl());

                            Navigation.findNavController(view1).navigate(R.id.action_joinPayment_to_joinPlans,bundle);
                        }else {
                            failureDialogue(joinGroupModel.getMessage());
                        }
                    });
                }
            }
    });
    }

    private void failureDialogue(String message){
        dialogBuilder = new AlertDialog.Builder(requireContext());
        dialogBuilder.setCancelable(false);
        View layoutView = getLayoutInflater().inflate(R.layout.already_member_dialogue, null);
        Button home = (Button) layoutView.findViewById(R.id.back_home);
        TextView reason = (TextView) layoutView.findViewById(R.id.tv_reason);
        reason.setText(message);


        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimations;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        home.setOnClickListener(view1 -> {
            alertDialog.dismiss();
            Navigation.findNavController(view1).navigate(R.id.home2);
            //ActivityUtil.gotoHome(Split.getAppContext());
        });
    }
}