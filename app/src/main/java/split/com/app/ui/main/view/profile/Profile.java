package split.com.app.ui.main.view.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import split.com.app.R;
import split.com.app.data.model.get_avatar.AvatarItem;
import split.com.app.databinding.FragmentProfileBinding;
import split.com.app.ui.main.adapter.avatar_adapter.AdapterAvatars;
import split.com.app.ui.main.view.avatar.ChooseAvatar;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.viewmodel.avatar_viewmodel.AvatarViewModel;


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

        initClickListeners();


        mViewModel = new AvatarViewModel();
        mViewModel.init();
        mViewModel.getData().observe(getActivity(), avatarModel -> {
            avatarList.addAll(avatarModel.getAvatar());
            for (int i=0; i <= avatarList.size()-1; i++){
                avatars.add(avatarList.get(i).getUrl());
            }
            buildAvatarsRV();
        });

    }

    private void initClickListeners() {
        binding.refund.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_profile2_to_refund2);
        });

        binding.refundIcon.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_profile2_to_refund2);
        });

        binding.userImage.setOnClickListener(view -> {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
            bottomSheetDialog.setContentView(R.layout.fragment_user_information);

            bottomSheetDialog.show();
        });
    }

    private void buildAvatarsRV() {
        binding.userProfile.rvAvatars.setHasFixedSize(true);
        binding.userProfile.rvAvatars.setHorizontalScrollBarEnabled(false);
        binding.userProfile.rvAvatars.setLayoutManager(
                new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        );

        RecyclerView.OnItemTouchListener disabler = new ChooseAvatar.RecyclerViewDisabler();
        binding.userProfile.rvAvatars.addOnItemTouchListener(disabler);// scrolling disable

        binding.userProfile.rvAvatars.setAdapter(new AdapterAvatars(getActivity(),avatars));
    }

}