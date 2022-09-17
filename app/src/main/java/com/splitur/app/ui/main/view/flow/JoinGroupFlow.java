package com.splitur.app.ui.main.view.flow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.splitur.app.R;
import com.splitur.app.databinding.FragmentJoinGroupFlowBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.view.working_slider.SliderAdapter;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

import java.util.ArrayList;
import java.util.List;


public class JoinGroupFlow extends Fragment {

    FragmentJoinGroupFlowBinding binding;
    int currentIndex = 0;
    List<Integer> joinGroup;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJoinGroupFlowBinding.inflate(inflater, container, false);

        Dashboard.hideNav(true);
        binding.JoinFlow.tvGroupCreateFlow.setText("How to Join");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        joinGroup = new ArrayList<>();
        joinGroupSlider();
        clickListeners();
    }

    private void clickListeners() {
//        binding.JoinFlow.nextImageButton.setOnClickListener(view -> {
//            Intent intent = new Intent(requireContext(), Dashboard.class);
//            startActivity(intent);
//            requireActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//
////            if (binding.JoinFlow.nextImageButton.getText().toString().equalsIgnoreCase("Home")) {
////                Navigation.findNavController(view).navigateUp();
////            } else {
////                if (currentIndex < joinGroup.size() - 1) {
////                    currentIndex++;
////                    binding.JoinFlow.imageSlider.setCurrentPagePosition(currentIndex);
////                } else {
////                    binding.JoinFlow.nextImageButton.setText("Home");
////                }
////            }
//
//        });

//        binding.JoinFlow.previousImageButton.setOnClickListener(view -> {
//            if (currentIndex > 0) {
//                currentIndex--;
//                binding.JoinFlow.imageSlider.setCurrentPagePosition(currentIndex);
//                binding.JoinFlow.nextImageButton.setText("Next");
//
//            }
//        });
    }

    private void joinGroupSlider() {


        joinGroup.add(R.drawable.group_flow1);
        joinGroup.add(R.drawable.group_flow2);
        joinGroup.add(R.drawable.group_flow3);
        joinGroup.add(R.drawable.group_flow4);
        joinGroup.add(R.drawable.group_flow5);
        joinGroup.add(R.drawable.group_flow6);
        joinGroup.add(R.drawable.group_flow7);
        joinGroup.add(R.drawable.group_flow8);
        joinGroup.add(R.drawable.group_flow9);

        SliderAdapter sliderAdapter = new SliderAdapter(Split.getAppContext(), joinGroup);
        binding.JoinFlow.imageSlider.setSliderAdapter(sliderAdapter);
//        binding.JoinFlow.imageSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        binding.JoinFlow.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        binding.JoinFlow.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
//        binding.JoinFlow.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        binding.JoinFlow.imageSlider.setAutoCycle(false);


        binding.JoinFlow.nextImageButton.setOnClickListener(v -> {
            final int pos = binding.JoinFlow.imageSlider.getCurrentPagePosition();
            if (pos == 7) {
                binding.JoinFlow.nextImageButton.setText("Home");
            }

            if (pos == 8) {
                MySharedPreferences mySharedPreferences = new MySharedPreferences(requireContext());
                mySharedPreferences.saveBooleanData(requireContext() , "isNewUser_Join" , true);
                Intent intent = new Intent(requireContext(), Dashboard.class);
                startActivity(intent);
                requireActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            } else {
                binding.JoinFlow.imageSlider.slideToNextPosition();
            }

        });

        binding.JoinFlow.previousImageButton.setOnClickListener(v -> {
            final int page = binding.JoinFlow.imageSlider.getCurrentPagePosition();
            if (page != 7) {
                binding.JoinFlow.nextImageButton.setText("Next");
            }
            if (page > 0) {
                binding.JoinFlow.imageSlider.setCurrentPagePosition(
                        binding.JoinFlow.imageSlider.getCurrentPagePosition() - 1
                );
            }
        });

    }
}