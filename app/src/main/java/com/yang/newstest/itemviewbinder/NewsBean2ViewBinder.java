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
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.drakeet.multitype.ItemViewBinder;
import com.yang.newstest.DetailActivity;
import com.yang.newstest.R;
import com.yang.newstest.UniteApplication;
import com.yang.newstest.bean.NewsBean;
import com.yang.newstest.databinding.ItemNews2Binding;
import com.yang.newstest.utils.StringUtils;

import java.util.List;

public class NewsBean2ViewBinder extends ItemViewBinder<NewsBean.DocsBean.ListBean, NewsBean2ViewBinder.ViewHolder> {
    @NonNull
    @Override
    public NewsBean2ViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        ItemNews2Binding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_news_2, viewGroup, false);
        return new NewsBean2ViewBinder.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsBean2ViewBinder.ViewHolder viewHolder, NewsBean.DocsBean.ListBean listBean) {
        ItemNews2Binding binding = viewHolder.getBinding();
        binding.setListBean(listBean);

        if (listBean.getImgUrls().size() > 0) {
            Glide.with(viewHolder.itemView)
                    .load(listBean.getImgUrls().get(0))
                    .placeholder(R.mipmap.loading)
                    .into(viewHolder.binding.ivNewsImage);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ViewBinding2", "onClick: ");
                String url = listBean.getLinkUrl();

                if (listBean.getChannel().getChannelId().equals("8")) {
                    String videoUrl = listBean.getVideo().getUrl();
                    List<String> imgUrls = listBean.getImgUrls();
                    String imageUrl = "";
                    String docuTitle = listBean.getDocTitle();
                    if (imgUrls.size() > 0){
                        imageUrl = imgUrls.get(0);
                    }
                    DetailActivity.start(UniteApplication.getContext(), url, videoUrl, imageUrl, docuTitle);

                } else {
                    DetailActivity.start(UniteApplication.getContext(), url);

                }
            }
        });
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ItemNews2Binding binding;
        public ItemNews2Binding getBinding(){
            return binding;
        }
        public ViewHolder(@NonNull ItemNews2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
