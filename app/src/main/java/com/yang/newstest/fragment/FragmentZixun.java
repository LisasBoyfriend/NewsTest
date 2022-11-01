package com.yang.newstest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.yang.newstest.databinding.FragmentZixunBinding;
import com.yang.newstest.helper.RetrofitHelper;
import com.yang.newstest.itemviewbinder.HeaderViewBinder;
import com.yang.newstest.itemviewbinder.NewsBean1ViewBinder;
import com.yang.newstest.itemviewbinder.NewsBean2ViewBinder;
import com.yang.newstest.itemviewbinder.NewsBean3ViewBinder;
import com.yang.newstest.itemviewbinder.NewsBean4ViewBinder;
import com.yang.newstest.utils.URLUtils;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import retrofit2.Retrofit;

public class FragmentZixun extends Fragment{

    private static final String TAG = "FragmentZixun";
    private MultiTypeAdapter adapter;
    RetrofitHelper mHelper;
    Retrofit mRetrofit;
    FragmentZixunBinding binding;

    List<NewsBean.DocsBean.ListBean> mData = new ArrayList<>();
    NewsBean.DocsBean docsBean;
    List<Object> data = new ArrayList<>();

    int pageCount = 0;
    int pageNow = 0;

    public FragmentZixun(){

    }

    public FragmentZixun(List<NewsBean.DocsBean.ListBean> data, int pageCount) {
        this.mData = data;
        this.pageCount = pageCount;
        this.pageNow = this.pageCount;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_zixun, container, false);

        mHelper = new RetrofitHelper();

        binding.rvFraZixun.setLayoutManager(new LinearLayoutManager(getContext()));
        //添加Android自带的分割线
        binding.rvFraZixun.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        adapter = new MultiTypeAdapter();
        initAdapter(adapter);
        binding.rvFraZixun.setAdapter(adapter);
        data.addAll(mData);
        adapter.setItems(data);
        adapter.notifyDataSetChanged();
        initSfl(binding.sml);
        requestBannerData();


        return binding.getRoot();
    }



    public void initAdapter(MultiTypeAdapter adapter){
        adapter.register(NewsBean.DocsBean.ListBean.class).to(new NewsBean1ViewBinder(), new NewsBean2ViewBinder()
                , new NewsBean3ViewBinder(), new NewsBean4ViewBinder()).withLinker(new Linker<NewsBean.DocsBean.ListBean>() {
            @Override
            public int index(int i, NewsBean.DocsBean.ListBean listBean) {
                String style = listBean.getListStyle();
                if (style != null){
                    if (style.equals("1")){
                        return 0;
                    }else if (style.equals("2")){
                        return 1;
                    }else if (style.equals("3")){
                        return 2;
                    }else {
                        return 3;
                    }
                }
                return 0;

            }
        });
        adapter.register(NewsBean.DocsBean.class, new HeaderViewBinder(this));
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



    private void requestBannerData(){
        //Retrofit+RxJava框架
        mRetrofit = mHelper.getRetrofit(URLUtils.BASE_URL);
        mHelper.makeRequest(URLUtils.PATH_FOR_ZIXUN_TOUTIAO, "", mRetrofit, new Consumer<NewsBean>() {
            @Override
            public void accept(NewsBean newsBean) throws Exception {

                docsBean = newsBean.getDocs();
                data.clear();
                data.add(docsBean);
                data.addAll(mData);
                adapter.notifyDataSetChanged();

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Toast.makeText(getContext(), "请检查网络设置", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void requestRefresh(){

        //Retrofit+RxJava框架
        mRetrofit = mHelper.getRetrofit(URLUtils.BASE_URL);
        mHelper.makeRequest(URLUtils.PATH_FOR_ZIXUN_TUIJIAN, "", mRetrofit, new Consumer<NewsBean>() {
            @Override
            public void accept(NewsBean newsBean) throws Exception {

                List<NewsBean.DocsBean.ListBean> list = newsBean.getDocs().getList();
                data.clear();
                data.add(docsBean);
                data.addAll(list);
                adapter.notifyDataSetChanged();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Toast.makeText(getContext(), "请检查网络设置", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void requestLoadMore(){
        String pageStr = "index_"+pageNow+".json";
        //Retrofit+RxJava框架
        mRetrofit = mHelper.getRetrofit(URLUtils.BASE_URL);
        mHelper.makeRequest(URLUtils.PATH_FOR_ZIXUN_TUIJIAN, pageStr, mRetrofit, new Consumer<NewsBean>() {
            @Override
            public void accept(NewsBean newsBean) throws Exception {

                List<NewsBean.DocsBean.ListBean> list = newsBean.getDocs().getList();
                data.addAll(list);
                adapter.notifyItemRangeChanged(data.size()-list.size(), list.size());
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

    }



}
