package com.yang.newstest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.yang.newstest.utils.SharePreUtils;

public class UniteApplication extends Application {
    //软件初始化操作
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
        SharedPreferences sharedPreferences = SharePreUtils.getSharedPreferences(SharePreUtils.WEBVIEW_INFO, MODE_PRIVATE);
        SharePreUtils.putIntInfoToSP(SharePreUtils.WEBVIEW_SIZE, 100, sharedPreferences);
    }

    public static Context getContext(){
        return context;
    }
}
