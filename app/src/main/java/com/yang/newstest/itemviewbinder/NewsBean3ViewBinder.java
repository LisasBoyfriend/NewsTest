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
import com.yang.newstest.databinding.ItemNews3Binding;
import com.yang.newstest.utils.StringUtils;

public class NewsBean3ViewBinder extends ItemViewBinder<NewsBean.DocsBean.ListBean, NewsBean3ViewBinder.ViewHolder> {
    @NonNull
    @Override
    public NewsBean3ViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        ItemNews3Binding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_news_3, viewGroup, false);
        return new NewsBean3ViewBinder.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsBean3ViewBinder.ViewHolder viewHolder, NewsBean.DocsBean.ListBean listBean) {
        ItemNews3Binding binding = viewHolder.getBinding();
        binding.setListBean(listBean);
        if (listBean.getImgUrls().get(0) != null){
            Glide.with(viewHolder.itemView).load(listBean.getImgUrls().get(0)).into(binding.ivNewsImage1);
            Glide.with(viewHolder.itemView).load(listBean.getImgUrls().get(1)).into(binding.ivNewsImage2);
            Glide.with(viewHolder.itemView).load(listBean.getImgUrls().get(2)).into(binding.ivNewsImage3);

        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ViewBinding3", "onClick: ");
                String url = listBean.getLinkUrl();
                DetailActivity.start(UniteApplication.getContext(), url);            }
        });
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        ItemNews3Binding binding;
        public ItemNews3Binding getBinding(){
            return binding;
        }
        public ViewHolder(@NonNull ItemNews3Binding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }
}
