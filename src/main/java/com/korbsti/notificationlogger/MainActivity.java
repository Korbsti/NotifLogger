package com.korbsti.notificationlogger;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.korbsti.notificationlogger.configmanager.AppSettings;
import com.korbsti.notificationlogger.configmanager.ConfigManager;
import com.korbsti.notificationlogger.configmanager.NotificationLogManager;
import com.korbsti.notificationlogger.ui.main.NotificationBar;
import com.korbsti.notificationlogger.ui.main.NotificationListener;
import com.korbsti.notificationlogger.ui.main.SectionsPagerAdapter;
import com.korbsti.notificationlogger.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ConfigManager configManager;
    private NotificationLogManager notificationLogManager;
    private static MainActivity mainActivity = null;
    private AppSettings appSettings;
    private SettingsActivity settingsActivity;
    private Intent mainIntent;
    private Intent settingsIntent;
    private NotificationListener notificationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        configManager = new ConfigManager(this);
        notificationLogManager = new NotificationLogManager(this);
        appSettings = new AppSettings(this);
        mainIntent = new Intent(this, MainActivity.class);
        settingsIntent = new Intent(this, SettingsActivity.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;

        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.settingsButton;

        mainActivity = this;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (!notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            startActivity(intent);
        }

        Intent serviceIntent = new Intent(this, NotificationBar.class);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent);
            } else {
                startService(serviceIntent);
            }
        } catch (Exception e) {
            Log.e("NotificationLogger", "Error starting service: " + e.getMessage());
            e.printStackTrace();
        }
        // set any background colors to black
        if(appSettings.isDarkMode()) {
            binding.tabs.setBackgroundColor(Color.rgb(0, 0, 0));
            binding.tabs.setTabTextColors(Color.rgb(255, 255, 255), Color.rgb(255, 255, 255));
            binding.viewPager.setBackgroundColor(Color.BLACK);
            binding.settingsButton.setBackgroundColor(Color.BLACK);

        } else {
            binding.tabs.setBackgroundColor(Color.WHITE);
            binding.viewPager.setBackgroundColor(Color.WHITE);
            binding.settingsButton.setBackgroundColor(Color.WHITE);
            // color purple
            binding.tabs.setTabTextColors(Color.rgb(128, 0, 128), Color.rgb(128, 0, 128));

        }



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Entering Settings", Snackbar.LENGTH_LONG).setAction("Settings", null).show();
                startActivity(settingsIntent);
            }
        });

        Intent notifIntent = new Intent(this, NotificationListener.class);
        bindService(notifIntent, serviceConnection, Context.BIND_AUTO_CREATE);


    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public NotificationLogManager getNotificationLogManager() {
        return notificationLogManager;
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public AppSettings getAppSettings() {
        return appSettings;
    }

    public Intent getMainIntent() {
        return mainIntent;
    }

    public Intent getSettingsIntent() {
        return settingsIntent;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("MainActivity", "Service connected");
            notificationListener = ((NotificationListener.LocalBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("MainActivity", "Service disconnected");

            notificationListener = null;
        }
    };

    @Override
    protected void onDestroy() {
        Log.d("MainActivity", "Service Destroyed");
        unbindService(serviceConnection);

        super.onDestroy();

    }


}