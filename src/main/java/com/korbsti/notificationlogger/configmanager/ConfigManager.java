package com.korbsti.notificationlogger.configmanager;

import android.util.Log;

import com.korbsti.notificationlogger.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigManager {

    MainActivity mainActivity;

    private JSONObject recent;
    private JSONObject favorite;
    private JSONObject settings;
    private File recentFile;
    private File favoriteFile;
    private File settingsFile;

    private String directory;

    // Date
    // Time
    // App
    // Notification Information

    public ConfigManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        recent = new JSONObject();
        favorite = new JSONObject();
        settings = new JSONObject();
        directory = mainActivity.getFilesDir().getAbsolutePath() + File.separator + "NotificationLogger";
        if(!new File(directory).exists()){
            new File(directory).mkdirs();
        }
        File recentFile = new File(directory + File.separator+ "recent.json");
        File favoriteFile = new File(directory  + File.separator + "favorite.json");
        File settingsFile = new File(directory + File.separator + "settings.json");

        if (!recentFile.exists()) {
            try {
                recentFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!favoriteFile.exists()) {
            try {
                favoriteFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!settingsFile.exists()) {
            try {
                settingsFile.createNewFile();

                JSONObject settings = new JSONObject();
                settings.put("darkMode", false);
                settings.put("deleteFavoriteAfter", "Never");
                settings.put("deleteRecentAfter", "Never");
                settings.put("favorites", new JSONArray());

                writeToJsonFile(settings, settingsFile.getName());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        recent = readJsonFromFile("recent.json");
        favorite = readJsonFromFile("favorite.json");
        settings = readJsonFromFile("settings.json");

    }

    public JSONObject readJsonFromFile(String fileName) {
        try {
            FileInputStream fis = null;
            try {
                fis = mainActivity.openFileInput(fileName);
                byte[] data = new byte[fis.available()];
                fis.read(data);
                String jsonString = new String(data);
                JSONObject jsonObject = new JSONObject(jsonString);

                return jsonObject;
            } catch (IOException | JSONException e) {
                // Handle I/O or JSON parsing error
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        // Handle error closing file input stream
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeToJsonFile(JSONObject object, String file) {
        FileOutputStream fos = null;
        try {

            fos = mainActivity.openFileOutput(file, mainActivity.MODE_PRIVATE);
            fos.write(object.toString().getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // write getters and setters for every private variable
    // this is just an example
    public JSONObject getRecent() {
        return recent;
    }

    public void setRecent(JSONObject recent) {
        this.recent = recent;
    }

    public JSONObject getFavorite() {
        return favorite;
    }

    public void setFavorite(JSONObject favorite) {
        this.favorite = favorite;
    }

    public JSONObject getSettings() {
        return settings;
    }

    public void setSettings(JSONObject settings) {
        this.settings = settings;
    }

    public File getRecentFile() {
        return recentFile;
    }

    public void setRecentFile(File recentFile) {
        this.recentFile = recentFile;
    }

    public File getFavoriteFile() {
        return favoriteFile;
    }

    public void setFavoriteFile(File favoriteFile) {
        this.favoriteFile = favoriteFile;
    }

    public File getSettingsFile() {
        return settingsFile;
    }

    public void setSettingsFile(File settingsFile) {
        this.settingsFile = settingsFile;
    }


}
