package split.com.app.ui.main.view.dashboard;

import static split.com.app.utils.Configration.OTP_NOTIFICATION;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.R;
import split.com.app.data.api.ApiManager;
import split.com.app.databinding.ActivityDashboardBinding;
import split.com.app.ui.main.viewmodel.UserOnlineStatusViewModel;
import split.com.app.utils.Configration;
import split.com.app.utils.MyReceiver;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;

public class Dashboard extends AppCompatActivity {

    private static final String TAG = "Dashboard";
    public static ActivityDashboardBinding binding;
    NavController mNavController;

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    private BroadcastReceiver receiver = null;

    private BroadcastReceiver mMessageReceiver;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding.bottomNavigation.addTab(binding.bottomNavigation.newTab().setText("").setIcon(R.drawable.ic_home).setId(0));
        binding.bottomNavigation.addTab(binding.bottomNavigation.newTab().setText("").setIcon(R.drawable.ic_add_circle).setId(1));
        binding.bottomNavigation.addTab(binding.bottomNavigation.newTab().setText("").setIcon(R.drawable.ic_group).setId(2));
        binding.bottomNavigation.addTab(binding.bottomNavigation.newTab().setText("").setIcon(R.drawable.ic_profile).setId(3));
        binding.bottomNavigation.setTabGravity(TabLayout.GRAVITY_FILL);

        binding.bottomNavigation.getTabAt(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);


        //update user last active status
        updateLastActiveStatus();

        receiver = new MyReceiver();
        broadcastIntent();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            mNavController = navHostFragment.getNavController();
        }
//
//        binding.bottomNavigation.setItemIconTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));


        binding.bottomNavigation.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

                switch (tab.getId()) {
                    case 0:
                        mNavController.navigate(R.id.home2, null);
                        break;

                    case 1:
                        // i.getIcon().setTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
                        mNavController.navigate(R.id.search2, null);
                        break;
                    case 2:
                        //  i.setIconTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));

                        mNavController.navigate(R.id.createdAndJoinedGroups, null);
                        break;

                    case 3:
                        //  i.setIconTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
                        mNavController.navigate(R.id.profile2, null);
                        break;
                    default:
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(OTP_NOTIFICATION)) {
                    // gcm successfully registered
                    showDialogue();
                }
            }
        };

        Intent data = getIntent();
        if (data.hasExtra("checkout_complete")) {
            //group data
            Bundle bundle = new Bundle();
            bundle.putString("group_credentials", data.getStringExtra("group_credentials"));
            mNavController.navigate(R.id.joinCheckoutComplete, bundle);
        }

    }


    public void broadcastIntent() {
        registerReceiver(receiver, new IntentFilter(Configration.PUSH_NOTIFICATION));
    }

    private void showDialogue() {
        dialogBuilder = new AlertDialog.Builder(Dashboard.this);
        dialogBuilder.setCancelable(false);
        View layoutView = getLayoutInflater().inflate(R.layout.otp_request_dialogue, null);
        ImageView close = (ImageView) layoutView.findViewById(R.id.close_dialogue);


        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimations;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        close.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });
    }

    public static void hideNav(boolean hideStatus) {
        if (hideStatus) {
            binding.bottomNavBar.setVisibility(View.GONE);
        } else {
            binding.bottomNavBar.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(Dashboard.this);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(OTP_NOTIFICATION));
    }

    private void updateLastActiveStatus() {
        MySharedPreferences pm = new MySharedPreferences(Split.getAppContext());
        final int userId = Integer.parseInt(pm.getData(Split.getAppContext(), "Id"));
        Call<JsonObject> call = ApiManager.getRestApiService().updateLastActive(userId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        final boolean status = response.body().get("status").getAsBoolean();
                        if (status) {
                            Log.e(TAG, "onResponse: Last Active Updated");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }//onPause

    @Override
    protected void onDestroy() {
        super.onDestroy();

        UserOnlineStatusViewModel userOnlineStatusViewModel = new UserOnlineStatusViewModel(0);
        userOnlineStatusViewModel.init();
        userOnlineStatusViewModel.getData().observe(this, basicModel -> {
            if (basicModel.isStatus().equals("true")){
                Log.e("user online/offline " , basicModel.getMessage());
            }
        });
    }
}