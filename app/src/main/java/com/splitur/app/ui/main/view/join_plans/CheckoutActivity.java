package com.splitur.app.ui.main.view.join_plans;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.gson.JsonObject;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import com.splitur.app.R;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity implements PaymentResultListener {

    private String group_id;
    private String groupData;
    String secret_key = "";
    private String groupAdminId = "";
//    Razorpay razorpay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Checkout.preload(CheckoutActivity.this);



        final Intent data = getIntent();
        String subID;


        if (data.hasExtra("subscription_id")) {
            subID = data.getStringExtra("subscription_id");
            group_id = data.getStringExtra("group_id");
            groupData = data.getStringExtra("group_credentials");
            secret_key = data.getStringExtra("secret_key");
            groupAdminId = data.getStringExtra("group_admin_id");
            if (!secret_key.isEmpty()) {
                checkout(subID);
            }
        }





    }

    private void checkout(String subscriptionId) {
        // initialize Razorpay account.
        Checkout checkout = new Checkout();

        // set your id as below
//        checkout.setKeyID("rzp_test_Z5X8uEVBddGyA5");
        checkout.setKeyID(secret_key);
      // razorpay = new Razorpay(CheckoutActivity.this, secret_key);


        // initialize json object
        JSONObject object = new JSONObject();
        try {
            object.put("subscription_id", subscriptionId);
            object.put("recurring", 1);
//            object.put("method", "UPI");  //Method specific fields
            object.put("_[flow]", "intent");
            object.put("upi_app_package_name", "net.one97.paytm");
            object.put("prefill.email", Constants.USER_EMAIL);
            object.put("prefill.contact",Constants.NUMBER);

            // open razorpay to checkout activity
            checkout.open(CheckoutActivity.this, object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");
        String userId = preferences.getData(Split.getAppContext(), "Id");

        JsonObject object = new JsonObject();
        object.addProperty("payment_id", s);
        object.addProperty("group_id", group_id);
        object.addProperty("user_id", userId);

        Call<JsonObject> call = ApiManager.getRestApiService().sendPaymentSuccess(object);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful() & response.body() != null) {
                    try {
                        final boolean success = response.body().get("success").getAsBoolean();
                        if (success) {
                            Toast.makeText(CheckoutActivity.this, "Payment Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CheckoutActivity.this, Dashboard.class);
                            intent.putExtra("checkout_complete", true);
                            intent.putExtra("group_credentials", groupData);
                            intent.putExtra("group_admin_id", groupAdminId);
                            finish();
                            finishAffinity();
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        } else {
                            final String msg = response.body().get("message").getAsString();
                            Toast.makeText(CheckoutActivity.this, msg, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                finish();
                Log.e("APi Error", t.getMessage());
            }
        });

    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e("onPaymentError", s);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
//        if(razorpay!=null){
//            razorpay.onActivityResult(requestCode,resultCode,data);
//        }
    }
}