package com.splitur.app.ui.main.view.join_plans;

import android.app.Activity;
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

import org.json.JSONArray;
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





        final Intent data = getIntent();
        String subID;
        Checkout.preload(Split.getAppContext());


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
        final Activity activity = this;


        try {


            JSONArray prefAppsJArray = new JSONArray();
            prefAppsJArray.put("com.phonepe.app");

            JSONArray otherAppsJArray = new JSONArray();
            otherAppsJArray.put("net.one97.paytm");

            JSONObject options = new JSONObject();
            options.put("subscription_id", subscriptionId);
            options.put("recurring", 1);
            options.put("prefill.email", Constants.USER_EMAIL);
            options.put("prefill.contact",Constants.NUMBER);
            options.put("method", "upi");
            options.put("_[flow]", "intent");
            options.put("upi_app_package_name", "com.phonepe.app");


            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }

//        // initialize json object
//        JSONObject object = new JSONObject();
//        try {
//            object.put("subscription_id", subscriptionId);
//            object.put("recurring", 1);
////            object.put("method", "UPI");  //Method specific fields
////            object.put("_[flow]", "intent");
////            object.put("upi_app_package_name", "net.one97.paytm");
//            object.put("prefill.email", Constants.USER_EMAIL);
//            object.put("prefill.contact",Constants.NUMBER);
//            JSONObject retryObj = new JSONObject();
//            retryObj.put("enabled", true);
//            retryObj.put("max_count", 4);
//            object.put("retry", retryObj);
//
//            // open razorpay to checkout activity
//            checkout.open(CheckoutActivity.this, object);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
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