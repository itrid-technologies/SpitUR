package split.com.app.ui.main.view.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;


public class Profile extends Fragment {

    FragmentProfileBinding binding;
    private AvatarViewModel mViewModel;
    private List<AvatarItem> avatarList = new ArrayList<>();
    private final List<String> avatars = new ArrayList<>();


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
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
            bottomSheetDialog.setContentView(R.layout.fragment_user_information);

          //  EditText name = item.findViewById(R.id.ed_name);



//            mViewModel.init();
//            mViewModel.getData().observe(getViewLifecycleOwner(), avatarModel -> {
//                avatarList.addAll(avatarModel.getAvatar());
//                for (int i=0; i <= avatarList.size()-1; i++){
//                    avatars.add(avatarList.get(i).getUrl());
//                }
//                buildAvatarsRV();
//            });

            bottomSheetDialog.show();

//            MySharedPreferences pm = new MySharedPreferences(Split.getAppContext());
//            binding.userProfile.edId.setText(pm.getData(Split.getAppContext(),"userId"));
//            binding.userProfile.edName.setText(pm.getData(Split.getAppContext(),"userName"));
//            Glide.with(Split.getAppContext()).load(pm.getData(Split.getAppContext(),"userAvatar")).placeholder(R.drawable.user).into(binding.userImage);
        });
    }

    private void buildAvatarsRV() {
        binding.userProfile.rvAvatars.setHasFixedSize(true);
        binding.userProfile.rvAvatars.setHorizontalScrollBarEnabled(false);
        binding.userProfile.rvAvatars.setLayoutManager(
                new LinearLayoutManager(Split.getAppContext(), RecyclerView.HORIZONTAL, false)
        );

        RecyclerView.OnItemTouchListener disabler = new RecyclerViewDisabler();
        binding.userProfile.rvAvatars.addOnItemTouchListener(disabler);// scrolling disable

        binding.userProfile.rvAvatars.setAdapter(new AdapterAvatars(getActivity(),avatars));
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