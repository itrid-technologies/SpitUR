package split.com.app.ui.main.view.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import split.com.app.R;
import split.com.app.data.model.get_avatar.AvatarItem;
import split.com.app.databinding.FragmentProfileBinding;
import split.com.app.ui.main.adapter.avatar_adapter.AdapterAvatars;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.viewmodel.avatar_viewmodel.AvatarViewModel;
import split.com.app.ui.main.viewmodel.profile_viewmodel.ProfileViewModel;
import split.com.app.utils.Constants;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;


public class Profile extends Fragment {

    FragmentProfileBinding binding;
    private AvatarViewModel mViewModel;
    private List<AvatarItem> avatarList = new ArrayList<>();
    private final List<String> avatars = new ArrayList<>();
    private int currentIndex = 0;

    ProfileViewModel viewModel;


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

        setProfileData();

        initClickListeners();


        mViewModel = new AvatarViewModel();


    }

    private void setProfileData() {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String user_name = preferences.getData(Split.getAppContext(), "userName");
        String avatar = preferences.getData(Split.getAppContext(), "userAvatar");

        binding.name.setText(user_name);
        Glide.with(Split.getAppContext()).load(avatar).placeholder(R.drawable.user).into(binding.userImage);
    }

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
            Navigation.findNavController(view).navigate(R.id.action_profile2_to_contactUs);
        });

        binding.swap.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_profile2_to_swapCoins);
        });

        binding.swapIcon.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_profile2_to_swapCoins);
        });


        binding.userImage.setOnClickListener(view -> {
            final BottomSheetDialog bt = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
            View profileView = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_user_information, null, false);

            EditText name = profileView.findViewById(R.id.profile_name);
            EditText userid = profileView.findViewById(R.id.profile_id);
            RecyclerView avatarRv = profileView.findViewById(R.id.profile_avatars);
            ImageButton previous = profileView.findViewById(R.id.previous_avatar);
            ImageButton next = profileView.findViewById(R.id.next_avatar);
            Button save = profileView.findViewById(R.id.btn_save_profile);

            MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());

            name.setText(preferences.getData(Split.getAppContext(), "userName"));
            userid.setText(preferences.getData(Split.getAppContext(), "userId"));


            mViewModel.init();
            mViewModel.getData().observe(getViewLifecycleOwner(), avatarModel -> {
                avatarList.addAll(avatarModel.getAvatar());
                for (int i = 0; i <= avatarList.size() - 1; i++) {
                    avatars.add(avatarList.get(i).getUrl());
                }
                avatarRv.setHasFixedSize(true);
                avatarRv.setHorizontalScrollBarEnabled(false);
                avatarRv.setLayoutManager(new LinearLayoutManager(Split.getAppContext(), RecyclerView.HORIZONTAL, false));
                RecyclerView.OnItemTouchListener disabler = new RecyclerViewDisabler();
                avatarRv.addOnItemTouchListener(disabler);// scrolling disable
                avatarRv.setAdapter(new AdapterAvatars(getActivity(), avatars));
            });

            next.setOnClickListener(view1 -> {
                if (currentIndex < avatars.size() - 1) {//in range
                    currentIndex++;
                    avatarRv.smoothScrollToPosition(currentIndex);
                }
            });
            previous.setOnClickListener(view1 -> {
                if (currentIndex > 0) {
                    currentIndex--;
                    avatarRv.smoothScrollToPosition(currentIndex);
                }
            });

            save.setOnClickListener(view1 -> {
                String updated_name = name.getText().toString().trim();
                String updated_id = userid.getText().toString().trim();
                final String updatedAvatar = avatars.get(currentIndex);

                viewModel = new ProfileViewModel(updated_name, updated_id, updatedAvatar);
                viewModel.init();
                viewModel.getUpdate_profile().observe(getViewLifecycleOwner(), userUpdateModel -> {
                    if (userUpdateModel.isSuccess()) {
                        if (userUpdateModel.getData() != null){
                            MySharedPreferences sharedPreferences = new MySharedPreferences(Split.getAppContext());
                            sharedPreferences.saveData(Split.getAppContext(), "userAvatar",  Constants.IMG_PATH +userUpdateModel.getData().getAvatar());
                            sharedPreferences.saveData(Split.getAppContext(), "userName", userUpdateModel.getData().getName());
                            sharedPreferences.saveData(Split.getAppContext(), "userId", userUpdateModel.getData().getUserId());

                            Toast.makeText(Split.getAppContext(), userUpdateModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            });

            bt.setContentView(profileView);
            bt.show();
        });
    }


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