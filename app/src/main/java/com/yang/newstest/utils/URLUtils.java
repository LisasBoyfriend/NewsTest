package com.yang.newstest.utils;

import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLUtils {

    public static final String HTTP_URL = "http://newapp.jyb.cn/app_pub/zixun/tuijian/";
    public static final String BASE_URL = "http://newapp.jyb.cn/";
    public static final String PATH_FOR_ZIXUN_TUIJIAN = "/app_pub/zixun/tuijian/";
    public static final String PATH_FOR_ZIXUN_TOUTIAO = "/app_pub/zixun/toutiao/";
    public static final String PATH_FOR_YINSHIPIN_SHENGYIN = "/app_pub/ysp/shengyin/";
    public static final String PATH_FOR_YINSHIPIN_SHIPIN = "/app_pub/ysp/shipin/";

    public String getHttpResponse(String url) {
        StringBuilder stringBuilder = new StringBuilder();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (url != null) {
                        URL mUrl = new URL(url);
                        HttpURLConnection urlConnection = (HttpURLConnection) mUrl.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.connect();
                        int responseCode = urlConnection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            InputStream inputStream = urlConnection.getInputStream();
                            stringBuilder.append(StringUtils.convertInputStream(inputStream));

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Log.d("URLUtils", "run: " + stringBuilder.toString().trim());
        return stringBuilder.toString().trim();
    }
}
