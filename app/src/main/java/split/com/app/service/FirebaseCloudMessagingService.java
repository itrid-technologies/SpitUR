package split.com.app.service;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import split.com.app.utils.Configration;
import split.com.app.utils.NotificationsUtils;

public class FirebaseCloudMessagingService extends FirebaseMessagingService {

    public static final MutableLiveData<String> deviceToken = new MutableLiveData<>();
    private static final String TAG = "FirebaseCloudMessagingS";
    private NotificationsUtils notificationUtils;

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        if (!s.isEmpty()) {
            deviceToken.postValue(s);
            Log.e(TAG, "onNewToken: " + s);
        } else {
            Log.e(TAG, "onNewToken: no new token");
        }
    }//onNewToken

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody(), remoteMessage.getData().get("type"));
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            final String notiType = remoteMessage.getData().get("type");
            Log.e(TAG, "Data Payload: " + notiType);

            if (notiType != null) {
                if (notiType.equals("new_message")) {
                    //broadcast new msg value to chat screen 1-1
                    Intent intent = new Intent(Configration.CHAT_MSG_NOTIFICATION);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                } else if (notiType.equals("new_group_message")) {
                    //broadcast new msg value to group chat
                    Intent intent = new Intent(Configration.GROUP_CHAT_MSG_NOTIFICATION);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                }
                else if (notiType.equals("otp_request")) {
                    //broadcast new msg value to group chat
                    Intent intent = new Intent(Configration.OTP_NOTIFICATION);
                    intent.putExtra("sender_id", remoteMessage.getData().get("sender_id"));
                    intent.putExtra("group_id", remoteMessage.getData().get("group_id"));
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                }
            }

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }//onMessageReceived

    private void handleNotification(String message, String logoutType) {
        if (!NotificationsUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Configration.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            pushNotification.putExtra("type", logoutType);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationsUtils NotificationsUtils = new NotificationsUtils(getApplicationContext());
            NotificationsUtils.playNotificationSound();
        } else {
            // If the app is in background, firebase itself handles the notification
            Log.e(TAG, "handleNotification: app is in background");
        }
    }//handleNotification

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json);

        try {
            // JSONObject data = json.getJSONObject("json");
//            String title = data.getString("title");
//            String message = data.getString("message");
//            String type = data.getString("type");
//            boolean isBackground = data.getBoolean("is_background");
//            String imageUrl = data.getString("image");
//            String timestamp = data.getString("timestamp");
//            JSONObject payload = data.getJSONObject("payload");
//            Log.e(TAG, "title: " + title);
//            Log.e(TAG, "message: " + message);
//            Log.e(TAG, "isBackground: " + isBackground);
//            Log.e(TAG, "payload: " + payload.toString());
//            Log.e(TAG, "imageUrl: " + imageUrl);
//            Log.e(TAG, "timestamp: " + timestamp);
//            Log.e(TAG, "type: " + type);

            if (json.get("type").toString().equals("otp_request")) {
                Intent intent = new Intent(Configration.PUSH_NOTIFICATION);
                intent.putExtra("value1", json.get("type").toString());
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            }

            // app is in background, show the notification in notification tray
//                Intent resultIntent = new Intent(getApplicationContext(), Dashboard.class);
//                resultIntent.putExtra("message", json.get("message").toString());
//                resultIntent.putExtra("type", json.get("type").toString());
//
//                // check for image attachment
//                if (TextUtils.isEmpty("imageUrl")) {
//                    showNotificationMessage(getApplicationContext(), json.get("title").toString(), json.get("message").toString(), json.get("timestamp").toString(), resultIntent);
//                    // handle
//                } else {
//                    // image is present, show notification with image
//                    //showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
//                }

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationsUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationsUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }

}//end class
