package split.com.app.ui.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import split.com.app.R;
import split.com.app.databinding.ActivityDashboardBinding;

public class Dashboard extends AppCompatActivity {

    public static ActivityDashboardBinding binding;
    NavController mNavController;


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

        binding.bottomNavigation.setOnItemSelectedListener(i -> {
            switch (i.getItemId()) {
                case R.id.home2:
                    i.getIcon().setTint(R.color.white);
                    mNavController.navigate(R.id.home2, null);
                    break;

                case R.id.search2:
                    i.getIcon().setTint(R.color.white);
                    mNavController.navigate(R.id.search2, null);
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