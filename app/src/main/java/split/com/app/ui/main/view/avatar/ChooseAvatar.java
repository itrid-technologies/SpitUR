package split.com.app.ui.main.view.avatar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import split.com.app.ui.main.adapter.avatar_adapter.AdapterAvatars;
import split.com.app.R;
import split.com.app.data.model.get_avatar.AvatarItem;
import split.com.app.data.model.get_avatar.AvatarModel;
import split.com.app.databinding.ActivityChooseAvatarBinding;
import split.com.app.ui.main.view.terms_conditions.TermsAndConditions;
import split.com.app.ui.main.viewmodel.avatar_viewmodel.AvatarViewModel;
import split.com.app.utils.ActivityUtil;
import split.com.app.utils.Constants;
import split.com.app.utils.MySharedPreferences;

public class ChooseAvatar extends AppCompatActivity {

    ActivityChooseAvatarBinding binding;
    private final List<String> avatars = new ArrayList<>();
    private int currentIndex = 0;

    private AvatarViewModel mViewModel;
    private List<AvatarItem> avatarList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseAvatarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        });



        mViewModel = new AvatarViewModel();
        mViewModel.init();
        mViewModel.getData().observe(this, avatarModel -> {
            avatarList.addAll(avatarModel.getAvatar());
            for (int i=0; i <= avatarList.size()-1; i++){
                avatars.add(avatarList.get(i).getUrl());
            }
            buildAvatarsRV();
        });

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

        binding.rvAvatars.setAdapter(new AdapterAvatars(ChooseAvatar.this,avatars));
    }



    private void initClickListeners() {
        binding.next.setOnClickListener(view -> {
            if (currentIndex < avatars.size() - 1) {//in range
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
            final String selectedAvatar = avatars.get(currentIndex);

           Constants.USER_AVATAR = Constants.IMG_PATH + selectedAvatar;

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