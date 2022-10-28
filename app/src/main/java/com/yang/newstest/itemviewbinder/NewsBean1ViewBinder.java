package com.yang.newstest.itemviewbinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.drakeet.multitype.ItemViewBinder;
import com.yang.newstest.DetailActivity;
import com.yang.newstest.R;
import com.yang.newstest.UniteApplication;
import com.yang.newstest.bean.NewsBean;
import com.yang.newstest.databinding.ItemNews1Binding;
import com.yang.newstest.utils.StringUtils;

public class NewsBean1ViewBinder extends BaseViewBinder<NewsBean.DocsBean.ListBean, ItemNews1Binding>{
    @Override
    public int setLayoutId() {
        return R.layout.item_news_1;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ItemNews1Binding> itemNews1BindingBaseViewHolder, NewsBean.DocsBean.ListBean listBean) {
        super.onBindViewHolder(itemNews1BindingBaseViewHolder, listBean);
        ItemNews1Binding binding = itemNews1BindingBaseViewHolder.getBinding();
        binding.setListBean(listBean);
        itemNews1BindingBaseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = listBean.getLinkUrl();
                DetailActivity.start(UniteApplication.getContext(), url);
            }
        });
    }
}
