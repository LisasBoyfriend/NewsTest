package com.yang.newstest.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 读取assets文件夹下内容的工具类
 */
public class AssetsUtils {

    public static void writeToAssets(Context context, String filename, String data){
        try{
            InputStream inputStream = context.getAssets().open(filename);
            InputStreamReader reader = new InputStreamReader(inputStream);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static String getAssetsContent(Context context,String filename){
        //获取Assets文件夹管理者对象
        AssetManager manager = context.getResources().getAssets();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            InputStream is = manager.open(filename);
            int hasRead = 0;
            byte[]buf = new byte[1024];
            while (true) {
                hasRead=is.read(buf);
                if (hasRead == -1){
                    break;
                }else{
                    baos.write(buf,0,hasRead);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos.toString();
    }
}
