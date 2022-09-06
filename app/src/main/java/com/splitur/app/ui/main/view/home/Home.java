package com.splitur.app.ui.main.view.home;

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

import java.util.ArrayList;
import java.util.List;

import com.splitur.app.R;
import com.splitur.app.data.model.HomeDataItem;
import com.splitur.app.data.model.active_user.ActiveUserModel;
import com.splitur.app.data.model.get_avatar.AvatarItem;
import com.splitur.app.data.model.home_categories.CategoryDataItems;
import com.splitur.app.data.model.popular_subcategory.DataItem;
import com.splitur.app.databinding.FragmentHomeBinding;
import com.splitur.app.ui.main.adapter.HomeSectionAdapter;
import com.splitur.app.ui.main.adapter.avatar_adapter.AdapterAvatars;
import com.splitur.app.ui.main.adapter.category_adapter.CategoryAdapter;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.view.profile.Profile;
import com.splitur.app.ui.main.view.splash.Splash;
import com.splitur.app.ui.main.viewmodel.UserOnlineStatusViewModel;
import com.splitur.app.ui.main.viewmodel.avatar_viewmodel.AvatarViewModel;
import com.splitur.app.ui.main.viewmodel.category_viewmodel.CategoryViewModel;
import com.splitur.app.ui.main.viewmodel.home_viewmodel.HomeViewModel;
import com.splitur.app.ui.main.viewmodel.profile_viewmodel.ProfileViewModel;
import com.splitur.app.ui.main.viewmodel.user_id.UserIdViewModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.SpanningLinearLayoutManager;
import com.splitur.app.utils.Split;


public class Home extends Fragment {

    FragmentHomeBinding binding;

    private CategoryViewModel mViewModel;
    private List<HomeDataItem> homeDataItems;

    private HomeViewModel homeViewModel;
    private List<CategoryDataItems> category_list;

    //    private List<DataItem> popularSubCategoryList;
    private List<DataItem> popularSubCategoryList;

    ProfileViewModel profileViewModel;
    private AvatarViewModel avatarViewModel;
    private List<AvatarItem> avatarList = new ArrayList<>();
//    private final List<String> avatars = new ArrayList<>();
    private int currentIndex = 0;

    int count = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        Dashboard.hideNav(false);

        // Glide.with(requireContext()).load(R.drawable.splitur_loading).into(binding.homeLoading);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        category_list = new ArrayList<>();
        homeDataItems = new ArrayList<>();

        binding.categoriesLst.setVisibility(View.GONE);
        // binding.popularLst.setVisibility(View.GONE);
        binding.homeSections.setVisibility(View.GONE);

        homeViewModel = new HomeViewModel();
        homeViewModel.init();
        homeViewModel.getHomeData().observe(getViewLifecycleOwner(), homeContentModel -> {
            if (homeContentModel.isSuccess()) {
                homeDataItems.addAll(homeContentModel.getData());
                getMoviesList();
            }
        });

        mViewModel = new CategoryViewModel();
        getCategories();
        getNotificationCount();
        getUserDetails();


        initClickListeners();
        getPopularList();
    }

    private void getNotificationCount() {
        NotificationViewModel notificationViewModel = new NotificationViewModel(0);
        notificationViewModel.initCount();
        notificationViewModel.getNotificationsCount().observe(getViewLifecycleOwner() , notificationCountModel -> {
            binding.notificationCount.setText(String.valueOf(notificationCountModel.getCount()));
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        UserOnlineStatusViewModel userOnlineStatusViewModel = new UserOnlineStatusViewModel(1);
        userOnlineStatusViewModel.init();
        userOnlineStatusViewModel.getData().observe(getViewLifecycleOwner(), basicModel -> {
            if (basicModel.isStatus().equals("true")){
                Log.e("user online/offline " , basicModel.getMessage());
            }
        });
    }



    private void getUserDetails() {
        profileViewModel = new ProfileViewModel("", "", "", "","");
        profileViewModel.initUser();
        profileViewModel.getUser_data().observe(getViewLifecycleOwner(), activeUserModel -> {
            if (activeUserModel.isStatus()) {
                if (activeUserModel.getData() != null) {
                    saveUserData(activeUserModel);
                }
            }
        });
    }


    private void initClickListeners() {
        binding.search.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_home2_to_joinSearch);
        });

        binding.notificationIcon.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_home2_to_notifications);
        });

        binding.viewAll.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_home2_to_joinSearch);
        });

        binding.userImage.setOnClickListener(view -> {

            if (count == 0) {
                count = 1;

                //fetch user data
                profileViewModel = new ProfileViewModel("", "", "", "","");
                profileViewModel.initUser();
                profileViewModel.getUser_data().observe(getViewLifecycleOwner(), activeUserModel -> {
                    if (activeUserModel.isStatus()) {

                        if (activeUserModel.getData() != null) {
                            final BottomSheetDialog bt = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
                            bt.setCanceledOnTouchOutside(false);
                            View profileView = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_user_information, null, false);

                            bt.getBehavior().addBottomSheetCallback(mBottomSheetBehaviorCallback);


                            EditText name = profileView.findViewById(R.id.profile_name);
                            EditText userid = profileView.findViewById(R.id.profile_id);
                            EditText email = profileView.findViewById(R.id.profile_email);
                            ImageView image = profileView.findViewById(R.id.profile_image);
                            ElasticImageView logout = profileView.findViewById(R.id.logout_icon);
                            TextView message = profileView.findViewById(R.id.errorMessage);

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
                            image.setImageResource(Constants.getAvatarIcon(requireContext(), Integer.parseInt(activeUserModel.getData().getAvatar())));
//                            Glide.with(Split.getAppContext()).load(Constants.IMG_PATH + activeUserModel.getData().getAvatar()).into(image);


                            saveUserData(activeUserModel);
                            MySharedPreferences sharedPreferences = new MySharedPreferences(Split.getAppContext());
                            sharedPreferences.saveData(Split.getAppContext(), "userAvatar",  activeUserModel.getData().getAvatar());
                            sharedPreferences.saveData(Split.getAppContext(), "userName", activeUserModel.getData().getName());
                            sharedPreferences.saveData(Split.getAppContext(), "userId", activeUserModel.getData().getUserId());

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
//
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

                                    profileViewModel = new ProfileViewModel(String.valueOf(activeUserModel.getData().getId()), "", "", "","");
                                    profileViewModel.initLogout();
                                    profileViewModel.getLogout().observe(getViewLifecycleOwner(), basicModel -> {
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
        profileViewModel = new ProfileViewModel("", updated_name, updated_id, updatedAvatar, updated_email);
        profileViewModel.init();
        profileViewModel.getUpdate_profile().observe(getViewLifecycleOwner(), userUpdateModel -> {
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

    private void getCategories() {
        mViewModel.init();
        mViewModel.getCategoryData().observe(getViewLifecycleOwner(), categoriesModel -> {
            if (categoriesModel.isSuccess()) {
                if (categoriesModel.getData().size() > 0) {
                    category_list.addAll(categoriesModel.getData());
                }
                buildCategoryRv();
            }
        });

    }

    private void buildCategoryRv() {
        SpanningLinearLayoutManager layoutManager = new SpanningLinearLayoutManager(Split.getAppContext(), RecyclerView.HORIZONTAL, false);
        binding.categoriesLst.suppressLayout(true);
        layoutManager.canScrollHorizontally();
        layoutManager.setSmoothScrollbarEnabled(false);
        binding.categoriesLst.setLayoutManager(layoutManager);

        CategoryAdapter adapter = new CategoryAdapter(Split.getAppContext(), category_list);
        binding.categoriesLst.setAdapter(adapter);

        adapter.setOnCategorySelectListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putString("CurrentCatId", String.valueOf(category_list.get(position).getId()));
            Navigation.findNavController(requireView()).navigate(R.id.action_home2_to_joinSearch, bundle);
        });
    }

    private void setProfileData() {

        String name  = Constants.USER_NAME;
        binding.name.setText(Constants.capitalize(name));
        String avatar = Constants.USER_AVATAR;
        if (avatar.isEmpty()){
            binding.userImage.setImageResource(R.drawable.user);
        }else {
            binding.userImage.setImageResource(Constants.getAvatarIcon(requireContext(), Integer.parseInt(avatar)));
        }
//        Glide.with(Split.getAppContext()).load(Constants.USER_AVATAR).placeholder(R.color.images_placeholder).into(binding.userImage);

    }

    private void getMoviesList() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.homeSections.setLayoutManager(layoutManager);
        HomeSectionAdapter adapter = new HomeSectionAdapter(Split.getAppContext(), homeDataItems, requireView());
        binding.homeSections.setAdapter(adapter);
        adapter.setOnViewAllClickListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putString("category_name", String.valueOf(homeDataItems.get(position).getTitle()));
            bundle.putString("CurrentCatId", String.valueOf(homeDataItems.get(position).getId()));
            Navigation.findNavController(requireView()).navigate(R.id.action_home2_to_joinSearch, bundle);
        });
    }


    private void getPopularList() {

        popularSubCategoryList = new ArrayList<>();

        homeViewModel = new HomeViewModel();
        homeViewModel.initPopular();
        homeViewModel.getPopularCategoryData().observe(getViewLifecycleOwner(), popularSubCategoryModel -> {
            if (popularSubCategoryModel.isSuccess()) {
                if (popularSubCategoryModel.getData().size() > 0) {
                    for (int i = 0; i <= 2; i++) {
                        popularSubCategoryList.add(popularSubCategoryModel.getData().get(i));
                    }
                    setDaATA(popularSubCategoryList);
                }

                // buildPopularCatRv();
            }
        });

//        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.HORIZONTAL, false);
//        binding.popularLst.setLayoutManager(layoutManager);
//        PopularHomeAdapter adapter = new PopularHomeAdapter(Split.getAppContext(), images, names);
//        binding.popularLst.setAdapter(adapter);
    }

    private void setDaATA(List<DataItem> popularSubCategoryList) {
        if (popularSubCategoryList.get(0).getCategory() != null) {

            binding.popularName.setText(popularSubCategoryList.get(0).getTitle());
            binding.popularIcons.setImageResource(
                    Constants.getCategoryIcon(requireContext(), popularSubCategoryList.get(0).getCategory().getId()));

        }

        if (popularSubCategoryList.get(1).getCategory() != null) {
            binding.popularIcons1.setImageResource(
                    Constants.getCategoryIcon(requireContext(), popularSubCategoryList.get(1).getCategory().getId()));
            binding.popularName1.setText(popularSubCategoryList.get(1).getTitle());

        }

        if (popularSubCategoryList.get(2).getCategory() != null) {

            binding.popularIcons2.setImageResource(
                    Constants.getCategoryIcon(requireContext(), popularSubCategoryList.get(2).getCategory().getId()));
            binding.popularName2.setText(popularSubCategoryList.get(2).getTitle());

        }

        binding.tvJoin.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("join_sub_cat_id", String.valueOf(popularSubCategoryList.get(0).getId()));//absolute adopter position
            bundle.putString("join_sub_cat_title", String.valueOf(popularSubCategoryList.get(0).getTitle()));

            Navigation.findNavController(view).navigate(R.id.action_home2_to_groupDetail, bundle);
        });

        binding.tvJoin1.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("join_sub_cat_id", String.valueOf(popularSubCategoryList.get(1).getId()));//absolute adopter position
            bundle.putString("join_sub_cat_title", String.valueOf(popularSubCategoryList.get(1).getTitle()));

            Navigation.findNavController(view).navigate(R.id.action_home2_to_groupDetail, bundle);
        });

        binding.tvJoin2.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("join_sub_cat_id", String.valueOf(popularSubCategoryList.get(2).getId()));//absolute adopter position
            bundle.putString("join_sub_cat_title", String.valueOf(popularSubCategoryList.get(2).getTitle()));

            Navigation.findNavController(view).navigate(R.id.action_home2_to_groupDetail, bundle);
        });


        binding.categoriesLst.setVisibility(View.VISIBLE);
        binding.homeSections.setVisibility(View.VISIBLE);
        binding.loadingView.setVisibility(View.GONE);
    }

//    private void buildPopularCatRv() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.HORIZONTAL, false);
//        binding.popularLst.setLayoutManager(layoutManager);
//        PopularHomeAdapter adapter = new PopularHomeAdapter(Split.getAppContext(), popularSubCategoryList);
//        binding.popularLst.setAdapter(adapter);
//
//        binding.categoriesLst.setVisibility(View.VISIBLE);
//        binding.popularLst.setVisibility(View.VISIBLE);
//        binding.homeSections.setVisibility(View.VISIBLE);
//        binding.loadingView.setVisibility(View.GONE);
//
//    }

    private void saveUserData(ActiveUserModel activeUserModel) {
        Constants.ID = String.valueOf(activeUserModel.getData().getId());
        Constants.USER_ID = activeUserModel.getData().getUserId();
        Constants.USER_NAME = activeUserModel.getData().getName();
        Constants.USER_EMAIL = activeUserModel.getData().getEmail();
        Constants.USER_AVATAR = activeUserModel.getData().getAvatar();
        Constants.NUMBER = activeUserModel.getData().getPhone();
        if (activeUserModel.getData().getEarned_coins() != null){
            Constants.EARNED_COINS = activeUserModel.getData().getEarned_coins();
        }

        setProfileData();

    }


}