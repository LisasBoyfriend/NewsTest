package com.yang.newstest.itemviewbinder;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.drakeet.multitype.ItemViewBinder;
import com.yang.newstest.DetailActivity;
import com.yang.newstest.R;
import com.yang.newstest.UniteApplication;
import com.yang.newstest.bean.NewsBean;
import com.yang.newstest.utils.StringUtils;

public class NewsBean3ViewBinder extends ItemViewBinder<NewsBean.DocsBean.ListBean, NewsBean3ViewBinder.ViewHolder> {
    @NonNull
    @Override
    public NewsBean3ViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        View root = layoutInflater.inflate(R.layout.item_news_3, viewGroup, false);
        return new NewsBean3ViewBinder.ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsBean3ViewBinder.ViewHolder viewHolder, NewsBean.DocsBean.ListBean listBean) {
        viewHolder.tv_news_title.setText(listBean.getListTitle());
        viewHolder.tv_news_author.setText(listBean.getAuthor());
        viewHolder.tv_news_time.setText(StringUtils.resetTimeArray(listBean.getPubTime()));
        if (listBean.getImgUrls().get(0) != null){
            Glide.with(viewHolder.itemView).load(listBean.getImgUrls().get(0)).into(viewHolder.iv_news_image1);
            Glide.with(viewHolder.itemView).load(listBean.getImgUrls().get(1)).into(viewHolder.iv_news_image2);
            Glide.with(viewHolder.itemView).load(listBean.getImgUrls().get(2)).into(viewHolder.iv_news_image3);

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
        TextView tv_news_title, tv_news_author, tv_news_time;
        ImageView iv_news_turn_on, iv_news_image1, iv_news_image2, iv_news_image3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_news_title = itemView.findViewById(R.id.tv_news_title);
            this.tv_news_author = itemView.findViewById(R.id.tv_news_author);
            this.tv_news_time = itemView.findViewById(R.id.tv_news_time);
            this.iv_news_turn_on = itemView.findViewById(R.id.iv_news_turn_on);
            this.iv_news_image1 = itemView.findViewById(R.id.iv_news_image1);
            this.iv_news_image2 = itemView.findViewById(R.id.iv_news_image2);
            this.iv_news_image3 = itemView.findViewById(R.id.iv_news_image3);


        }
    }
}
