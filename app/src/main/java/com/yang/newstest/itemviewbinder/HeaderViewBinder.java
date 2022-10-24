package com.yang.newstest.itemviewbinder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.drakeet.multitype.ItemViewBinder;
import com.yang.newstest.R;
import com.yang.newstest.adapter.ImageBannerAdapter;
import com.yang.newstest.bean.NewsBean;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;

public class HeaderViewBinder extends ItemViewBinder<NewsBean.DocsBean, HeaderViewBinder.ViewHolder> {
    Fragment fragment;
    public HeaderViewBinder (Fragment fragment){
        Log.i("Fra", "HeaderViewBinder: ");
        this.fragment = fragment;
    }
    @NonNull
    @Override
    public HeaderViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        View root = layoutInflater.inflate(R.layout.item_header, viewGroup, false);
        return new HeaderViewBinder.ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderViewBinder.ViewHolder viewHolder, NewsBean.DocsBean docsBean) {

        viewHolder.banner.setAdapter(new ImageBannerAdapter(docsBean.getFocuses()));
        viewHolder.banner.addBannerLifecycleObserver(fragment);
        viewHolder.banner.setIndicator(new CircleIndicator(fragment.getContext()));
        viewHolder.banner.start();

    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        Banner banner;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.banner = itemView.findViewById(R.id.banner);
        }
    }
}
