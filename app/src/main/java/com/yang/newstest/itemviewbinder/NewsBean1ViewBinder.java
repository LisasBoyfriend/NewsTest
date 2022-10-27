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

public class NewsBean1ViewBinder extends ItemViewBinder<NewsBean.DocsBean.ListBean, NewsBean1ViewBinder.ViewHolder> {
    @NonNull
    @Override
    public NewsBean1ViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        ItemNews1Binding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_news_1, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsBean1ViewBinder.ViewHolder viewHolder, NewsBean.DocsBean.ListBean listBean) {

        ItemNews1Binding binding = viewHolder.getBinding();
        binding.setListBean(listBean);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = listBean.getLinkUrl();
                DetailActivity.start(UniteApplication.getContext(), url);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemNews1Binding binding;
        public ItemNews1Binding getBinding(){
            return binding;
        }

        public ViewHolder(@NonNull ItemNews1Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
