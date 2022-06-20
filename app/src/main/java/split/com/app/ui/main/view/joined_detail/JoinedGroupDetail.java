package split.com.app.ui.main.view.joined_detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import dagger.android.DaggerActivity;
import split.com.app.R;
import split.com.app.data.model.all_joined_groups.DataItem;
import split.com.app.databinding.FragmentJoinedGroupDetailBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.utils.Constants;
import split.com.app.utils.Split;


public class JoinedGroupDetail extends Fragment {

    FragmentJoinedGroupDetailBinding binding;
    split.com.app.data.model.all_joined_groups.DataItem data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJoinedGroupDetailBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        try {
            if (getArguments() != null){
                Gson gson = new Gson();
                String groupData = getArguments().getString("jointedGroupData");
                data = gson.fromJson(groupData, split.com.app.data.model.all_joined_groups.DataItem.class);

                setProfileData(data);
            }
        }catch (IllegalStateException e){
            Log.e("join_group_detail",e.getMessage());
        }

        initClickListeners();
    }

    private void setProfileData(DataItem data) {

        binding.joinedProfile.netflix.setText(data.getGroup().getGroupTitle());
        Glide.with(Split.getAppContext())
                .load(Constants.IMG_PATH + data.getGroup().getGroupAdmin().getAvatar())
                .placeholder(R.drawable.user)
                .into(binding.joinedProfile.userImage);

        binding.joinedProfile.userName.setText(data.getGroup().getGroupAdmin().getUserId());
        String coin = String.valueOf(data.getGroup().getCostPerMember());
        Double coinFloat = Double.parseDouble(coin);
        String value = String.valueOf(((coinFloat * 30) / 100) + coinFloat);
        binding.joinedProfile.count.setText(value + " Coins");

        binding.tvScoreValue.setText(String.valueOf(data.getGroup().getGroupAdmin().getSpliturScore()));

        binding.tvLastActive.setText(Constants.getDate(data.getGroup().getGroupAdmin().getLastActive()));
    }

    private void initClickListeners() {

        binding.jgdToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

    }
}