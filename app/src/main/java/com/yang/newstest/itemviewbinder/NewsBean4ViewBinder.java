package com.yang.newstest.itemviewbinder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.drakeet.multitype.ItemViewBinder;
import com.yang.newstest.R;
import com.yang.newstest.bean.NewsBean;
import com.yang.newstest.utils.StringUtils;

public class NewsBean4ViewBinder extends ItemViewBinder<NewsBean.DocsBean.ListBean, NewsBean4ViewBinder.ViewHolder> {
    @NonNull
    @Override
    public NewsBean4ViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        View root = layoutInflater.inflate(R.layout.item_news_4, viewGroup, false);
        return new NewsBean4ViewBinder.ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsBean4ViewBinder.ViewHolder viewHolder, NewsBean.DocsBean.ListBean listBean) {
        viewHolder.tv_news_title.setText(listBean.getListTitle());
        viewHolder.tv_news_author.setText(listBean.getAuthor());
        viewHolder.tv_news_time.setText(StringUtils.resetTimeArray(listBean.getPubTime()));
        if (listBean.getImgUrls().get(0) != null){
            Glide.with(viewHolder.itemView).load(listBean.getImgUrls().get(0)).into(viewHolder.iv_news_image);
        }
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_news_title, tv_news_author, tv_news_time;
        ImageView iv_news_turn_on, iv_news_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_news_title = itemView.findViewById(R.id.tv_news_title);
            this.tv_news_author = itemView.findViewById(R.id.tv_news_author);
            this.tv_news_time = itemView.findViewById(R.id.tv_news_time);
            this.iv_news_turn_on = itemView.findViewById(R.id.iv_news_turn_on);
            this.iv_news_image = itemView.findViewById(R.id.iv_news_image);
        }
    }
}
