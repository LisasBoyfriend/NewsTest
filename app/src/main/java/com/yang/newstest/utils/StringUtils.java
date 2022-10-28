package com.yang.newstest.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtils {


    public static String convertInputStream(InputStream is) {//用于处理网络获取的流数据
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();
        String response = "";
        try{
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        response = stringBuilder.toString();
        return response;

    }
    public static String resetTimeArray(String pubTime){//用于显示新闻时对时间数据的处理
        StringBuilder stringBuilder = new StringBuilder();
        String[] s = pubTime.split(" ");
        String[] date = s[0].split("-");//把年月日进行了分割，只需要月日
        String[] time = s[1].split(":");//把时分秒进行了分割，只需要时分
        if (date[1].startsWith("0")){
            date[1] = date[1].substring(0);
        }
        stringBuilder.append(date[1]).append("-").append(date[2]);
        stringBuilder.append("  ");
        if (time[0].startsWith("0")){
            time[0] = time[0].substring(0);
        }
        stringBuilder.append(time[0]).append(":").append(time[1]);
        return stringBuilder.toString();
    }
}
