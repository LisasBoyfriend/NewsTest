package com.yang.newstest.itemviewbinder;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.yang.newstest.DetailActivity;
import com.yang.newstest.R;
import com.yang.newstest.UniteApplication;
import com.yang.newstest.bean.NewsBean;
import com.yang.newstest.databinding.ItemNews2Binding;

import java.util.List;

public class NewsBean2ViewBinder extends BaseViewBinder<NewsBean.DocsBean.ListBean, ItemNews2Binding>{
    @Override
    public int setLayoutId() {
        return R.layout.item_news_2;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ItemNews2Binding> itemNews2BindingBaseViewHolder, NewsBean.DocsBean.ListBean listBean) {
        super.onBindViewHolder(itemNews2BindingBaseViewHolder, listBean);
        ItemNews2Binding binding = itemNews2BindingBaseViewHolder.getBinding();
        binding.setListBean(listBean);

        if (listBean.getImgUrls().size() > 0) {
            Glide.with(itemNews2BindingBaseViewHolder.itemView)
                    .load(listBean.getImgUrls().get(0))
                    .placeholder(R.mipmap.loading)
                    .into(itemNews2BindingBaseViewHolder.binding.ivNewsImage);
        }
        itemNews2BindingBaseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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

}
