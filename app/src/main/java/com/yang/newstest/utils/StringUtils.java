package com.yang.newstest.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtils {


    public static String convertInputStream(InputStream is){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();
        String response = "";
        while (true){
            try {
                if ((line = bufferedReader.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            stringBuilder.append(line);
        }
        response = stringBuilder.toString().trim();
        return response;

    }
}
