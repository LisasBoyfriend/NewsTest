package com.yang.newstest.helper.requestImpl;

import com.yang.newstest.bean.NewsBean;
import com.yang.newstest.utils.URLUtils;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetZixunRequest {
    @GET(URLUtils.PATH_FOR_ZIXUN)
    Call<NewsBean> getCall();
}
