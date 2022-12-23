package com.yang.newstest.utils;

import android.content.SharedPreferences;
import android.util.Log;

import com.yang.newstest.UniteApplication;

public class SharePreUtils {

    public static String WEBVIEW_INFO = "webview_info";
    public static String WEBVIEW_SIZE = "webview_size";

    private static SharedPreferences sharedPreferences;

    public static SharedPreferences getSharedPreferences(String fileName, int mode) {
        if (sharedPreferences == null) {
            sharedPreferences = UniteApplication.getContext().getSharedPreferences(fileName, mode);
        }
        return sharedPreferences;
    }

    public static void putStringInfoToSP(String key, String info, SharedPreferences sp) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, info);
        edit.commit();
    }

    public static void putIntInfoToSP(String key, int info, SharedPreferences sp) {
        Log.i("SP", "putIntInfoToSP: "+key+" "+info);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, info);
        edit.commit();
    }

    public static String getStringInfoFromSP(String key, SharedPreferences sp) {
        return sp.getString(key, null);
    }

    public static int getIntInfoFromSP(String key, SharedPreferences sp) {
        Log.i("SP", "getIntFromSP: "+key+" "+sp.getInt(key, 0));

        return sp.getInt(key, 0);
    }

    public static void clearSP(String key, SharedPreferences sp){
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
    }

    public static void removeInfoFromSP(String key, SharedPreferences sp){
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key);
        edit.apply();
    }
}
