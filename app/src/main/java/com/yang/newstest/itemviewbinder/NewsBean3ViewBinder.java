package com.yang.newstest.itemviewbinder;

import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.yang.newstest.DetailActivity;
import com.yang.newstest.R;
import com.yang.newstest.UniteApplication;
import com.yang.newstest.bean.NewsBean;
import com.yang.newstest.databinding.ItemNews3Binding;

public class NewsBean3ViewBinder extends BaseViewBinder<NewsBean.DocsBean.ListBean, ItemNews3Binding> {
    @Override
    public int setLayoutId() {
        return R.layout.item_news_3;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ItemNews3Binding> itemNews3BindingBaseViewHolder, NewsBean.DocsBean.ListBean listBean) {
        super.onBindViewHolder(itemNews3BindingBaseViewHolder, listBean);
        ItemNews3Binding binding = itemNews3BindingBaseViewHolder.getBinding();
        binding.setListBean(listBean);
        if (listBean.getImgUrls().get(0) != null){
            Glide.with(itemNews3BindingBaseViewHolder.itemView).load(listBean.getImgUrls().get(0)).into(binding.ivNewsImage1);
            Glide.with(itemNews3BindingBaseViewHolder.itemView).load(listBean.getImgUrls().get(1)).into(binding.ivNewsImage2);
            Glide.with(itemNews3BindingBaseViewHolder.itemView).load(listBean.getImgUrls().get(2)).into(binding.ivNewsImage3);

        }
        itemNews3BindingBaseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ViewBinding3", "onClick: ");
                String url = listBean.getLinkUrl();
                DetailActivity.start(UniteApplication.getContext(), url);            }
        });
    }
}
