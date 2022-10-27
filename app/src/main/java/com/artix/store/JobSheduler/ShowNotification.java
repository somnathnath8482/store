package com.artix.store.JobSheduler;

import static android.app.Notification.BADGE_ICON_SMALL;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.artix.store.MainActivity;
import com.artix.store.R;

import java.util.Calendar;

public class ShowNotification {


    private  NotificationManager notificationManager;
    private  NotificationCompat.Builder builder;
    Context contex;

    public ShowNotification(Context contex) {
        this.contex = contex;



        Intent intent = new Intent();

        intent = new Intent(contex, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);




//        Intent intent = new Intent(this, NotificationActivity.class);
        String channel_id = "notification_channel";
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(contex, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        builder
                = new NotificationCompat
                .Builder(contex, channel_id)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setWhen(Calendar.getInstance().getTimeInMillis())
                .setColor(Color.rgb(55, 84, 96))
                .setOngoing(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);



      notificationManager = (NotificationManager) contex.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(channel_id, "web_app", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(notificationChannel);
        }


    }

    public void showNotification( int total, int current) {

        builder = builder.setContentTitle("Checking.....")
                .setContentText(total+"")
                .setProgress(total,current,false)
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(0, builder.build());
    }
    public void UpdateNotification( int total, int current) {


        builder = builder.setContentTitle(current+"")
                .setContentText(total+"")
                .setProgress(total,current,false);

        notificationManager.notify(0, builder.build());
    }

}
