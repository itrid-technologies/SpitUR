package split.com.app.utils;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import split.com.app.R;
import split.com.app.ui.main.view.dashboard.Dashboard;

public class MyReceiver extends BroadcastReceiver {

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra("type");
        if (type.equalsIgnoreCase("otp_request")){
            showDialogue(context);
        }
    }

    private void showDialogue(Context context) {
        dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setCancelable(false);
        View layoutView = LayoutInflater.from(context).inflate(R.layout.otp_request_dialogue, null);

      //  View layoutView = getLayoutInflater().inflate(R.layout.otp_request_dialogue, null);
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

}
