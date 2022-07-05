package split.com.app.ui.main.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.skydoves.elasticviews.ElasticImageView;

import java.util.ArrayList;
import java.util.List;

import split.com.app.R;
import split.com.app.data.model.HomeDataItem;
import split.com.app.data.model.get_avatar.AvatarItem;
import split.com.app.data.model.home_categories.CategoryDataItems;
import split.com.app.data.model.popular_subcategory.DataItem;
import split.com.app.databinding.FragmentHomeBinding;
import split.com.app.ui.main.adapter.HomeSectionAdapter;
import split.com.app.ui.main.adapter.avatar_adapter.AdapterAvatars;
import split.com.app.ui.main.adapter.category_adapter.CategoryAdapter;
import split.com.app.ui.main.adapter.popular_adapter.PopularHomeAdapter;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.view.profile.Profile;
import split.com.app.ui.main.view.splash.Splash;
import split.com.app.ui.main.viewmodel.avatar_viewmodel.AvatarViewModel;
import split.com.app.ui.main.viewmodel.category_viewmodel.CategoryViewModel;
import split.com.app.ui.main.viewmodel.home_viewmodel.HomeViewModel;
import split.com.app.ui.main.viewmodel.profile_viewmodel.ProfileViewModel;
import split.com.app.utils.Constants;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;


public class Home extends Fragment {

    FragmentHomeBinding binding;

    private CategoryViewModel mViewModel;
    private List<HomeDataItem> homeDataItems;

    private HomeViewModel homeViewModel;
    private List<CategoryDataItems> category_list;

    private List<DataItem> popularSubCategoryList;

    ProfileViewModel profileViewModel;
    private AvatarViewModel avatarViewModel;
    private List<AvatarItem> avatarList = new ArrayList<>();
    private final List<String> avatars = new ArrayList<>();
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
        binding.popularLst.setVisibility(View.GONE);
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

        setProfileData();


        initClickListeners();
        getPopularList();
    }

    private void initClickListeners() {
        binding.search.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_home2_to_joinSearch);
        });

        binding.viewAll.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_home2_to_joinSearch);
        });

        binding.userImage.setOnClickListener(view -> {

            if (count == 0) {
                count = 1;

                //fetch user data
                profileViewModel = new ProfileViewModel("", "", "", "");
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

                            RecyclerView avatarRv = profileView.findViewById(R.id.profile_avatars);
                            ImageButton previous = profileView.findViewById(R.id.previous_avatar);
                            ImageButton next = profileView.findViewById(R.id.next_avatar);
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
                            Glide.with(Split.getAppContext()).load(Constants.IMG_PATH + activeUserModel.getData().getAvatar()).into(image);


                            MySharedPreferences sharedPreferences = new MySharedPreferences(Split.getAppContext());
                            sharedPreferences.saveData(Split.getAppContext(), "userAvatar", Constants.IMG_PATH + activeUserModel.getData().getAvatar());
                            sharedPreferences.saveData(Split.getAppContext(), "userName", activeUserModel.getData().getName());
                            sharedPreferences.saveData(Split.getAppContext(), "userId", activeUserModel.getData().getUserId());

                            avatarViewModel = new AvatarViewModel();
                            avatarViewModel.init();
                            avatarViewModel.getData().observe(getViewLifecycleOwner(), avatarModel -> {
                                avatarList.addAll(avatarModel.getAvatar());
                                for (int i = 0; i <= avatarList.size() - 1; i++) {
                                    avatars.add(avatarList.get(i).getUrl());
                                }
                                avatarRv.setHasFixedSize(true);
                                avatarRv.setHorizontalScrollBarEnabled(false);
                                avatarRv.setLayoutManager(new LinearLayoutManager(Split.getAppContext(), RecyclerView.HORIZONTAL, false));
                                RecyclerView.OnItemTouchListener disabler = new Profile.RecyclerViewDisabler();
                                avatarRv.addOnItemTouchListener(disabler);// scrolling disable
                                avatarRv.setAdapter(new AdapterAvatars(getActivity(), avatars));
                            });

                            next.setOnClickListener(view1 -> {
                                if (currentIndex < avatars.size() - 1) {//in range

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
                                final String updatedAvatar = avatars.get(currentIndex);

                                profileViewModel = new ProfileViewModel("", updated_name, updated_id, updatedAvatar);
                                profileViewModel.init();
                                profileViewModel.getUpdate_profile().observe(getViewLifecycleOwner(), userUpdateModel -> {
                                    if (userUpdateModel.isSuccess()) {
                                        if (userUpdateModel.getData() != null) {
                                            MySharedPreferences sharedPreferences1 = new MySharedPreferences(Split.getAppContext());
                                            sharedPreferences1.saveData(Split.getAppContext(), "userAvatar", Constants.IMG_PATH + userUpdateModel.getData().getAvatar());
                                            sharedPreferences1.saveData(Split.getAppContext(), "userName", userUpdateModel.getData().getName());
                                            sharedPreferences1.saveData(Split.getAppContext(), "userId", userUpdateModel.getData().getUserId());

                                            Toast.makeText(Split.getAppContext(), userUpdateModel.getMessage(), Toast.LENGTH_SHORT).show();
                                            bt.dismiss();
                                            count = 0;
                                        }
                                    }
                                });
                            });


                            logout.setOnClickListener(view1 -> {

                                final BottomSheetDialog dialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
                                View layout = LayoutInflater.from(requireContext()).inflate(R.layout.ask_to_remove_dialogue, null, false);
                                ConstraintLayout deleteLayout = layout.findViewById(R.id.delete_layout);
                                TextView remove = layout.findViewById(R.id.tv_remove);
                                TextView tagline = layout.findViewById(R.id.delete_detail);
                                TextView confirm = layout.findViewById(R.id.confirm_tagline);
                                ImageView confirm_logout = layout.findViewById(R.id.confirm_remove);

                                remove.setText("Log Out");
                                tagline.setText("You can login back again using your Phone number and OTP");
                                confirm.setText("You want to logout");


                                confirm_logout.setOnClickListener(view2 -> {

                                    profileViewModel = new ProfileViewModel(String.valueOf(activeUserModel.getData().getId()), "", "", "");
                                    profileViewModel.initLogout();
                                    profileViewModel.getLogout().observe(getViewLifecycleOwner(), basicModel -> {
                                        if (basicModel.isStatus().equalsIgnoreCase("true")) {
                                            Intent intent = new Intent(requireContext(), Splash.class);
                                            startActivity(intent);

                                        }
                                    });
                                });

                            });


                            bt.setContentView(profileView);
                            bt.show();

//                            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) profileView.getParent()).getLayoutParams();
//                            CoordinatorLayout.Behavior behavior = params.getBehavior();
//
//                            if(behavior instanceof BottomSheetBehavior) {
//                                ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
//                            }
                        }
                    }

                });

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.HORIZONTAL, false);
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
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String user_name = preferences.getData(Split.getAppContext(), "userName");
        String avatar = preferences.getData(Split.getAppContext(), "userAvatar");

        binding.name.setText(user_name);
        Glide.with(Split.getAppContext()).load(avatar).placeholder(R.color.blue).into(binding.userImage);


    }

    private void getMoviesList() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.homeSections.setLayoutManager(layoutManager);
        HomeSectionAdapter adapter = new HomeSectionAdapter(Split.getAppContext(), homeDataItems, requireView());
        binding.homeSections.setAdapter(adapter);
    }


    private void getPopularList() {

        popularSubCategoryList = new ArrayList<>();

        homeViewModel = new HomeViewModel();
        homeViewModel.initPopular();
        homeViewModel.getPopularCategoryData().observe(getViewLifecycleOwner(), popularSubCategoryModel -> {
            if (popularSubCategoryModel.isSuccess()) {
                if (popularSubCategoryModel.getData().size() > 0) {
                    popularSubCategoryList.addAll(popularSubCategoryModel.getData());
                }

                buildPopularCatRv();
            }
        });

//        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.HORIZONTAL, false);
//        binding.popularLst.setLayoutManager(layoutManager);
//        PopularHomeAdapter adapter = new PopularHomeAdapter(Split.getAppContext(), images, names);
//        binding.popularLst.setAdapter(adapter);
    }

    private void buildPopularCatRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.HORIZONTAL, false);
        binding.popularLst.setLayoutManager(layoutManager);
        PopularHomeAdapter adapter = new PopularHomeAdapter(Split.getAppContext(), popularSubCategoryList);
        binding.popularLst.setAdapter(adapter);

        binding.categoriesLst.setVisibility(View.VISIBLE);
        binding.popularLst.setVisibility(View.VISIBLE);
        binding.homeSections.setVisibility(View.VISIBLE);
        binding.loadingView.setVisibility(View.GONE);

    }
}