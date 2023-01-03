package com.korbsti.notificationlogger;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import org.json.JSONObject;

import java.util.List;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity mainActivity = MainActivity.getMainActivity();

        // Set the content view for the activity
        setContentView(R.layout.settings_main);


        // Initialize any other views or resources that you need
        // ...
        Button button = findViewById(R.id.backButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mainActivity.getMainIntent());
            }
        });

        Switch switch1 = findViewById(R.id.switch1);

        if (mainActivity.getAppSettings().isDarkMode()) {
            switch1.setChecked(true);
        } else {
            switch1.setChecked(false);
        }

        ScrollView appListContainer = findViewById(R.id.app_list_container);

        LinearLayout appContainer = new LinearLayout(this);
        appContainer.setOrientation(LinearLayout.VERTICAL);

        PackageManager packageManager = getPackageManager();

        List<ApplicationInfo> appInfo = packageManager.getInstalledApplications(PackageManager.MATCH_APEX);

        for (ApplicationInfo applicationInfo : appInfo) {
            String appName= applicationInfo.loadLabel(packageManager).toString();
            LinearLayout appItems = new LinearLayout(this);
            TextView appNameTextView = new TextView(this);
            appNameTextView.setText(appName);
            appItems.setOrientation(LinearLayout.HORIZONTAL);

            SwitchCompat appSwitch = new SwitchCompat(this);

            for(String favoritedAppName : mainActivity.getAppSettings().getFavoritedAppNames()) {
                if(favoritedAppName.equals(appName)) {
                    appSwitch.setChecked(true);
                }
            }

            String finalAppName = appName;
            appSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (appSwitch.isChecked()) {
                        mainActivity.getAppSettings().addFavoriteAppName(finalAppName);
                    } else {
                        mainActivity.getAppSettings().removeFavoriteAppName(finalAppName);
                    }
                }
            });
            appItems.addView(appNameTextView);
            appItems.addView(appSwitch);
            appContainer.addView(appItems);
        }
        appListContainer.removeAllViews();
        appListContainer.addView(appContainer);

        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch1.isChecked()) {
                    mainActivity.getAppSettings().setDarkMode(true);
                    changeVisualDisplay();
                } else {
                    mainActivity.getAppSettings().setDarkMode(false);
                    changeVisualDisplay();
                }
            }
        });


        Spinner spinner = findViewById(R.id.deleteRecentAfter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (mainActivity.getAppSettings().getDeleteRecentAfter().equals("Never")) {
            spinner.setSelection(0);
        } else if (mainActivity.getAppSettings().getDeleteRecentAfter().equals("1 Day")) {
            spinner.setSelection(1);
        } else if (mainActivity.getAppSettings().getDeleteRecentAfter().equals("Three Days")) {
            spinner.setSelection(2);
        } else if (mainActivity.getAppSettings().getDeleteRecentAfter().equals("Five Days")) {
            spinner.setSelection(3);
        } else if (mainActivity.getAppSettings().getDeleteRecentAfter().equals("One Week")) {
            spinner.setSelection(4);
        } else if (mainActivity.getAppSettings().getDeleteRecentAfter().equals("Two Weeks")) {
            spinner.setSelection(5);
        } else if (mainActivity.getAppSettings().getDeleteRecentAfter().equals("Three Weeks")) {
            spinner.setSelection(6);
        } else if (mainActivity.getAppSettings().getDeleteRecentAfter().equals("One Month")) {
            spinner.setSelection(7);
        } else if (mainActivity.getAppSettings().getDeleteRecentAfter().equals("1 Year")) {
            spinner.setSelection(8);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mainActivity.getAppSettings().setDeleteRecentAfter(spinner.getSelectedItem().toString());
                saveSettings();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mainActivity.getAppSettings().setDeleteRecentAfter(spinner.getSelectedItem().toString());
                saveSettings();

            }
        });


        Spinner spinner2 = findViewById(R.id.deleteFavoriteAfter);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.options, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        if (mainActivity.getAppSettings().getDeleteFavoriteAfter().equals("Never")) {
            spinner2.setSelection(0);
        } else if (mainActivity.getAppSettings().getDeleteFavoriteAfter().equals("1 Day")) {
            spinner2.setSelection(1);
        } else if (mainActivity.getAppSettings().getDeleteFavoriteAfter().equals("Three Days")) {
            spinner2.setSelection(2);
        } else if (mainActivity.getAppSettings().getDeleteFavoriteAfter().equals("Five Days")) {
            spinner2.setSelection(3);
        } else if (mainActivity.getAppSettings().getDeleteFavoriteAfter().equals("One Week")) {
            spinner2.setSelection(4);
        } else if (mainActivity.getAppSettings().getDeleteFavoriteAfter().equals("Two Weeks")) {
            spinner2.setSelection(5);
        } else if (mainActivity.getAppSettings().getDeleteFavoriteAfter().equals("Three Weeks")) {
            spinner2.setSelection(6);
        } else if (mainActivity.getAppSettings().getDeleteFavoriteAfter().equals("One Month")) {
            spinner2.setSelection(7);
        } else if (mainActivity.getAppSettings().getDeleteFavoriteAfter().equals("1 Year")) {
            spinner2.setSelection(8);
        }

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mainActivity.getAppSettings().setDeleteFavoriteAfter(spinner.getSelectedItem().toString());
                saveSettings();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mainActivity.getAppSettings().setDeleteFavoriteAfter(spinner.getSelectedItem().toString());
                saveSettings();

            }
        });

        changeVisualDisplay();

    }

    private void saveSettings() {
        MainActivity mainActivity = MainActivity.getMainActivity();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("deleteFavoriteAfter", mainActivity.getAppSettings().getDeleteFavoriteAfter());
            jsonObject.put("deleteRecentAfter", mainActivity.getAppSettings().getDeleteRecentAfter());
            jsonObject.put("darkMode", mainActivity.getAppSettings().isDarkMode());

            mainActivity.getConfigManager().writeToJsonFile(jsonObject, "settings.json");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeVisualDisplay() {
        if (MainActivity.getMainActivity().getAppSettings().isDarkMode()) {
            findViewById(R.id.settings_menu).setBackgroundColor(Color.BLACK);
            ((Switch) findViewById(R.id.switch1)).setTextColor(Color.WHITE);

            Spinner spinner = findViewById(R.id.deleteRecentAfter);
            for (int i = 0; i != spinner.getChildCount(); i++) {
                ((TextView) spinner.getChildAt(i)).setTextColor(Color.WHITE);
            }
            findViewById(R.id.deleteRecentAfter).setBackgroundColor(Color.BLACK);

            Spinner spinner2 = findViewById(R.id.deleteFavoriteAfter);
            for (int i = 0; i != spinner2.getChildCount(); i++) {
                ((TextView) spinner2.getChildAt(i)).setTextColor(Color.WHITE);
            }
            findViewById(R.id.deleteFavoriteAfter).setBackgroundColor(Color.BLACK);


            ((TextView) findViewById(R.id.titleTextOne)).setTextColor(Color.WHITE);
            ((TextView) findViewById(R.id.titleTextTwo)).setTextColor(Color.WHITE);
            ((TextView) findViewById(R.id.textView)).setTextColor(Color.WHITE);
            ((TextView) findViewById(R.id.textAboveScrollView)).setTextColor(Color.WHITE);


            // get all child elements of the linear layout of layoutInsideScrollview
            ScrollView scrollView = (ScrollView) findViewById(R.id.app_list_container);
            LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);

            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                LinearLayout layout = (LinearLayout) linearLayout.getChildAt(i);
                linearLayout.getChildAt(i).setBackgroundColor(Color.rgb(50, 50, 50));
                ((TextView) layout.getChildAt(0)).setTextColor(Color.WHITE);
                // print out child elements
            }

        } else {
            findViewById(R.id.settings_menu).setBackgroundColor(Color.WHITE);
            ((Switch) findViewById(R.id.switch1)).setTextColor(Color.BLACK);
            ((TextView) findViewById(R.id.titleTextOne)).setTextColor(Color.BLACK);
            ((TextView) findViewById(R.id.titleTextTwo)).setTextColor(Color.BLACK);
            ((TextView) findViewById(R.id.textView)).setTextColor(Color.BLACK);
            ((TextView) findViewById(R.id.textAboveScrollView)).setTextColor(Color.BLACK);
            ((ScrollView) findViewById(R.id.app_list_container)).setBackgroundColor(Color.WHITE);

            Spinner spinner = findViewById(R.id.deleteRecentAfter);
            for (int i = 0; i != spinner.getChildCount(); i++) {
                ((TextView) spinner.getChildAt(i)).setTextColor(Color.BLACK);
            }
            findViewById(R.id.deleteRecentAfter).setBackgroundColor(Color.WHITE);

            Spinner spinner2 = findViewById(R.id.deleteFavoriteAfter);
            for (int i = 0; i != spinner2.getChildCount(); i++) {
                ((TextView) spinner2.getChildAt(i)).setTextColor(Color.BLACK);
            }
            findViewById(R.id.deleteFavoriteAfter).setBackgroundColor(Color.WHITE);


            ScrollView scrollView = (ScrollView) findViewById(R.id.app_list_container);
            LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                LinearLayout layout = (LinearLayout) linearLayout.getChildAt(i);
                linearLayout.getChildAt(i).setBackgroundColor(Color.WHITE);
                ((TextView) layout.getChildAt(0)).setTextColor(Color.BLACK);
            }

        }
    }

}
