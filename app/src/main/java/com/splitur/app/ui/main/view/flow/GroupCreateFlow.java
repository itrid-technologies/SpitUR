package com.splitur.app.ui.main.view.flow;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.splitur.app.R;
import com.splitur.app.databinding.FragmentGroupCreateFlowBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.view.working_slider.GroupWorkingSlider;
import com.splitur.app.ui.main.view.working_slider.SliderAdapter;
import com.splitur.app.utils.ActivityUtil;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

import java.util.ArrayList;
import java.util.List;

public class GroupCreateFlow extends Fragment {

   FragmentGroupCreateFlowBinding binding;
   int currentIndex = 0;
    List<Integer> createGroup;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGroupCreateFlowBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.createFlow.tvGroupCreateFlow.setText("How to Create");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createGroup = new ArrayList<>();
        createGroupSlider();
        clickListeners();
    }

    private void clickListeners() {
//        binding.createFlow.nextImageButton.setOnClickListener(view -> {
//            Intent intent = new Intent(requireContext(),Dashboard.class);
//            startActivity(intent);
//            requireActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//
//
////            if (binding.createFlow.nextImageButton.getText().toString().equalsIgnoreCase("Home")){
////                Navigation.findNavController(view).navigateUp();
////            }else {
////                if (currentIndex < createGroup.size() - 1) {
////                    currentIndex++;
////                    binding.createFlow.imageSlider.setCurrentPagePosition(currentIndex);
////                }else {
////                    binding.createFlow.nextImageButton.setText("Home");
////                }
////            }
//
//        });

//        binding.createFlow.previousImageButton.setOnClickListener(view -> {
//            if (currentIndex > 0) {
//                currentIndex--;
//                binding.createFlow.imageSlider.setCurrentPagePosition(currentIndex);
//                binding.createFlow.nextImageButton.setText("Next");
//
//            }
//        });
    }

    private void createGroupSlider() {


        createGroup.add(R.drawable.group_flow10);
        createGroup.add(R.drawable.group_flow11);
        createGroup.add(R.drawable.group_flow12);
        createGroup.add(R.drawable.group_flow13);
        createGroup.add(R.drawable.group_flow14);
        createGroup.add(R.drawable.group_flow15);
        createGroup.add(R.drawable.group_flow16);

        SliderAdapter sliderAdapter = new SliderAdapter(Split.getAppContext(), createGroup);
        binding.createFlow.imageSlider.setSliderAdapter(sliderAdapter);
//        binding.createFlow.imageSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        binding.createFlow.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        binding.createFlow.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
//        binding.createFlow.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        binding.createFlow.imageSlider.setAutoCycle(false);


        binding.createFlow.nextImageButton.setOnClickListener(v -> {
            final int pos = binding.createFlow.imageSlider.getCurrentPagePosition();
            if (pos == 5) {
                binding.createFlow.nextImageButton.setText("Home");
            }

            if (pos == 6) {
                MySharedPreferences mySharedPreferences = new MySharedPreferences(requireContext());
                mySharedPreferences.saveBooleanData(requireContext() , "isNewUser_Create" , true);
                Intent intent = new Intent(requireContext(), Dashboard.class);
                startActivity(intent);
                requireActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            } else {
                binding.createFlow.imageSlider.slideToNextPosition();
            }

        });

        binding.createFlow.previousImageButton.setOnClickListener(v -> {
            final int page = binding.createFlow.imageSlider.getCurrentPagePosition();
            if (page != 5) {
                binding.createFlow.nextImageButton.setText("Next");
            }
            if (page > 0) {
                binding.createFlow.imageSlider.setCurrentPagePosition(
                        binding.createFlow.imageSlider.getCurrentPagePosition() - 1
                );
            }
        });
        
    }
}