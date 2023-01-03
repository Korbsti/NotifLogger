package com.korbsti.notificationlogger.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.korbsti.notificationlogger.MainActivity;

import org.json.JSONObject;

import java.sql.Date;

public class PageViewModel extends ViewModel {


    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();

    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            MainActivity instance = MainActivity.getMainActivity();
            if (input == 1) {
                String recent = "";
                if (instance.getNotificationLogManager().getRecentNotifications().isEmpty()) {
                    return "No recent notifications";
                }
                for (JSONObject str : instance.getNotificationLogManager().getRecentNotifications()) {
                    try {
                        if (!instance.getAppSettings().getDeleteFavoriteAfter().equals("Never")) {
                            Date date = Date.valueOf(str.getString("date"));
                            switch (str.optString("date")) {
                                case "One Day":
                                    if (date.getTime() < System.currentTimeMillis() - 86400000) {
                                        instance.getNotificationLogManager().removeRecentNotification(str.toString());
                                        continue;
                                    }
                                    break;
                                case "Three Days":
                                    if (date.getTime() < System.currentTimeMillis() - 259200000) {
                                        instance.getNotificationLogManager().removeRecentNotification(str.toString());
                                        continue;
                                    }
                                    break;

                                case "Five Days":
                                    if (date.getTime() < System.currentTimeMillis() - 432000000) {
                                        instance.getNotificationLogManager().removeRecentNotification(str.toString());
                                        continue;
                                    }
                                    break;
                                case "One Week":
                                    if (date.getTime() < System.currentTimeMillis() - 604800000) {
                                        instance.getNotificationLogManager().removeRecentNotification(str.toString());
                                        continue;
                                    }
                                    break;
                                case "Two Weeks":
                                    if (date.getTime() < System.currentTimeMillis() - 1209600000) {
                                        instance.getNotificationLogManager().removeRecentNotification(str.toString());
                                        continue;
                                    }
                                    break;
                                case "Three Weeks":
                                    if (date.getTime() < System.currentTimeMillis() - 1814400000) {
                                        instance.getNotificationLogManager().removeRecentNotification(str.toString());
                                        continue;
                                    }
                                    break;

                                case "One Month":
                                    if (date.getTime() < System.currentTimeMillis() - 2592000000L) {
                                        instance.getNotificationLogManager().removeRecentNotification(str.toString());
                                        continue;
                                    }
                                    break;
                                case "1 Year":
                                    if (date.getTime() < System.currentTimeMillis() - 31536000000L) {
                                        instance.getNotificationLogManager().removeRecentNotification(str.toString());
                                        continue;
                                    }
                                    break;

                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    recent += str.toString() + "\n";
                }
                return recent;
            } else if (input == 2) {
                String favorite = "d";

                if (instance.getNotificationLogManager().getRecentFavorites().isEmpty()) {
                    return "No recent favorites";
                }
                for (JSONObject str : instance.getNotificationLogManager().getRecentFavorites()) {
                    try {
                        if (!instance.getAppSettings().getDeleteFavoriteAfter().equals("Never")) {
                            Date date = Date.valueOf(str.getString("date"));
                            switch (str.optString("date")) {
                                case "One Day":
                                    if (date.getTime() < System.currentTimeMillis() - 86400000) {
                                        instance.getNotificationLogManager().removeRecentFavorite(str.toString());
                                        continue;
                                    }
                                    break;
                                case "Three Days":
                                    if (date.getTime() < System.currentTimeMillis() - 259200000) {
                                        instance.getNotificationLogManager().removeRecentFavorite(str.toString());
                                        continue;
                                    }
                                    break;

                                case "Five Days":
                                    if (date.getTime() < System.currentTimeMillis() - 432000000) {
                                        instance.getNotificationLogManager().removeRecentFavorite(str.toString());
                                        continue;
                                    }
                                    break;
                                case "One Week":
                                    if (date.getTime() < System.currentTimeMillis() - 604800000) {
                                        instance.getNotificationLogManager().removeRecentFavorite(str.toString());
                                        continue;
                                    }
                                    break;
                                case "Two Weeks":
                                    if (date.getTime() < System.currentTimeMillis() - 1209600000) {
                                        instance.getNotificationLogManager().removeRecentFavorite(str.toString());
                                        continue;
                                    }
                                    break;
                                case "Three Weeks":
                                    if (date.getTime() < System.currentTimeMillis() - 1814400000) {
                                        instance.getNotificationLogManager().removeRecentFavorite(str.toString());
                                        continue;
                                    }
                                    break;

                                case "One Month":
                                    if (date.getTime() < System.currentTimeMillis() - 2592000000L) {
                                        instance.getNotificationLogManager().removeRecentFavorite(str.toString());
                                        continue;
                                    }
                                    break;
                                case "1 Year":
                                    if (date.getTime() < System.currentTimeMillis() - 31536000000L) {
                                        instance.getNotificationLogManager().removeRecentFavorite(str.toString());
                                        continue;
                                    }
                                    break;

                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    favorite += str.toString() + "\n";
                }
                return favorite;

            }


            return "null";


        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }
}