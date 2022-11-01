package com.yang.newstest.itemviewbinder;

import android.util.Log;
import android.view.View;


import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.yang.newstest.DetailActivity;
import com.yang.newstest.R;
import com.yang.newstest.UniteApplication;
import com.yang.newstest.bean.NewsBean;
import com.yang.newstest.databinding.ItemNews4Binding;

public class NewsBean4ViewBinder extends BaseViewBinder<NewsBean.DocsBean.ListBean, ItemNews4Binding> {
    @Override
    public int setLayoutId() {
        return R.layout.item_news_4;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ItemNews4Binding> itemNews4BindingBaseViewHolder, NewsBean.DocsBean.ListBean listBean) {
        super.onBindViewHolder(itemNews4BindingBaseViewHolder, listBean);
        ItemNews4Binding binding = itemNews4BindingBaseViewHolder.getBinding();
        binding.setListBean(listBean);
        if (listBean.getImgUrls().get(0) != null){
            Glide.with(itemNews4BindingBaseViewHolder.itemView).load(listBean.getImgUrls().get(0)).into(binding.ivNewsImage);
        }
        itemNews4BindingBaseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ViewBinding4", "onClick: ");
                String url = listBean.getLinkUrl();
                DetailActivity.start(UniteApplication.getContext(), url);           }
        });
    }
}
