package com.yang.newstest.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtils {


    public static String convertInputStream(InputStream is) {
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
}
