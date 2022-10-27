package com.yang.newstest.itemviewbinder;

import android.content.Intent;
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
import com.yang.newstest.databinding.ItemNews4Binding;
import com.yang.newstest.utils.StringUtils;

public class NewsBean4ViewBinder extends ItemViewBinder<NewsBean.DocsBean.ListBean, NewsBean4ViewBinder.ViewHolder> {
    @NonNull
    @Override
    public NewsBean4ViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        ItemNews4Binding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_news_4, viewGroup, false);
        return new NewsBean4ViewBinder.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsBean4ViewBinder.ViewHolder viewHolder, NewsBean.DocsBean.ListBean listBean) {
        ItemNews4Binding binding = viewHolder.getBinding();
        binding.setListBean(listBean);
        if (listBean.getImgUrls().get(0) != null){
            Glide.with(viewHolder.itemView).load(listBean.getImgUrls().get(0)).into(binding.ivNewsImage);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ViewBinding4", "onClick: ");
                String url = listBean.getLinkUrl();
                DetailActivity.start(UniteApplication.getContext(), url);           }
        });
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        ItemNews4Binding binding;
        public ItemNews4Binding getBinding(){
            return binding;
        }

        public ViewHolder(@NonNull ItemNews4Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
