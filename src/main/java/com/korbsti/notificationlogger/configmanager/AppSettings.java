package com.korbsti.notificationlogger.configmanager;

import android.graphics.Color;
import android.widget.LinearLayout;

import com.korbsti.notificationlogger.MainActivity;
import com.korbsti.notificationlogger.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;

public class AppSettings {
    private boolean darkMode;
    private String deleteFavoriteAfter;
    private String deleteRecentAfter;
    private MainActivity mainActivity;
    private ArrayList<String> favoritedAppNames;

    public AppSettings(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        try {
            JSONObject settings = mainActivity.getConfigManager().getSettings();
            darkMode = settings.optBoolean("darkMode", false);
            deleteFavoriteAfter = settings.optString("deleteFavoriteAfter", "Never");
            deleteRecentAfter = settings.optString("deleteRecentAfter", "Never");
            favoritedAppNames = new ArrayList<String>();
            JSONArray jsonArray = settings.optJSONArray("favorites");
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    favoritedAppNames.add(jsonArray.getString(i));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {


        this.darkMode = darkMode;

        if (darkMode) {
            mainActivity.findViewById(R.id.main_page).setBackgroundColor(Color.BLACK);
        } else {
            mainActivity.findViewById(R.id.main_page).setBackgroundColor(Color.WHITE);

        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("darkMode", darkMode);
            jsonObject.put("deleteFavoriteAfter", deleteFavoriteAfter);
            jsonObject.put("deleteRecentAfter", deleteRecentAfter);
            jsonObject.put("favorites", new JSONArray(favoritedAppNames));

            mainActivity.getConfigManager().writeToJsonFile(jsonObject, "settings.json");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public String getDeleteFavoriteAfter() {
        return deleteFavoriteAfter;
    }

    public void setDeleteFavoriteAfter(String deleteFavoriteAfter) {
        this.deleteFavoriteAfter = deleteFavoriteAfter;
    }

    public String getDeleteRecentAfter() {
        return deleteRecentAfter;
    }

    public void setDeleteRecentAfter(String deleteRecentAfter) {
        this.deleteRecentAfter = deleteRecentAfter;
    }

    public void addFavoriteAppName(String appName) {
        favoritedAppNames.add(appName);

        // add favorite to json file and save it to settings file
        try {
            mainActivity.getConfigManager().getSettings().put("favorites", new JSONArray(favoritedAppNames));
            mainActivity.getConfigManager().writeToJsonFile(mainActivity.getConfigManager().getSettings(), "settings.json");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void removeFavoriteAppName(String appName) {

        favoritedAppNames.remove(appName);

        // add favorite to json file and save it to settings file
        try {
            mainActivity.getConfigManager().getSettings().put("favorites", new JSONArray(favoritedAppNames));
            mainActivity.getConfigManager().writeToJsonFile(mainActivity.getConfigManager().getSettings(), "settings.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getFavoritedAppNames() {
        return favoritedAppNames;
    }

}
