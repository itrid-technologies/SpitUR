package com.splitur.app.ui.main.view.join_checkout_terms;

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
import android.widget.EditText;
import android.widget.TextView;

import com.splitur.app.R;
import com.splitur.app.databinding.FragmentJoinTermsBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.utils.Constants;


public class JoinTerms extends Fragment {


    FragmentJoinTermsBinding binding;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJoinTermsBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.jtToolbar.title.setText("Join");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initClickListeners();
    }

    private void initClickListeners() {

        binding.jtToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.btnJoin.setOnClickListener(view -> {
            dialogBuilder = new AlertDialog.Builder(requireContext());
            View layoutView = getLayoutInflater().inflate(R.layout.enter_email_dialogue, null);
            TextView join = (TextView) layoutView.findViewById(R.id.d_join);
            TextView error = (TextView) layoutView.findViewById(R.id.d_errorMessage);

            EditText email = (EditText) layoutView.findViewById(R.id.ed_userEMAIL);

            dialogBuilder.setView(layoutView);
            alertDialog = dialogBuilder.create();
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimations;
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();
            join.setOnClickListener(view1 -> {
                String emailId = email.getText().toString().trim();

                if (emailId.isEmpty()){
                    error.setText("Enter email id");
                    error.setVisibility(View.VISIBLE);
                }else if (!Patterns.EMAIL_ADDRESS.matcher(emailId).matches()){
                    error.setText("Enter valid email id");
                    error.setVisibility(View.VISIBLE);
                }else {
                    error.setVisibility(View.GONE);
                    Constants.JoinEmail = emailId;
                    nAV();
                    alertDialog.dismiss();
                }
            });

        });
    }

    private void nAV() {
        Navigation.findNavController(requireView()).navigate(R.id.action_joinTerms_to_joinPayment);
    }
}