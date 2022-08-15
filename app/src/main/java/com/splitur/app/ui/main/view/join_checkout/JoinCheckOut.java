package com.splitur.app.ui.main.view.join_checkout;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.splitur.app.R;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.model.group_detail.DataItem;
import com.splitur.app.data.model.settings.SettingsResponse;
import com.splitur.app.databinding.FragmentJoinCheckOutBinding;
import com.splitur.app.ui.main.view.WebViewActivity;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.view.join_plans.CheckoutActivity;
import com.splitur.app.ui.main.viewmodel.CheckOutViewModel;
import com.splitur.app.ui.main.viewmodel.join_checkout_viewmodel.JoinCheckoutViewModel;
import com.splitur.app.ui.main.viewmodel.join_group.JoinGroupViewModel;
import com.splitur.app.utils.Constants;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JoinCheckOut extends Fragment {

    FragmentJoinCheckOutBinding binding;
    DataItem dataItem;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    JoinCheckoutViewModel viewModel;
    CheckOutViewModel checkOutViewModel;

    String value;

    boolean isPromoValid = false;
    String discount_amount , discount_per;
    String code = "";

    JoinGroupViewModel joinGroupViewModel;
    String commission;
    Intent webViewIntent;
    private String urlTerms = "nil";
    private String urlPrivacy = "nil";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJoinCheckOutBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        getCommissionValue();

        binding.jcToolbar.title.setText("Join");
        binding.profile2.count.setText("143 coins");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            String data = getArguments().getString("groupDetail");

            Gson gson = new Gson();
            dataItem = gson.fromJson(data, DataItem.class);

            setData(dataItem);
        }
        fetchSettingsFromServer();
        webViewIntent = new Intent(requireContext(), WebViewActivity.class);

        initClickListeners();

    }

    @Override
    public void onStart() {
        super.onStart();
        Call<JsonObject> call = ApiManager.getRestApiService().getGroupAmount(dataItem.getId());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        final String amount = response.body().get("data").getAsString();
                        binding.btnJoin.setText(String.format("Join for %s Coins", amount));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "ERROR: Group total not set", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCommissionValue() {
        Call<SettingsResponse> call = ApiManager.getRestApiService().getSettings();
        call.enqueue(new Callback<SettingsResponse>() {
            @Override
            public void onResponse(@NonNull Call<SettingsResponse> call, @NonNull Response<SettingsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        commission = response.body().getData().getCommissionPercentage();

                        final double fee = (Double.parseDouble(value) / 100) * Double.parseDouble(commission);
                        final String roundedFee = String.format(Locale.getDefault(), "%.2f", fee);
                        binding.payment.setText(roundedFee);
//String totalCoins = String.valueOf(Double.parseDouble(value) + Double.parseDouble(commission));
//binding.btnJoin.setText(String.format("Join for %s Coins", totalCoins));

                    } else {
                        Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SettingsResponse> call, @NonNull Throwable t) {
                Log.e("Commission Error", "onFailure: ", t);
            }
        });
    }


    private void setData(DataItem dataItem) {
        try {
            binding.profile2.netflix.setText(dataItem.getTitle());
            binding.profile2.userImage.setImageResource(Constants.getAvatarIcon(requireContext(), Integer.parseInt(dataItem.getGroupAdmin().getAvatar())));

//            Glide.with(requireContext())
//                    .load(Constants.IMG_PATH + dataItem.getGroupAdmin().getAvatar())
//                    .placeholder(R.color.images_placeholder)
//                    .into(binding.profile2.userImage);
            binding.profile2.userName.setText(String.format("@%s", dataItem.getGroupAdmin().getUserId()));
            String coin = String.valueOf(dataItem.getCostPerMember());
            double coinFloat = Double.parseDouble(coin);
            value = String.valueOf(Math.round(((coinFloat * 30) / 100) + coinFloat));
            binding.profile2.count.setText(String.format("%s Coins", value));
            binding.priceValue.setText(String.format("%s Coins", value));

//            final double fee = (Double.parseDouble(value) / 100) * 2.5;
//            final String roundedFee = String.format("%.2f", fee);

            binding.payment.setText(commission);
//            String totalCoins = String.valueOf(Math.round(Double.parseDouble(value) + Double.parseDouble(commission)));
//            binding.btnJoin.setText(String.format("Join for %s Coins", totalCoins));

//            MySharedPreferences mySharedPreferences = new MySharedPreferences(Split.getAppContext());
//            mySharedPreferences.saveData(Split.getAppContext(), "GroupID", String.valueOf(dataItem.getId()));

        } catch (NullPointerException e) {

        }

    }

    private void initClickListeners() {

        binding.jcToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.next.setOnClickListener(view -> {
            dialogBuilder = new AlertDialog.Builder(requireContext());

            View layoutView = getLayoutInflater().inflate(R.layout.promocode_dialogue, null);
            TextView apply = (TextView) layoutView.findViewById(R.id.apply_code);
            TextView status = (TextView) layoutView.findViewById(R.id.promo_status);
            TextView message = (TextView) layoutView.findViewById(R.id.codeView);

            EditText promo_code = (EditText) layoutView.findViewById(R.id.ed_promocode);

            dialogBuilder.setView(layoutView);
            alertDialog = dialogBuilder.create();
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimations;
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();
            apply.setOnClickListener(view1 -> {
                code = promo_code.getText().toString().trim();

                if (code.isEmpty()) {

                    status.setText("Enter Promo code");
                    status.setVisibility(View.VISIBLE);

                } else {
                    viewModel = new JoinCheckoutViewModel(Constants.ID, code);
                    viewModel.initPromo();
                    viewModel.getPromoData().observe(getViewLifecycleOwner(), promoModel -> {
                        if (promoModel.isSuccess()) {
                            if (promoModel.getData() != null) {

                                isPromoValid = true;

                                discount_per = String.valueOf(Math.round(promoModel.getData().getPromoDiscount()));

                                int amount_of_discount = (int) (Integer.parseInt(value) * Math.round(promoModel.getData().getPromoDiscount()) / 100);
                                discount_amount = String.valueOf(amount_of_discount);
                                status.setText("Success!");
                                status.setTextColor(Color.parseColor("#0FB900"));
                                status.setVisibility(View.VISIBLE);
                                message.setText("You will receive " + amount_of_discount + " Coins in your account after you join the group.");
                                message.setVisibility(View.VISIBLE);

                                applyPromo(discount_amount);
                                alertDialog.dismiss();

                            }
                        } else {

                            isPromoValid = false;

                            status.setText("Expired!");
                            status.setTextColor(Color.parseColor("#FF5722"));
                            status.setVisibility(View.VISIBLE);
                            message.setText("This Promocode as expired, Please try a diffrent Promocode.");
                            message.setVisibility(View.VISIBLE);
                        }
                    });

//                    status.setVisibility(View.GONE);
//                    alertDialog.dismiss();
                }
            });
        });


        binding.termsUseText.setOnClickListener(view -> {
            if (!urlTerms.equals("nil")) {
                webViewIntent.putExtra("url", urlTerms);
                webViewIntent.putExtra("title", "Terms Of Use");
                startActivity(webViewIntent);
            }
        });

        binding.policyText.setOnClickListener(view -> {
            if (!urlPrivacy.equals("nil")) {
                webViewIntent.putExtra("url", urlPrivacy);
                webViewIntent.putExtra("title", "Privacy Policy");
                startActivity(webViewIntent);
            }
        });


        binding.btnJoin.setOnClickListener(view -> {

            viewModel = new JoinCheckoutViewModel(Constants.ID, "");
            viewModel.init();
            viewModel.getData().observe(getViewLifecycleOwner(), basicModel -> {
                if (basicModel.isStatus()) {
                    nAV();
                    //Navigation.findNavController(requireView()).navigate(R.id.action_joinCheckOut_to_joinPayment);
                } else {
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

                        if (emailId.isEmpty()) {
                            error.setText("Enter email id");
                            error.setVisibility(View.VISIBLE);
                        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
                            error.setText("Enter valid email id");
                            error.setVisibility(View.VISIBLE);
                        } else {
                            error.setVisibility(View.GONE);
                            Constants.JoinEmail = emailId;
                            nAV();
                            alertDialog.dismiss();
                        }
                    });

                }
            });


        });
    }

    private void applyPromo(String amount_of_discount) {
        if (isPromoValid) {
            binding.tvDiscount.setText(discount_per + "% OFF (" + code + ")");
            binding.discountAmount.setText("- " + amount_of_discount);
            binding.tvCredit.setVisibility(View.VISIBLE);
            binding.tvDiscount.setVisibility(View.VISIBLE);
            binding.discountAmount.setVisibility(View.VISIBLE);

        }
    }

    private void nAV() {
//        Navigation.findNavController(requireView()).navigate(R.id.action_joinCheckOut_to_joinPayment);
        binding.loadingView.setVisibility(View.VISIBLE);

//        MySharedPreferences mySharedPreferences = new MySharedPreferences(Split.getAppContext());
//        String group_id = mySharedPreferences.getData(Split.getAppContext(), "GroupID");
        String group_id = String.valueOf(dataItem.getId());
        String group_admin_id = String.valueOf(dataItem.getGroupAdmin().getId());
        if (!group_id.isEmpty()) {
            joinGroupViewModel = new JoinGroupViewModel(group_id, Constants.USER_EMAIL, code);
            joinGroupViewModel.init();
            joinGroupViewModel.getData().observe(getViewLifecycleOwner(), joinGroupModel -> {
                if (joinGroupModel.isSuccess()) {
                    Bundle bundle = new Bundle();
                    Gson gson = new Gson();
                    String groupDATA = gson.toJson(joinGroupModel);
                    bundle.putString("group_credentials", groupDATA);
                    bundle.putString("plan_url", joinGroupModel.getSubscriptions().getShortUrl());


                    checkOutViewModel = new CheckOutViewModel();
                    checkOutViewModel.init();
                    checkOutViewModel.getData().observe(getViewLifecycleOwner(), secretKeyModel -> {
                        if (secretKeyModel.isStatus()) {
                            String secret_key = secretKeyModel.getKey();

                            Intent checkoutIntent = new Intent(requireContext(), CheckoutActivity.class);
                            checkoutIntent.putExtra("group_id", group_id);
                            checkoutIntent.putExtra("subscription_id", joinGroupModel.getSubscriptions().getId());
                            checkoutIntent.putExtra("group_credentials", groupDATA);
                            checkoutIntent.putExtra("secret_key", secret_key);
                            checkoutIntent.putExtra("group_admin_id", group_admin_id);

                            startActivity(checkoutIntent);
                            binding.loadingView.setVisibility(View.GONE);
                            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    });

//                          Navigation.findNavController(view1).navigate(R.id.action_joinPayment_to_joinPlans,bundle);


                } else {
                    failureDialogue(joinGroupModel.getMessage());
                    binding.loadingView.setVisibility(View.GONE);

                }
            });
        } else {

        }
    }

    private void failureDialogue(String message) {
        dialogBuilder = new AlertDialog.Builder(requireContext());
        dialogBuilder.setCancelable(false);
        View layoutView = getLayoutInflater().inflate(R.layout.already_member_dialogue, null);
        Button home = (Button) layoutView.findViewById(R.id.back_home);
        TextView reason = (TextView) layoutView.findViewById(R.id.tv_reason);
        reason.setText("You cannot join a group you have created");


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

    private void fetchSettingsFromServer() {
        Call<SettingsResponse> call = ApiManager.getRestApiService().getSettings();
        call.enqueue(new Callback<SettingsResponse>() {
            @Override
            public void onResponse(@NonNull Call<SettingsResponse> call, @NonNull Response<SettingsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        urlTerms = response.body().getData().getTermsAndConditionsUrl();
                        urlPrivacy = response.body().getData().getPrivayUrl();
                    } else {
                        Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SettingsResponse> call, @NonNull Throwable t) {
                Log.e("ActivityTerms", "onFailure: ", t);
            }
        });
    }


}