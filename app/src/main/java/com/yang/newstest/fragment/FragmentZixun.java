package com.yang.newstest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drakeet.multitype.ItemViewDelegate;
import com.drakeet.multitype.JavaClassLinker;
import com.drakeet.multitype.Linker;
import com.drakeet.multitype.MultiTypeAdapter;
import com.yang.newstest.R;
import com.yang.newstest.bean.NewsBean;
import com.yang.newstest.itemviewbinder.NewsBean1ViewBinder;
import com.yang.newstest.itemviewbinder.NewsBean2ViewBinder;
import com.yang.newstest.itemviewbinder.NewsBean3ViewBinder;
import com.yang.newstest.itemviewbinder.NewsBean4ViewBinder;

import java.util.ArrayList;
import java.util.List;

public class FragmentZixun extends Fragment {

    private RecyclerView recyclerView;
    private MultiTypeAdapter adapter;

    List<NewsBean.DocsBean.ListBean> mData = new ArrayList<>();

    public FragmentZixun(List<NewsBean.DocsBean.ListBean> data) {
        this.mData = data;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zixun, container, false);
        recyclerView = view.findViewById(R.id.rv_fra_zixun);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //添加Android自带的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        adapter = new MultiTypeAdapter();
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
//        adapter.register(NewsBean.DocsBean.ListBean.class, new NewsBean2ViewBinder());

        recyclerView.setAdapter(adapter);
        adapter.setItems(mData);
        adapter.notifyDataSetChanged();
        return view;
    }



}
