package yuana.kodemetro.com.cargallery.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import yuana.kodemetro.com.cargallery.CarApp;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/13/17
 */

public class SharedPref {

    private static SharedPreferences getPref() {
        Context context = CarApp.getContext();
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void saveString(String key, String value) {
        getEditor().putString(key, value).apply();
    }

    public static String getString(String key) {
        return getPref().getString(key, null);
    }

    public static void saveInt(String key, int value) {
        getEditor().putInt(key, value).apply();
    }

    public static int getInt(String key) {
        return getPref().getInt(key, 0);
    }

    public static void saveBoolean(String key, boolean value) {
        getEditor().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key) {
        return getPref().getBoolean(key, false);
    }

    public static void saveLong(String key, Long value) {
        getEditor().putLong(key, value).apply();
    }

    public static Long getLong(String key) {
        return getPref().getLong(key, 0);
    }

    public static void saveFloat(String key, float value) {
        getEditor().putFloat(key, value).apply();
    }

    public static float getFloat(String key) {
        return getPref().getFloat(key, 0);
    }

    public static void remove(String key) {
        getEditor().remove(key).apply();
    }

    public static SharedPreferences.Editor getEditor() {
        return getPref().edit();
    }
}
