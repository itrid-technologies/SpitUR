package split.com.app.ui.main.view.create_visibility;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import split.com.app.R;
import split.com.app.databinding.FragmentVisibilityBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.utils.Constants;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;


public class Visibility extends Fragment {


    FragmentVisibilityBinding binding;
    boolean visibility = true;
    int visible = 1;
    String slot;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVisibilityBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clickListeners();

        setProfileData();

    }

    private void setProfileData() {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String user_name = preferences.getData(Split.getAppContext(), "userName");
        String user_ID = preferences.getData(Split.getAppContext(), "userId");
        String avatar = preferences.getData(Split.getAppContext(), "userAvatar");
        slot = preferences.getData(Split.getAppContext(), "SLOTS");

        binding.visibilityProfile.netflix.setText(user_name);
        binding.visibilityProfile.userName.setText(user_ID);
        binding.visibilityProfile.count.setText(slot + " Slots");
        Glide.with(Split.getAppContext()).load(avatar).into(binding.visibilityProfile.userImage);
    }

    private void clickListeners() {

        binding.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.privateLayout.setOnClickListener(view -> {

            visibility = false;
            visible = 0;

            binding.publicLayout.setBackgroundResource(R.drawable.only_grey_stroke);
            binding.publicSelected.setVisibility(View.GONE);

            binding.privateLayout.setBackgroundResource(R.drawable.selected_gradient_stroke);
            binding.privateSelected.setVisibility(View.VISIBLE);
        });

        binding.publicLayout.setOnClickListener(view -> {

            visibility = true;
            visible = 1;


            binding.privateLayout.setBackgroundResource(R.drawable.only_grey_stroke);
            binding.privateSelected.setVisibility(View.GONE);

            binding.publicLayout.setBackgroundResource(R.drawable.selected_gradient_stroke);
            binding.publicSelected.setVisibility(View.VISIBLE);
        });

        binding.btnNext.setOnClickListener(view -> {

           // MySharedPreferences pm = new MySharedPreferences(Split.getAppContext());
            if (!slot.isEmpty()){
                Constants.SLOTS = slot;

            }else {
                Constants.SLOTS = "4";
            }
//            pm.saveBooleanData(Split.getAppContext(), "VISIBILITY", visibility);
            Constants.VISIBILITY_string = String.valueOf(visible);
            Constants.VISIBILITY = visibility;

            Navigation.findNavController(view).navigate(R.id.action_visibility2_to_cost2);
        });
    }
}