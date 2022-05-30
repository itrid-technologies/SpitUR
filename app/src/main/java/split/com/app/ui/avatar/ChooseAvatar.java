package split.com.app.ui.avatar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import split.com.app.AdapterAvatars;
import split.com.app.R;
import split.com.app.databinding.ActivityChooseAvatarBinding;
import split.com.app.ui.terms_conditions.TermsAndConditions;
import split.com.app.ui.user_id.UserId;

public class ChooseAvatar extends AppCompatActivity {

    ActivityChooseAvatarBinding binding;
    private final List<Integer> avatars = new ArrayList<>();
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseAvatarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        });

        buildAvatarsRV();
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

        populateAvatars();
        binding.rvAvatars.setAdapter(new AdapterAvatars(avatars));
    }

    private void populateAvatars() {
        avatars.add(R.drawable.artist);
        avatars.add(R.drawable.asia_women);
        avatars.add(R.drawable.astronaut);
        avatars.add(R.drawable.baby);
        avatars.add(R.drawable.black_man);
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
            final int selectedAvatar = avatars.get(currentIndex);

            startActivity(new Intent(ChooseAvatar.this, TermsAndConditions.class));
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