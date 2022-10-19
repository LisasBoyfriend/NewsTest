package com.yang.newstest.helper;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InterceptorOKHttpClient {

    OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

    public OkHttpClient getClient(){
        okHttpClientBuilder.addInterceptor(new IpInterceptor());
        return okHttpClientBuilder.build();
    }
    class IpInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String host = request.url().host();


            Response response = chain.proceed(request);

            return response;
        }
    }
}
