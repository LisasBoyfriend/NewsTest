package com.yang.newstest;

import android.app.Application;
import android.content.Context;

public class UniteApplication extends Application {
    //软件初始化操作
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
    }

    public static Context getContext(){
        return context;
    }
}
