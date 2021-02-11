package com.mrf.mrfmaharashtra.FirebaseServiceClass;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mrf.mrfmaharashtra.Activity.ActivityNotifications;
import com.mrf.mrfmaharashtra.Activity.MainActivity;
import com.mrf.mrfmaharashtra.Activity.Preferences;
import com.mrf.mrfmaharashtra.BuildConfig;
import com.mrf.mrfmaharashtra.R;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class MyFirebaseService extends FirebaseMessagingService {


    public static String title;
    public static String message;
    public static  String imageUrl;
    public static  String timestamp;
    public static  String driver_id;
    public  static  String from_ads;
    public  static  String to_ads;
    public  static  String cust_userId;
    public  static  String user_contact_no;
    public  static  String riding_price;
    private  static  String address_driver;
    Preferences preferences;




    /**
     * Called when message is received.
     *
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */

    private static final String TAG = MyFirebaseService.class.getSimpleName();
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and mrf_soundss messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Map<String, String> data = remoteMessage.getData();

        preferences=new Preferences(this);
        int counter = preferences.getInt("count");
        Log.e("count",""+counter);
        counter=counter+1;
        Log.e("count",""+counter);
        preferences.set("count", counter);
        preferences.commit();



        //int count=counter++;
       // Log.e("count",""+count);


       // MainActivity.tvCount.setText(count);

        // Check if message contains a mrf_soundss payload.
        if (remoteMessage.getNotification() != null)
        {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(notification, data);
            // NotificationTime();
        }
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {

                // JSONObject jsonObject = new JSONObject(remoteMessage.getData());
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "ExceptionJson: " + e.getMessage());
            }
        }

    }

    private void handleDataMessage(JSONObject json) throws JSONException {
        Log.e(TAG, "push json: " + json.toString());

        JSONObject obj=new JSONObject(json.toString().replace("\\"," "));

        try {
            JSONObject data = obj.getJSONObject("data");

            // result.replaceAll("\\","");

             title = data.getString("title");
             message = data.getString("message");
//      boolean isBackground = data.getBoolean("is_background");
             imageUrl = data.getString("image");
             timestamp = data.getString("timestamp");
             driver_id=data.getString("heading");
             from_ads=data.getString("content");
             to_ads=data.getString("img");
             cust_userId=data.getString("date");

            // JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);
            Log.e(TAG, "driver_id: " + driver_id);
            Log.e(TAG, "from_add: " + from_ads);
            Log.e(TAG, "to_add: " + to_ads);
//            Log.e(TAG, "customer_user_id: " + cust_userId);
//            Log.e(TAG, "user_contact_no: " + user_contact_no);
//            Log.e(TAG, "price: " + riding_price);


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message

                Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.logomrf);
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                Intent intent = new Intent(this, ActivityNotifications.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                play_notification(title,message,imageUrl,intent);

            } else {
                // app is in background, show the mrf_soundss in mrf_soundss tray
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.putExtra("message", message);

                play_notification(title,message,imageUrl,resultIntent);

                // check for image attachment
//        if (TextUtils.isEmpty(imageUrl)) {
//          showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
//        } else {
//          // image is present, show mrf_soundss with image
//          showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
//        }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        NotificationUtils notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }
    /**
     * Showing mrf_soundss with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        NotificationUtils notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
    /**
     * Create and show a custom mrf_soundss containing the received FCM message.
     *
     * @param notification FCM mrf_soundss payload received.
     * @param data FCM data payload received.
     */
    private void sendNotification(RemoteMessage.Notification notification, Map<String, String> data) {
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.logomrf);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

         /* preferences=new Preferences(this);
        counter = preferences.getInt("count");
        int count=counter+1;
        preferences.set("count", count);
        preferences.commit();

        MainActivity.tvCount.setText(count);
*/
        final Uri NOTIFICATION_SOUND_URI = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + BuildConfig.APPLICATION_ID + "/" + R.raw.notification);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(NOTIFICATION_SOUND_URI)
                .setContentIntent(pendingIntent)
                .setContentInfo(notification.getTitle())
                .setLargeIcon(icon)
                .setColor(Color.BLUE)
                .setPriority(Notification.PRIORITY_HIGH)
                .setLights(Color.BLUE, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.logomrf);

        try {
            String picture_url = data.get("picture_url");
            if (picture_url != null && !"".equals(picture_url)) {
                URL url = new URL(picture_url);
                Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                notificationBuilder.setStyle(
                        new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(notification.getBody())
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Notification Channel is required for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "channel_id", "channel_name", NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("channel description");
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            notificationManager.createNotificationChannel(channel);
        }
        //final int id=0;

        notificationManager.notify(0, notificationBuilder.build());
        //removeNotification(id);


    }



    public void play_notification(String title,String message,String imageUrl,Intent intent)
    {
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.logomrf);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        final Uri NOTIFICATION_SOUND_URI = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + BuildConfig.APPLICATION_ID + "/" + R.raw.notification);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(NOTIFICATION_SOUND_URI)
                .setContentIntent(pendingIntent)
                .setContentInfo(title)
                .setLargeIcon(icon)
                .setColor(Color.BLUE)
                .setPriority(Notification.PRIORITY_HIGH)
                .setLights(Color.BLUE, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.logomrf);

        try {
            //  String picture_url = data.get("picture_url");
            if (imageUrl != null && !"".equals(imageUrl)) {
                URL url = new URL(imageUrl);
                Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                notificationBuilder.setStyle(
                        new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(title)
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Notification Channel is required for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.notification);

            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            NotificationChannel channel = new NotificationChannel(
                    "channel_id", "channel_name", NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("channel description");
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setSound(sound, attributes);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            notificationManager.createNotificationChannel(channel);
        }
       // final int id=0;

        Notification note = notificationBuilder.build();
        //here
        note.flags = Notification.FLAG_INSISTENT;
        note.flags |= Notification.FLAG_ONGOING_EVENT;
        note.flags |= Notification.FLAG_AUTO_CANCEL;

       // NotificationUtils.clearNotifications(getApplicationContext());

        notificationManager.notify(0, note);



        // play mrf_soundss sound
        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        notificationUtils.playNotificationSound();
    }

}

