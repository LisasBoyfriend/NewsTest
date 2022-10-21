package com.yang.newstest.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drakeet.multitype.Linker;
import com.drakeet.multitype.MultiTypeAdapter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yang.newstest.R;
import com.yang.newstest.bean.NewsBean;
import com.yang.newstest.helper.InterceptorOKHttpClient;
import com.yang.newstest.helper.RetrofitHelper;
import com.yang.newstest.helper.requestImpl.GetZixunRequest;
import com.yang.newstest.itemviewbinder.NewsBean1ViewBinder;
import com.yang.newstest.itemviewbinder.NewsBean2ViewBinder;
import com.yang.newstest.itemviewbinder.NewsBean3ViewBinder;
import com.yang.newstest.itemviewbinder.NewsBean4ViewBinder;
import com.yang.newstest.utils.URLUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentZixun extends Fragment{

    private RecyclerView recyclerView;
    private MultiTypeAdapter adapter;
    private SmartRefreshLayout mSmartRefreshLayout;
    RetrofitHelper mHelper;
    Retrofit mRetrofit;

    List<NewsBean.DocsBean.ListBean> mData = new ArrayList<>();
    int pageCount = 0;
    int pageNow = 0;

    public FragmentZixun(List<NewsBean.DocsBean.ListBean> data, int pageCount) {
        this.mData = data;
        this.pageCount = pageCount;
        this.pageNow = this.pageCount;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zixun, container, false);
        initView(view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //添加Android自带的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        adapter = new MultiTypeAdapter();
        initAdapter(adapter);
        recyclerView.setAdapter(adapter);
        adapter.setItems(mData);
        adapter.notifyDataSetChanged();
        initSfl(mSmartRefreshLayout);

        mHelper = new RetrofitHelper();
        return view;
    }

    public void initView(View view){
        recyclerView = view.findViewById(R.id.rv_fra_zixun);
        mSmartRefreshLayout = view.findViewById(R.id.sml);
    }

    public void initAdapter(MultiTypeAdapter adapter){
        adapter.register(NewsBean.DocsBean.ListBean.class).to(new NewsBean1ViewBinder(), new NewsBean2ViewBinder()
                , new NewsBean3ViewBinder(), new NewsBean4ViewBinder()).withLinker(new Linker<NewsBean.DocsBean.ListBean>() {
            @Override
            public int index(int i, NewsBean.DocsBean.ListBean listBean) {
                if (listBean.getListStyle().equals("1")){
                    return 0;
                }else if (listBean.getListStyle().equals("2")){
                    return 1;
                }else if (listBean.getListStyle().equals("3")){
                    return 2;
                }else {
                    return 3;
                }
            }
        });
    }

    public void initSfl(SmartRefreshLayout mSmartRefreshLayout){
        mSmartRefreshLayout.setRefreshHeader(new MaterialHeader(getContext()).setShowBezierWave(true));
        mSmartRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                requestRefresh();//不需要分页，直接用之前的数据即可
                pageNow = pageCount;//下拉刷新后注意重置当前页码
                mSmartRefreshLayout.finishRefresh(1000);
                Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();

            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                if (pageNow > 0){
                    pageNow--;
                }
                requestLoadMore();//需要分页，这里需要重构url
                mSmartRefreshLayout.finishLoadMore(1000);
            }
        });
    }

    private void requestRefresh(){

        //Retrofit+RxJava框架
        mRetrofit = mHelper.getRetrofit(URLUtils.BASE_URL);
        mHelper.makeRequest("", mRetrofit, new Consumer<NewsBean>() {
            @Override
            public void accept(NewsBean newsBean) throws Exception {

                List<NewsBean.DocsBean.ListBean> list = newsBean.getDocs().getList();
                mData.clear();
                mData.addAll(list);
                adapter.notifyDataSetChanged();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Toast.makeText(getContext(), "请检查网络设置", Toast.LENGTH_SHORT).show();
            }
        });
        //单Retrofit框架
//        Call<NewsBean> call = getHttpCall(URLUtils.BASE_URL,"");
//        call.enqueue(new Callback<NewsBean>() {
//            @Override
//            public void onResponse(Call<NewsBean> call, Response<NewsBean> response) {
//                List<NewsBean.DocsBean.ListBean> list = response.body().getDocs().getList();
//                mData.clear();
//                mData.addAll(list);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<NewsBean> call, Throwable t) {
//                Toast.makeText(getContext(), "请检查网络设置", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void requestLoadMore(){
        String pageStr = "index_"+pageNow+".json";
        //Retrofit+RxJava框架
        mRetrofit = mHelper.getRetrofit(URLUtils.BASE_URL);
        mHelper.makeRequest(pageStr, mRetrofit, new Consumer<NewsBean>() {
            @Override
            public void accept(NewsBean newsBean) throws Exception {

                List<NewsBean.DocsBean.ListBean> list = newsBean.getDocs().getList();
                mData.addAll(list);
                adapter.notifyItemRangeChanged(mData.size()-list.size(), list.size());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (pageNow == 0){
                    Toast.makeText(getContext(), "已经到底啦", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "请检查网络设置", Toast.LENGTH_SHORT).show();
                }            }
        });
//        Call<NewsBean> call = getHttpCall(URLUtils.BASE_URL, pageStr);
//        call.enqueue(new Callback<NewsBean>() {
//            @Override
//            public void onResponse(Call<NewsBean> call, Response<NewsBean> response) {
//                List<NewsBean.DocsBean.ListBean> list = response.body().getDocs().getList();
//                mData.addAll(list);
//                adapter.notifyItemRangeChanged(mData.size()-list.size(), list.size());
//            }
//
//            @Override
//            public void onFailure(Call<NewsBean> call, Throwable t) {
//                if (pageNow == 0){
//                    Toast.makeText(getContext(), "已经到底啦", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getContext(), "请检查网络设置", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    //retrofit处理网络数据
//    public Call<NewsBean> getHttpCall(String baseUrl, String url){
//        //获取OKHttp拦截器对象
//        OkHttpClient client = new InterceptorOKHttpClient().getClient();
//        //创建Retrofit对象
//        Retrofit retrofit = new Retrofit.Builder()
//                .client(client)
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        //创建网络请求接口实例
//        GetZixunRequest request = retrofit.create(GetZixunRequest.class);
//        //封装发送请求
//        Call<NewsBean> call = request.yourGet(URLUtils.PATH_FOR_ZIXUN + url);
//        return call;
//
//    }

}
