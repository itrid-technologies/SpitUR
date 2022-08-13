package com.splitur.app.ui.main.view.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.skydoves.elasticviews.ElasticImageView;
import com.splitur.app.R;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.model.ChatWootAccountIdModel;
import com.splitur.app.data.model.get_avatar.AvatarItem;
import com.splitur.app.databinding.FragmentProfileBinding;
import com.splitur.app.ui.main.adapter.avatar_adapter.AdapterAvatars;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.view.splash.Splash;
import com.splitur.app.ui.main.viewmodel.avatar_viewmodel.AvatarViewModel;
import com.splitur.app.ui.main.viewmodel.profile_viewmodel.ProfileViewModel;
import com.splitur.app.ui.main.viewmodel.user_id.UserIdViewModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Profile extends Fragment {

    FragmentProfileBinding binding;
    private AvatarViewModel mViewModel;
    private List<AvatarItem> avatarList = new ArrayList<>();
    private final List<String> avatars = new ArrayList<>();
    private int currentIndex = 0;
    String id;

    ProfileViewModel viewModel;
    String userCoins;
    int count = 0;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        Dashboard.hideNav(false);



        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                count = 0;
            }
        });

        setProfileData();

        initClickListeners();


        mViewModel = new AvatarViewModel();


        MySharedPreferences pm = new MySharedPreferences(Split.getAppContext());
        id = pm.getData(Split.getAppContext(), "Id");
        viewModel = new ProfileViewModel(id, "", "", "", "");
        viewModel.initCoins();
        viewModel.getCoins_data().observe(getViewLifecycleOwner(), totalCoinsModel -> {
            if (totalCoinsModel.isStatus()) {
                userCoins = String.valueOf(totalCoinsModel.getCoins());
                binding.coinValue.setText(String.valueOf(Math.round(totalCoinsModel.getCoins())));
            }
        });
    }

    private void setProfileData() {
        String name  = Constants.USER_NAME;
        binding.name.setText(Constants.capitalize(name));
        String avatar = Constants.USER_AVATAR;
        binding.userImage.setImageResource(Constants.getAvatarIcon(requireContext(), Integer.parseInt(avatar)));
//        Glide.with(Split.getAppContext()).load(avatar).placeholder(R.color.images_placeholder).into(binding.userImage);

//        ReferralViewModel referralViewModel = new ReferralViewModel(Constants.ID,"2");
//        referralViewModel.init();
//        referralViewModel.getData().observe(getViewLifecycleOwner(),basicModel -> {
//            if (basicModel.isStatus().equalsIgnoreCase("true")){
//                Toast.makeText(requireContext(), basicModel.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initClickListeners() {

        binding.friendsLayout.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_profile2_to_friends);
        });

        binding.transactionLayout.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_profile2_to_transactions);

        });

        binding.payment.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_profile2_to_transactions);

        });

        binding.paymentIcon.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_profile2_to_transactions);

        });

        binding.transactionLayout.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_profile2_to_transactions);

        });

        binding.promotionLayout.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_profile2_to_promotion);
        });

        binding.refund.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_profile2_to_refund2);
        });

        binding.refundIcon.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_profile2_to_refund2);
        });

        binding.contactLayout.setOnClickListener(view -> {
            NavToContact();
        });

        binding.swap.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("Coins", userCoins);
            Navigation.findNavController(view).navigate(R.id.action_profile2_to_swapCoins, bundle);
        });

        binding.swapIcon.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("Coins", userCoins);
            Navigation.findNavController(view).navigate(R.id.action_profile2_to_swapCoins, bundle);

        });

        binding.legalLayout.setOnClickListener(v -> Navigation.findNavController(requireView()).navigate(R.id.action_profile2_to_legalContactFragment));

        binding.userImage.setOnClickListener(view -> {


            if (count == 0) {
                count = 1;

                //fetch user data
                viewModel = new ProfileViewModel("", "", "", "", "");
                viewModel.initUser();
                viewModel.getUser_data().observe(getViewLifecycleOwner(), activeUserModel -> {
                    if (activeUserModel.isStatus()) {

                        if (activeUserModel.getData() != null) {
                            final BottomSheetDialog bt = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
                            bt.setCanceledOnTouchOutside(false);
                            View profileView = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_user_information, null, false);

                            bt.getBehavior().addBottomSheetCallback(mBottomSheetBehaviorCallback);

                            EditText name = profileView.findViewById(R.id.profile_name);
                            EditText userid = profileView.findViewById(R.id.profile_id);
                            TextView message = profileView.findViewById(R.id.errorMessage);

                            EditText email = profileView.findViewById(R.id.profile_email);
                            ImageView image = profileView.findViewById(R.id.profile_image);
                            ElasticImageView logout = profileView.findViewById(R.id.logout_icon);
                            RecyclerView avatarRv = profileView.findViewById(R.id.profile_avatars);
                            ImageView previous = profileView.findViewById(R.id.previous_avatar);
                            ImageView next = profileView.findViewById(R.id.next_avatar);
                            Button save = profileView.findViewById(R.id.btn_save_profile);

//            MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
//
//            name.setText(preferences.getData(Split.getAppContext(), "userName"));
//            userid.setText(preferences.getData(Split.getAppContext(), "userId"));

                            name.setText(activeUserModel.getData().getName());
                            userid.setText(activeUserModel.getData().getUserId());
                            email.setText(activeUserModel.getData().getEmail());
                            avatarRv.setVisibility(View.GONE);
                            image.setVisibility(View.VISIBLE);

                            //Svg.INSTANCE.loadUrl(Constants.IMG_PATH + activeUserModel.getData().getAvatar(), image);
//                            Glide.with(requireContext()).load(Constants.IMG_PATH + activeUserModel.getData().getAvatar()).into(image);
                            image.setImageResource(Constants.getAvatarIcon(requireContext(), Integer.parseInt(activeUserModel.getData().getAvatar())));

                            Constants.ID = String.valueOf(activeUserModel.getData().getId());
                            Constants.USER_ID = activeUserModel.getData().getUserId();
                            Constants.USER_NAME = activeUserModel.getData().getName();
                            Constants.USER_EMAIL = activeUserModel.getData().getEmail();
                            Constants.USER_AVATAR =  activeUserModel.getData().getAvatar();
                            Constants.NUMBER = activeUserModel.getData().getPhone();

//                            MySharedPreferences sharedPreferences = new MySharedPreferences(Split.getAppContext());
//                            sharedPreferences.saveData(Split.getAppContext(), "userAvatar", Constants.IMG_PATH + activeUserModel.getData().getAvatar());
//                            sharedPreferences.saveData(Split.getAppContext(), "userName", activeUserModel.getData().getName());
//                            sharedPreferences.saveData(Split.getAppContext(), "userId", activeUserModel.getData().getUserId());

                            List<Integer> avatars1 = new ArrayList<>();
                            avatars1.add(1);
                            avatars1.add(2);
                            avatars1.add(3);
                            avatars1.add(4);
                            avatars1.add(5);
                            avatars1.add(6);
                            avatars1.add(7);
                            avatars1.add(8);
                            avatars1.add(9);
                            avatars1.add(10);
                            avatars1.add(11);
                            avatars1.add(12);
                            avatars1.add(13);
                            avatars1.add(14);
                            avatars1.add(15);
                            avatars1.add(16);
                            avatars1.add(17);
                            avatars1.add(18);
                            avatars1.add(19);
                            avatars1.add(20);
//                            avatarViewModel = new AvatarViewModel();
//                            avatarViewModel.init();
//                            avatarViewModel.getData().observe(getViewLifecycleOwner(), avatarModel -> {
//                                avatarList.addAll(avatarModel.getAvatar());
//                                for (int i = 0; i <= avatarList.size() - 1; i++) {
//                                    avatars.add(avatarList.get(i).getUrl());
//                                }
                            avatarRv.setHasFixedSize(true);
//                            avatarRv.setHorizontalScrollBarEnabled(false);
                            avatarRv.setLayoutManager(new LinearLayoutManager(Split.getAppContext(), RecyclerView.HORIZONTAL, false));
                            SnapHelper snapHelper = new PagerSnapHelper();
                            snapHelper.attachToRecyclerView(avatarRv);
                            avatarRv.setAdapter(new AdapterAvatars(getActivity(), avatars1));

                            image.setOnTouchListener(new View.OnTouchListener() {
                                @SuppressLint("ClickableViewAccessibility")
                                @Override
                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                    avatarRv.setVisibility(View.VISIBLE);
                                    image.setVisibility(View.GONE);
                                    return false;
                                }
                            });
                            

                            logout.setOnClickListener(view1 -> {

                                final BottomSheetDialog dialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
                                View layout = LayoutInflater.from(requireContext()).inflate(R.layout.ask_to_remove_dialogue, null, false);
                                ConstraintLayout deleteLayout = layout.findViewById(R.id.deleteLAYOUT);
                                TextView remove = layout.findViewById(R.id.tv_remove);
                                TextView tagline = layout.findViewById(R.id.delete_detail);
                                TextView confirm = layout.findViewById(R.id.confirm_tagline);
                                ImageView confirm_logout = layout.findViewById(R.id.confirm_remove);
                                ConstraintLayout confirm_layout = layout.findViewById(R.id.delete_layout);

                                deleteLayout.setOnClickListener(view2 -> {
                                    if (confirm_layout.getTag().equals("hidden")) {
                                        confirm_layout.setVisibility(View.VISIBLE);
                                        confirm_layout.setTag("visible");
                                    } else {
                                        confirm_layout.setVisibility(View.GONE);
                                        confirm_layout.setTag("hidden");
                                    }

                                });

                                remove.setText("Log Out");
                                tagline.setText("You can login back again using your Phone number and OTP");
                                confirm.setText("You want to logout");


                                confirm_logout.setOnClickListener(view2 -> {

                                    viewModel = new ProfileViewModel(String.valueOf(activeUserModel.getData().getId()), "", "", "", "");
                                    viewModel.initLogout();
                                    viewModel.getLogout().observe(getViewLifecycleOwner(), basicModel -> {
                                        if (basicModel.isStatus().equalsIgnoreCase("true")) {
                                            Intent intent = new Intent(requireContext(), Splash.class);
                                            startActivity(intent);

                                        }
                                    });


                                });

                                dialog.setContentView(layout);
                                dialog.show();
                            });


                            next.setOnClickListener(view1 -> {
                                if (currentIndex < avatars1.size() - 1) {//in range

                                    avatarRv.setVisibility(View.VISIBLE);
                                    image.setVisibility(View.GONE);

                                    currentIndex++;
                                    avatarRv.smoothScrollToPosition(currentIndex);
                                }
                            });
                            previous.setOnClickListener(view1 -> {
                                if (currentIndex > 0) {

                                    avatarRv.setVisibility(View.VISIBLE);
                                    image.setVisibility(View.GONE);

                                    currentIndex--;
                                    avatarRv.smoothScrollToPosition(currentIndex);
                                }
                            });

                            save.setOnClickListener(view1 -> {

                                String updated_name = name.getText().toString().trim();
                                String updated_id = userid.getText().toString().trim();
                                String updated_email = email.getText().toString().trim();
                                final Integer updatedAvatar = avatars1.get(currentIndex);

                                if (updated_id.equals(Constants.USER_ID)) {
                                    updateUserInformation(updated_name,updated_id,updated_email, String.valueOf(updatedAvatar));

                                } else {
                                    UserIdViewModel userIdViewModel = new UserIdViewModel(updated_id);
                                    userIdViewModel.init();
                                    userIdViewModel.getData().observe(getViewLifecycleOwner(), basicModel -> {
                                        if (!basicModel.isStatus()) {
                                            message.setVisibility(View.GONE);
                                            updateUserInformation(updated_name,updated_id,updated_email, String.valueOf(updatedAvatar));
                                        }else {
                                            message.setText(basicModel.getMessage());
                                            message.setVisibility(View.VISIBLE);
                                        }
                                    });
                                }
                            });

                            bt.setContentView(profileView);
                            bt.show();
                        }
                    }
                });
            }

        });
    }

    private void updateUserInformation(String updated_name, String updated_id, String updated_email, String updatedAvatar) {
        if (updated_id.equals(Constants.USER_ID)){

        }
        viewModel = new ProfileViewModel("", updated_name, updated_id, updatedAvatar, updated_email);
        viewModel.init();
        viewModel.getUpdate_profile().observe(getViewLifecycleOwner(), userUpdateModel -> {
            if (userUpdateModel.isSuccess()) {
                if (userUpdateModel.getData() != null) {
                    MySharedPreferences sharedPreferences1 = new MySharedPreferences(Split.getAppContext());
                    sharedPreferences1.saveData(Split.getAppContext(), "userAvatar", userUpdateModel.getData().getAvatar());
                    sharedPreferences1.saveData(Split.getAppContext(), "userName", userUpdateModel.getData().getName());
                    sharedPreferences1.saveData(Split.getAppContext(), "userId", userUpdateModel.getData().getUserId());

                    Toast.makeText(Split.getAppContext(), userUpdateModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void NavToContact() {
        Call<ChatWootAccountIdModel> call = ApiManager.getRestApiService().getAccountId();
        call.enqueue(new Callback<ChatWootAccountIdModel>() {
            @Override
            public void onResponse(@NonNull Call<ChatWootAccountIdModel> call, @NonNull Response<ChatWootAccountIdModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ChatWootAccountIdModel accountIdModel = response.body();
                    Constants.AccountId = Integer.parseInt(accountIdModel.getChatWootAccountId());
                    Constants.ChatApiKey = accountIdModel.getChat_api_key();
                    Constants.InboxId = Integer.parseInt(accountIdModel.getInbox_id());
                    Navigation.findNavController(requireView()).navigate(R.id.action_profile2_to_contactUs);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ChatWootAccountIdModel> call, @NonNull Throwable t) {
                Log.e("Account Id", "onFailure: ", t);
            }
        });
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                count = 0;
            } else {
                count = 1;
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };


    public static class RecyclerViewDisabler implements RecyclerView.OnItemTouchListener {

        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            return true;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) {

        }


    }


}