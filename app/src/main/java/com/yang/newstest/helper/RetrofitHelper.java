package com.yang.newstest.helper;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yang.newstest.bean.NewsBean;
import com.yang.newstest.helper.requestImpl.GetZixunRequest;
import com.yang.newstest.utils.URLUtils;

import org.reactivestreams.Subscriber;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {


    //获取Retrofit实例方法
    public Retrofit getRetrofit(String baseUrl) {
        //获取OKHttp拦截器对象
        OkHttpClient client = new InterceptorOKHttpClient().getClient();
        //创建Retrofit对象
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    //发送请求

    public void makeRequest(String url, Retrofit retrofit, Consumer<NewsBean> successfulConsumer, Consumer<Throwable> errorConsumer) {
        GetZixunRequest request = retrofit.create(GetZixunRequest.class);
        request.getCallUseRxJava(URLUtils.PATH_FOR_ZIXUN + url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(successfulConsumer, errorConsumer);
    }
}
