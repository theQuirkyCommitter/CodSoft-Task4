
package com.droidamsel.windownotify;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "my_channel";
    private static final int NOTIFICATION_ID = 1;

    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    public void showNotification(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Notification.Builder builder = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.icon)
                    .setContentTitle("WindowNotify")
                    .setContentText("Notification from theQuirkyCommitter")
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        }

        Notification notification;
        assert builder != null;
        notification = builder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "My Channel";
            String channelDescription = "My Channel Description";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            channel.setDescription(channelDescription);

            notificationManager.createNotificationChannel(channel);
        }
    }

    private PendingIntent getShowNotificationPendingIntent() {
        Intent showNotificationIntent = new Intent(this, MainActivity.class);
        showNotificationIntent.setAction("SHOW_NOTIFICATION_ACTION");

        return PendingIntent.getActivity(
                this,
                0,
                showNotificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }


}
