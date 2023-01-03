package com.korbsti.notificationlogger.ui.main;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import androidx.viewpager.widget.ViewPager;

import com.korbsti.notificationlogger.MainActivity;
import com.korbsti.notificationlogger.R;

import org.json.JSONObject;

public class NotificationListener extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

            // modify mText from PageViewModel
            MainActivity mainActivity = MainActivity.getMainActivity();
            Log.d("NotificationListener", "onNotificationPosted: " + sbn.getPackageName());

            try {
                JSONObject object = new JSONObject();
                String packageName = sbn.getPackageName();
                PackageManager pm = mainActivity.getPackageManager();
                ApplicationInfo appInfo = getPackageManager().getApplicationInfo(packageName, PackageManager.MATCH_APEX);
                String appName = appInfo.loadLabel(pm).toString();
                // Set the application name and title
                object.put("application", appName);
                object.put("title", sbn.getNotification().extras.getString("android.title"));
                object.put("description", sbn.getNotification().extras.getString("android.text"));
                object.put("date", sbn.getNotification().extras.getString("android.when"));

                for (String appNames : MainActivity.getMainActivity().getAppSettings().getFavoritedAppNames()) {
                    if (appNames.equals(appName)) {
                        mainActivity.getNotificationLogManager().addRecentFavorite(object);
                        return;
                    }
                }
                mainActivity.getNotificationLogManager().addRecentNotification(object);

            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // This method is called when a notification is removed
        // You can access the notification data using the sbn object
    }

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public NotificationListener getService() {
            Log.d("NotificationListener", "getService: ");
            return NotificationListener.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("NotificationListener", "onBind: ");
        return mBinder;
    }


}
