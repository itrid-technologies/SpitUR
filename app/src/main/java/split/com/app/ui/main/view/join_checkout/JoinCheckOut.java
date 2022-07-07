package split.com.app.ui.main.view.join_checkout;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import split.com.app.R;
import split.com.app.data.model.group_detail.DataItem;
import split.com.app.databinding.FragmentJoinCheckOutBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.view.join_plans.CheckoutActivity;
import split.com.app.ui.main.viewmodel.join_checkout_viewmodel.JoinCheckoutViewModel;
import split.com.app.ui.main.viewmodel.join_group.JoinGroupViewModel;
import split.com.app.utils.Constants;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;


public class JoinCheckOut extends Fragment {

    FragmentJoinCheckOutBinding binding;
    DataItem dataItem;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    JoinCheckoutViewModel viewModel;
    String value;

    boolean isPromoValid = false;
    String discount_amount , code;

    JoinGroupViewModel joinGroupViewModel;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJoinCheckOutBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);

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

        initClickListeners();

        

    }

    private void setData(DataItem dataItem) {
        try {
            binding.profile2.netflix.setText(dataItem.getTitle());
            Glide.with(requireContext())
                    .load(Constants.IMG_PATH + dataItem.getGroupAdmin().getAvatar())
                    .placeholder(R.color.images_placeholder)
                    .into(binding.profile2.userImage);
            binding.profile2.userName.setText(String.valueOf(dataItem.getGroupAdmin().getUserId()));
            String coin = String.valueOf(dataItem.getCostPerMember());
            Double coinFloat = Double.parseDouble(coin);
            value = String.valueOf(Math.round(((coinFloat * 30) / 100) + coinFloat));
            binding.profile2.count.setText(value + " Coins");
            binding.priceValue.setText(value + " Coins");
            binding.payment.setText(String.valueOf(dataItem.getCostPerMember()));
            binding.btnJoin.setText("Join for " + value + " Coins");

            MySharedPreferences mySharedPreferences = new MySharedPreferences(Split.getAppContext());
            mySharedPreferences.saveData(Split.getAppContext(), "GroupID", String.valueOf(dataItem.getId()));

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

                    status.setText("Enter Promocode");
                    status.setVisibility(View.VISIBLE);

                } else {
                    viewModel = new JoinCheckoutViewModel(Constants.ID,code);
                    viewModel.initPromo();
                    viewModel.getPromoData().observe(getViewLifecycleOwner(),promoModel -> {
                        if (promoModel.isSuccess()){
                            if (promoModel.getData() != null){

                                isPromoValid = true;

                                int amount_of_discount = (int) (Integer.parseInt(value) * Math.round(promoModel.getData().getPromoDiscount()) / 100);
                                discount_amount = String.valueOf(amount_of_discount);
                                status.setText("Success!");
                                status.setTextColor(Color.parseColor("#0FB900"));
                                status.setVisibility(View.VISIBLE);
                                message.setText("You got " + amount_of_discount + "Coins discount");
                                message.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        applyPromo();
                                        alertDialog.dismiss();
                                    }
                                }, 3000); //Timer is in ms here.

                            }
                        }else {

                            isPromoValid = false;

                            status.setText("Expired!");
                            status.setTextColor(Color.parseColor("#FF5722"));
                            status.setVisibility(View.VISIBLE);
                        }
                    });

//                    status.setVisibility(View.GONE);
//                    alertDialog.dismiss();
                }
            });
        });


        binding.btnJoin.setOnClickListener(view -> {

            viewModel = new JoinCheckoutViewModel(Constants.ID,"");
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

    private void applyPromo() {
        if (isPromoValid){
            binding.tvDiscount.setText("Discount by using " + code);
            binding.discountAmount.setText("- " + discount_amount);
            binding.tvCredit.setVisibility(View.VISIBLE);
            binding.tvDiscount.setVisibility(View.VISIBLE);
            binding.discountAmount.setVisibility(View.VISIBLE);

        }
    }

    private void nAV() {
//        Navigation.findNavController(requireView()).navigate(R.id.action_joinCheckOut_to_joinPayment);

        MySharedPreferences mySharedPreferences = new MySharedPreferences(Split.getAppContext());
        String group_id = mySharedPreferences.getData(Split.getAppContext(), "GroupID");

        if (!group_id.isEmpty()) {
            joinGroupViewModel = new JoinGroupViewModel(group_id, Constants.USER_EMAIL);
            joinGroupViewModel.init();
            joinGroupViewModel.getData().observe(getViewLifecycleOwner(), joinGroupModel -> {
                if (joinGroupModel.isSuccess()) {
                    Bundle bundle = new Bundle();
                    Gson gson = new Gson();
                    String groupDATA = gson.toJson(joinGroupModel);
                    bundle.putString("group_credentials", groupDATA);
                    bundle.putString("plan_url", joinGroupModel.getSubscriptions().getShortUrl());

//                          Navigation.findNavController(view1).navigate(R.id.action_joinPayment_to_joinPlans,bundle);

                    Intent checkoutIntent = new Intent(requireContext(), CheckoutActivity.class);
                    checkoutIntent.putExtra("group_id", group_id);
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