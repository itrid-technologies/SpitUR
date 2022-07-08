package split.com.app.ui.main.view.dashboard;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import split.com.app.R;
import split.com.app.databinding.ActivityDashboardBinding;
import split.com.app.utils.Configration;
import split.com.app.utils.MyReceiver;

public class Dashboard extends AppCompatActivity {

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

        receiver = new MyReceiver();
        broadcastIntent();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            mNavController = navHostFragment.getNavController();
        }
//
//        binding.bottomNavigation.setItemIconTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));


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


//        mMessageReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                // Extract data included in the Intent
//                String type = intent.getStringExtra("type");
//                if (type.equalsIgnoreCase("otp_request")){
//                    showDialogue();
//                }
//            }
//        };

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
    }

//    @Override
//    protected void onPause() {
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
//        super.onPause();
//    }//onPause
}