package com.example.dong.tiss2;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import java.io.UnsupportedEncodingException;

import static android.content.Context.NOTIFICATION_SERVICE;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = "FirebaseMsgService";

    private String msg, title;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String channelId = "channel";
        String channelName = "Channel Name";

        NotificationManager notifManager

                = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);

            notifManager.createNotificationChannel(mChannel);

        }

        Log.e(TAG, "onMessageReceived");

        try {
            title = new String(remoteMessage.getNotification().getTitle().getBytes("EUC-KR"),"EUC-KR");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        msg = remoteMessage.getNotification().getBody();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //notification 눌렀을때 행위 구현
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class),0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{1, 1000});

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, mBuilder.build());

        mBuilder.setContentIntent(contentIntent);
    }


}