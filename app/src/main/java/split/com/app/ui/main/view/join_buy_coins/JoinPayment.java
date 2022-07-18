package split.com.app.ui.main.view.join_buy_coins;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.gson.Gson;

import split.com.app.R;
import split.com.app.databinding.FragmentJoinPaymentBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.view.join_plans.CheckoutActivity;
import split.com.app.ui.main.viewmodel.join_group.JoinGroupViewModel;
import split.com.app.utils.Constants;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;


public class JoinPayment extends Fragment {

    FragmentJoinPaymentBinding binding;
    JoinGroupViewModel viewModel;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
            if (upi_id.isEmpty()) {
                binding.dErrorMessage.setText("Enter Upi id");
                binding.dErrorMessage.setVisibility(View.VISIBLE);
            } else {

                MySharedPreferences mySharedPreferences = new MySharedPreferences(Split.getAppContext());
                String group_id = mySharedPreferences.getData(Split.getAppContext(), "GroupID");

                if (!group_id.isEmpty()) {
                    viewModel = new JoinGroupViewModel(group_id, "Constants.USER_EMAIL","");
                    viewModel.init();
                    viewModel.getData().observe(getViewLifecycleOwner(), joinGroupModel -> {
                        if (joinGroupModel.isSuccess()) {
                            Bundle bundle = new Bundle();
                            Gson gson = new Gson();
                            String groupDATA = gson.toJson(joinGroupModel);
                            bundle.putString("group_credentials", groupDATA);
                            bundle.putString("plan_url", joinGroupModel.getSubscriptions().getShortUrl());

//                          Navigation.findNavController(view1).navigate(R.id.action_joinPayment_to_joinPlans,bundle);

                            Intent checkoutIntent = new Intent(requireContext(), CheckoutActivity.class);
                            checkoutIntent.putExtra("group_id", group_id);
                            checkoutIntent.putExtra("upi_id", upi_id);
                            checkoutIntent.putExtra("subscription_id", joinGroupModel.getSubscriptions().getId());
                            checkoutIntent.putExtra("group_credentials", groupDATA);
                            startActivity(checkoutIntent);
                            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                        } else {
                            failureDialogue(joinGroupModel.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void failureDialogue(String message) {
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
            Navigation.findNavController(requireView()).navigate(R.id.home2);
            //ActivityUtil.gotoHome(Split.getAppContext());
        });
    }
}