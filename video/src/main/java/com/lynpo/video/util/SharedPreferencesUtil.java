package com.lynpo.video.util;

import android.content.SharedPreferences;

public class SharedPreferencesUtil {

    static void putBoolean(SharedPreferences sharedPreferences ,String key,boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    static boolean getBoolean(SharedPreferences sharedPreferences ,String key,boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    static void putInt(SharedPreferences sharedPreferences ,String key,int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    static int getInt(SharedPreferences sharedPreferences ,String key,int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    static void putString(SharedPreferences sharedPreferences ,String key,String  value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    static String  getString(SharedPreferences sharedPreferences ,String key,String  defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    static void putLong(SharedPreferences sharedPreferences ,String key,long  value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    static long  getLong(SharedPreferences sharedPreferences ,String key,long  defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public static void putFloat(SharedPreferences sharedPreferences, String key, float value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    static float  getFloat(SharedPreferences sharedPreferences ,String key,float  defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public static void clearConfig(SharedPreferences config) {
        SharedPreferences.Editor editor = config.edit();
        editor.clear();
        editor.apply();
    }


    public static void remove(SharedPreferences sharedPreferences, String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key).apply();
    }

    public static boolean contains(SharedPreferences sharedPreferences, String key) {
        return sharedPreferences.contains(key);
    }
}
