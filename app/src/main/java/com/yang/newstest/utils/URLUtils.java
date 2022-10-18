package com.yang.newstest.utils;

import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class URLUtils {

    public static final String HTTP_URL = "http://newapp.jyb.cn/app_pub/zixun/tuijian/";

    public static String getHttpResponse(String url){
        StringBuilder stringBuilder = new StringBuilder();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (url != null){
                        URL mUrl = new URL(url);
                        HttpURLConnection urlConnection = (HttpURLConnection)mUrl.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.connect();
                        int responseCode = urlConnection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            InputStream inputStream = urlConnection.getInputStream();
                            stringBuilder.append(StringUtils.convertInputStream(inputStream));
                            Log.d("URLUtils", "run: "+stringBuilder.toString().trim());
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        return stringBuilder.toString().trim();
    }
}
