package com.splitur.app.ui.main.view.working_slider;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.splitur.app.R;
import com.splitur.app.databinding.ActivityGroupWorkingSliderBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.view.otp_verification.OtpVerification;
import com.splitur.app.utils.ActivityUtil;
import com.splitur.app.utils.Split;

import java.util.ArrayList;
import java.util.List;

public class GroupWorkingSlider extends AppCompatActivity {

    ActivityGroupWorkingSliderBinding binding;
    int currentIndex = 0;
    List<SliderData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupWorkingSliderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list = new ArrayList<>();

        buildSliderList();

        clickListeners();


    }

    private void clickListeners() {

        binding.nextImageButton.setOnClickListener(view -> {
            if (binding.nextImageButton.getText().toString().equalsIgnoreCase("Home")){
                ActivityUtil.gotoPage(GroupWorkingSlider.this, Dashboard.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }else {
                if (currentIndex < list.size() - 1) {
                    currentIndex++;
                    binding.sliderList.smoothScrollToPosition(currentIndex);
                }else {
                    binding.nextImageButton.setText("Home");
                }
            }

        });

        binding.previousImageButton.setOnClickListener(view -> {
            if (currentIndex > 0) {
                currentIndex--;
                binding.sliderList.smoothScrollToPosition(currentIndex);
                binding.nextImageButton.setText("Next");

            }
        });

    }

    private void buildSliderList() {

        List<Integer> joinGroup = new ArrayList<>();
        joinGroup.add(R.drawable.group_flow1);
        joinGroup.add(R.drawable.group_flow2);
        joinGroup.add(R.drawable.group_flow3);
        joinGroup.add(R.drawable.group_flow4);
        joinGroup.add(R.drawable.group_flow5);
        joinGroup.add(R.drawable.group_flow6);
        joinGroup.add(R.drawable.group_flow7);
        joinGroup.add(R.drawable.group_flow8);
        joinGroup.add(R.drawable.group_flow9);

        List<Integer> createGroup = new ArrayList<>();
        createGroup.add(R.drawable.group_flow10);
        createGroup.add(R.drawable.group_flow11);
        createGroup.add(R.drawable.group_flow12);
        createGroup.add(R.drawable.group_flow13);
        createGroup.add(R.drawable.group_flow14);
        createGroup.add(R.drawable.group_flow15);
        createGroup.add(R.drawable.group_flow16);


        list.add(new SliderData("How to Create?", createGroup));
        list.add(new SliderData("How to Join?", joinGroup));

        binding.sliderList.setLayoutManager(new LinearLayoutManager(Split.getAppContext(), RecyclerView.HORIZONTAL, false));
        binding.sliderList.hasFixedSize();
         SnapHelper snapHelper = new PagerSnapHelper();
         snapHelper.attachToRecyclerView(binding.sliderList);
        binding.sliderList.setAdapter(new GroupWorkingSliderAdapter(GroupWorkingSlider.this, list));


    }


}