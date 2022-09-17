package com.splitur.app.utils;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;

public class InAppReviewHelper {

    private static InAppReviewHelper _instance = null;

    private InAppReviewHelper() {
    }

    public static InAppReviewHelper getInstance() {
        if (_instance == null) {
            _instance = new InAppReviewHelper();
        }
        return _instance;
    }

    public static void initReviewFlow(AppCompatActivity activity) {
        ReviewManager manager = ReviewManagerFactory.create(activity);

        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();
                Log.e("ReviewInfo", "initReviewFlow: " + reviewInfo);

                //inAppFlow
                Task<Void> flow = manager.launchReviewFlow(activity, reviewInfo);
                flow.addOnCompleteListener(flowTask -> {
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                    Log.e("flowTask", "initReviewFlow: completed");
                });

            } else {
                // There was some problem, log or handle the error code.
                Log.e("REVIEW_MANAGER", "initReviewFlow: error occurred");
            }
        });
    }
}
