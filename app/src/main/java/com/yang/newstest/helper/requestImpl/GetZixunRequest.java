package com.yang.newstest.helper.requestImpl;

import com.yang.newstest.bean.NewsBean;
import com.yang.newstest.utils.URLUtils;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface GetZixunRequest {
    @GET
    Call<NewsBean> yourGet(@Url String Url);
    @GET
    Flowable<NewsBean> getCallUseRxJava(@Url String Url);
}
