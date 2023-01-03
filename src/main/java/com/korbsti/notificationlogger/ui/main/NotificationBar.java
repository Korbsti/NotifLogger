package com.korbsti.notificationlogger.ui.main;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.korbsti.notificationlogger.R;

public class NotificationBar extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        // Create a notification channel (only required on Android 8.0 or higher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("MyServiceChannel", "My Service", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create a notification to show to the user
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyServiceChannel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("My Service")
                .setContentText("Running in the foreground");
        // Start the service in the foreground
        startForeground(1, builder.build());
        // Do your background work here
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // Stop the foreground service
        stopForeground(true);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
