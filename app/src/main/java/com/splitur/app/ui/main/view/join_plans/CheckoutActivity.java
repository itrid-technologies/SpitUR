package com.splitur.app.ui.main.view.join_plans;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.gson.JsonObject;
import com.razorpay.BaseRazorpay;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.razorpay.Razorpay;
import com.razorpay.ValidationListener;
import com.splitur.app.R;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.RazorPayed;
import com.splitur.app.utils.Split;

import java.util.List;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity implements PaymentResultListener {

    private String group_id;
    private String groupData;
    String secret_key = "";
    private String groupAdminId = "";
    Razorpay razorpay;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        webView = findViewById(R.id.checkOutWebview);

        razorpay = new Razorpay(CheckoutActivity.this);

        razorpay.setWebView(webView);



        final Intent data = getIntent();
        String subID;
//        Checkout.preload(Split.getAppContext());

        BaseRazorpay.getAppsWhichSupportUpi(this, list -> {
            // List of upi supported app
            Log.e("UPI APPS " , list.toString());
        });



        if (data.hasExtra("subscription_id")) {
            subID = "sub_K6YUA9SxuNPXUs";
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
      //  Checkout checkout = new Checkout();
      //  razorpay = new RazorPayed(secret_key,webView,CheckoutActivity.this);

        // set your id as below
//        checkout.setKeyID("rzp_test_Z5X8uEVBddGyA5");
        razorpay.changeApiKey(secret_key);
      // razorpay = new Razorpay(CheckoutActivity.this, secret_key);
        final Activity activity = this;


        try {


            JSONArray prefAppsJArray = new JSONArray();
            prefAppsJArray.put("net.one97.paytm");
            JSONArray otherAppsJArray = new JSONArray();
            otherAppsJArray.put("com.phonepe.app");
            JSONObject options = new JSONObject();

            options.put("subscription_id", subscriptionId);
            options.put("amount", 29935);
            options.put("currency" , "INR");
            options.put("recurring", 1);
            options.put("contact", Constants.NUMBER);
            options.put("email", Constants.USER_EMAIL);
            options.put("method", "upi");
            options.put("_[flow]", "intent");
            /// options.put("upi_app_package_name", "com.phonepe.app");
            options.put("preferred_apps_order", prefAppsJArray);
            options.put("other_apps_order", otherAppsJArray);
            sendRequest(options);


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

    private void sendRequest(JSONObject options) {
        razorpay.validateFields(options, new ValidationListener() {
            @Override
            public void onValidationSuccess() {
                try {
                    webView.setVisibility(View.VISIBLE);
                    razorpay.submit(options, CheckoutActivity.this);
                } catch (Exception e) {
                    Log.e("com.example", "Exception: ", e);
                }
            }

            @Override
            public void onValidationError(Map<String, String> error) {
                Log.d("com.example", "Validation failed: " + error.get("field") + " " + error.get("description"));
                Toast.makeText(CheckoutActivity.this, "Validation: " + error.get("field") + " " + error.get("description"), Toast.LENGTH_SHORT).show();
            }
        });
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
        if(requestCode == RazorPayed.UPI_INTENT_REQUEST_CODE){
            razorpay.onActivityResult(requestCode,resultCode,data);
        }
    }


}