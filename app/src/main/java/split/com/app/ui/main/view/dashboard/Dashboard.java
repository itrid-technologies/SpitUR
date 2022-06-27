package split.com.app.ui.main.view.dashboard;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import split.com.app.R;
import split.com.app.databinding.ActivityDashboardBinding;

public class Dashboard extends AppCompatActivity {

    public static ActivityDashboardBinding binding;
    NavController mNavController;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            mNavController = navHostFragment.getNavController();
        }

        binding.bottomNavigation.setItemIconTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));


        binding.bottomNavigation.setOnItemSelectedListener(i -> {
            switch (i.getItemId()) {
                case R.id.home2:
                   // i.getIcon().setTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
                    mNavController.navigate(R.id.home2, null);
                    break;

                case R.id.search2:
                   // i.getIcon().setTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
                    mNavController.navigate(R.id.search2, null);
                    break;
                case R.id.createdAndJoinedGroups:
                  //  i.setIconTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));

                    mNavController.navigate(R.id.createdAndJoinedGroups, null);
                    break;

                case R.id.profile2:
                  //  i.setIconTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
                    mNavController.navigate(R.id.profile2, null);
                    break;
                default:
            }

            return false;
        });
    }

    public static void hideNav(boolean hideStatus) {
        if (hideStatus) {
            binding.bottomNavBar.setVisibility(View.GONE);
        } else {
            binding.bottomNavBar.setVisibility(View.VISIBLE);

        }
    }

}