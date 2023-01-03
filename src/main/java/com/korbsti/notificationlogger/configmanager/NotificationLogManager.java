package com.korbsti.notificationlogger.configmanager;

import com.korbsti.notificationlogger.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationLogManager {
    private ArrayList<JSONObject> recentNotifications;
    private ArrayList<JSONObject> recentFavorites;

    private MainActivity mainActivity;

    public NotificationLogManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        recentNotifications = new ArrayList<JSONObject>();
        recentFavorites = new ArrayList<JSONObject>();
    }

    public void addRecentNotification(JSONObject notification) {
        recentNotifications.add(notification);



        JSONObject jsonObject = new JSONObject();
        try {

            JSONObject jsonSave = new JSONObject();
            for(JSONObject recentFavorite : recentFavorites) {
                jsonObject.put(recentFavorite.optString("date"), recentNotifications);
            }
            mainActivity.getConfigManager().writeToJsonFile(jsonSave, "recent.json");
            mainActivity.getConfigManager().writeToJsonFile(jsonObject, "recent.json");



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRecentFavorite(JSONObject notification) {
        recentFavorites.add(notification);

        try {
            // run async task to write to file



            JSONObject jsonObject = new JSONObject();
            for(JSONObject recentFavorite : recentFavorites) {
                jsonObject.put(recentFavorite.optString("date"), recentFavorite);
            }
            mainActivity.getConfigManager().writeToJsonFile(jsonObject, "favorite.json");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<JSONObject> getRecentNotifications() {
        return recentNotifications;
    }

    public ArrayList<JSONObject> getRecentFavorites() {
        return recentFavorites;
    }

    public void removeRecentNotification(String dateToDelete) {
        JSONObject saveNotifs = new JSONObject();
        for (int i = 0; i < recentNotifications.size(); i++) {
            try {
                if (recentNotifications.get(i).getString("date").equals(dateToDelete)) {
                    recentNotifications.remove(i);
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        mainActivity.getConfigManager().writeToJsonFile(saveNotifs, "recent");

    }

    public void removeRecentFavorite(String dateToDelete) {
        JSONObject saveNotifs = new JSONObject();
        for (int i = 0; i < recentFavorites.size(); i++) {
            try {
                if (recentFavorites.get(i).getString("date").equals(dateToDelete)) {
                    recentFavorites.remove(i);
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        mainActivity.getConfigManager().writeToJsonFile(saveNotifs, "favorite");

    }

    public void clearRecentNotifications() {
        recentNotifications.clear();
    }

    public void clearRecentFavorites() {
        recentFavorites.clear();
    }


}
