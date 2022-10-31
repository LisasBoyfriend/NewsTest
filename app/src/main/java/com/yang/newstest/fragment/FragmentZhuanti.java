package com.yang.newstest.fragment;

import android.os.Bundle;
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
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.yang.newstest.MainActivity;
import com.yang.newstest.R;
import com.yang.newstest.bean.NewsBean;
import com.yang.newstest.helper.RetrofitHelper;
import com.yang.newstest.itemviewbinder.HeaderViewBinder;
import com.yang.newstest.itemviewbinder.NewsBean1ViewBinder;
import com.yang.newstest.itemviewbinder.NewsBean2ViewBinder;
import com.yang.newstest.itemviewbinder.NewsBean3ViewBinder;
import com.yang.newstest.itemviewbinder.NewsBean4ViewBinder;
import com.yang.newstest.itemviewbinder.VideoViewBinder;
import com.yang.newstest.utils.URLUtils;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import retrofit2.Retrofit;

public class FragmentZhuanti extends Fragment {
    private static final String TAG = "FragmentZhuanTi";
    private RecyclerView recyclerView;
    private MultiTypeAdapter adapter;
    private SmartRefreshLayout mSmartRefreshLayout;
    RetrofitHelper mHelper;
    Retrofit mRetrofit;

    List<NewsBean.DocsBean.ListBean> mData = new ArrayList<>();
    //    NewsBean.DocsBean docsBean;
    List<Object> data = new ArrayList<>();

    int pageCount = 0;
    int pageNow = 0;

    public FragmentZhuanti() {

    }

    public FragmentZhuanti(List<NewsBean.DocsBean.ListBean> data, int pageCount) {
        this.mData = data;
        this.pageCount = pageCount;
        this.pageNow = this.pageCount;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhuanti, container, false);
        initView(view);
        mHelper = new RetrofitHelper();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //添加Android自带的分割线
        adapter = new MultiTypeAdapter();
        initAdapter(adapter);
        recyclerView.setAdapter(adapter);
        data.addAll(mData);
        adapter.setItems(data);
        adapter.notifyDataSetChanged();
        initSfl(mSmartRefreshLayout);
        return view;
    }

    public void initView(View view) {
        recyclerView = view.findViewById(R.id.rv_fra_zhuanti);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItem = dx + dy;
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(VideoViewBinder.TAG)
                            && (position < dx || position > lastVisibleItem)) {
                        if (GSYVideoManager.isFullState(getActivity())) {
                            return;
                        }
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        GSYVideoManager.releaseAllVideos();
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        mSmartRefreshLayout = view.findViewById(R.id.sml);
    }


    public void initAdapter(MultiTypeAdapter adapter) {
//        adapter.register(NewsBean.DocsBean.ListBean.class).to(new NewsBean1ViewBinder(), new NewsBean2ViewBinder()
//                , new NewsBean3ViewBinder(), new NewsBean4ViewBinder()).withLinker(new Linker<NewsBean.DocsBean.ListBean>() {
//            @Override
//            public int index(int i, NewsBean.DocsBean.ListBean listBean) {
//                if (listBean.getListStyle().equals("1")){
//                    return 0;
//                }else if (listBean.getListStyle().equals("2")){
//                    return 1;
//                }else if (listBean.getListStyle().equals("3")){
//                    return 2;
//                }else {
//                    return 3;
//                }
//            }
//        });
//        adapter.register(NewsBean.DocsBean.class, new HeaderViewBinder(this));
        adapter.register(NewsBean.DocsBean.ListBean.class, new VideoViewBinder());
    }

    @Override
    public void onPause() {
        GSYVideoManager.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.onResume();
    }

    public void initSfl(SmartRefreshLayout mSmartRefreshLayout) {
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

                if (pageNow > 0) {
                    pageNow--;
                }
                requestLoadMore();//需要分页，这里需要重构url
                mSmartRefreshLayout.finishLoadMore(1000);
            }
        });
    }

    private void requestRefresh() {

        //Retrofit+RxJava框架
        mRetrofit = mHelper.getRetrofit(URLUtils.BASE_URL);
        mHelper.makeRequest(URLUtils.PATH_FOR_YINSHIPIN_SHIPIN, "", mRetrofit, new Consumer<NewsBean>() {
            @Override
            public void accept(NewsBean newsBean) throws Exception {

                List<NewsBean.DocsBean.ListBean> list = newsBean.getDocs().getList();
                data.clear();
//                data.add(docsBean);
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

    private void requestLoadMore() {
        String pageStr = "index_" + pageNow + ".json";
        //Retrofit+RxJava框架
        mRetrofit = mHelper.getRetrofit(URLUtils.BASE_URL);
        mHelper.makeRequest(URLUtils.PATH_FOR_YINSHIPIN_SHIPIN, pageStr, mRetrofit, new Consumer<NewsBean>() {
            @Override
            public void accept(NewsBean newsBean) throws Exception {

                List<NewsBean.DocsBean.ListBean> list = newsBean.getDocs().getList();
                data.addAll(list);
                adapter.notifyItemRangeChanged(data.size() - list.size(), list.size());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (pageNow == 0) {
                    Toast.makeText(getContext(), "已经到底啦", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "请检查网络设置", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
