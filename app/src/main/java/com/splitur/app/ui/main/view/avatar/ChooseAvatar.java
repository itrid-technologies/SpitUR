package com.splitur.app.ui.main.view.avatar;

import android.os.Bundle;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.splitur.app.ui.main.adapter.avatar_adapter.AdapterAvatars;
import com.splitur.app.R;
import com.splitur.app.data.model.get_avatar.AvatarItem;
import com.splitur.app.databinding.ActivityChooseAvatarBinding;
import com.splitur.app.ui.main.view.terms_conditions.TermsAndConditions;
import com.splitur.app.ui.main.viewmodel.avatar_viewmodel.AvatarViewModel;
import com.splitur.app.utils.ActivityUtil;
import com.splitur.app.utils.Constants;

public class ChooseAvatar extends AppCompatActivity {

    ActivityChooseAvatarBinding binding;
    private final List<Integer> avatars1 = new ArrayList<>();

    private int currentIndex = 0;

    private AvatarViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseAvatarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());





        binding.back.setOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        });



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
        avatars1.add(21);
        buildAvatarsRV();


//        mViewModel = new AvatarViewModel();
//        mViewModel.init();
//        mViewModel.getData().observe(this, avatarModel -> {
//            avatarList.addAll(avatarModel.getAvatar());
//            for (int i=0; i <= avatarList.size()-1; i++){
//                avatars.add(avatarList.get(i).getUrl());
//            }
//            buildAvatarsRV();
//        });

        initClickListeners();


    }

    private void buildAvatarsRV() {
        binding.rvAvatars.setHasFixedSize(true);
        binding.rvAvatars.setHorizontalScrollBarEnabled(false);
        binding.rvAvatars.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        );

        RecyclerView.OnItemTouchListener disabler = new RecyclerViewDisabler();
        binding.rvAvatars.addOnItemTouchListener(disabler);// scrolling disable

        binding.rvAvatars.setAdapter(new AdapterAvatars(ChooseAvatar.this,avatars1));
    }



    private void initClickListeners() {
        binding.next.setOnClickListener(view -> {
            if (currentIndex < avatars1.size() - 1) {//in range
                currentIndex++;
                binding.rvAvatars.smoothScrollToPosition(currentIndex);
            }
        });
        binding.previous.setOnClickListener(view -> {
            if (currentIndex > 0) {
                currentIndex--;
                binding.rvAvatars.smoothScrollToPosition(currentIndex);
            }
        });
        binding.btnSet.setOnClickListener(view -> {
            //TODO: here is your selected avatar
            final Integer selectedAvatar = avatars1.get(currentIndex);

           Constants.USER_AVATAR = String.valueOf(selectedAvatar);

            ActivityUtil.gotoPage(ChooseAvatar.this, TermsAndConditions.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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