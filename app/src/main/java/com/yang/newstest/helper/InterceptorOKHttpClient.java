package com.yang.newstest.helper;


import com.shuyu.gsyvideoplayer.utils.NetworkUtils;
import com.yang.newstest.UniteApplication;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InterceptorOKHttpClient {

    OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

    public OkHttpClient getClient() {
        File cacheFile = new File(UniteApplication.getContext().getExternalCacheDir(), "HttpCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        okHttpClientBuilder.addInterceptor(new IpInterceptor());

        okHttpClientBuilder.cache(cache);
        return okHttpClientBuilder.build();
    }

    class IpInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String host = request.url().host();
            if (!NetworkUtils.isAvailable(UniteApplication.getContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }


            Response response = chain.proceed(request);
            if (NetworkUtils.isAvailable(UniteApplication.getContext())) {
                //有网络情况下，超过1分钟，则重新请求，否则直接使用缓存数据
                int maxAge = 60; //缓存一分钟
                String cacheControl = "public,max-age=" + maxAge;
                //当然如果你想在有网络的情况下都直接走网络，那么只需要
                //将其超时时间maxAge设为0即可
                return response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma").build();

            } else {
                //无网络时直接取缓存数据，该缓存数据保存1周
                int maxStale = 60 * 60 * 24 * 7 * 1;  //1周
                return response.newBuilder()
                        .header("Cache-Control", "public,only-if-cached,max-stale=" + maxStale)
                        .removeHeader("Pragma").build();
            }

        }
    }
}
