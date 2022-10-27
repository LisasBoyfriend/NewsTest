package com.yang.newstest.itemviewbinder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.drakeet.multitype.ItemViewBinder;
import com.yang.newstest.R;
import com.yang.newstest.adapter.ImageBannerAdapter;
import com.yang.newstest.bean.NewsBean;
import com.yang.newstest.databinding.ItemHeaderBinding;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;

public class HeaderViewBinder extends ItemViewBinder<NewsBean.DocsBean, HeaderViewBinder.ViewHolder> {
    Fragment fragment;
    public HeaderViewBinder (Fragment fragment){
        this.fragment = fragment;
    }
    @NonNull
    @Override
    public HeaderViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        ItemHeaderBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_header, viewGroup, false);
        return new HeaderViewBinder.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderViewBinder.ViewHolder viewHolder, NewsBean.DocsBean docsBean) {

        ItemHeaderBinding binding = viewHolder.getBinding();
        binding.banner.setAdapter(new ImageBannerAdapter(docsBean.getFocuses()));
        binding.banner.setIndicator(new CircleIndicator(fragment.getContext()));
        binding.banner.addBannerLifecycleObserver(fragment);
        binding.banner.start();


    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        ItemHeaderBinding binding;

        public ItemHeaderBinding getBinding() {
            return binding;
        }

        public ViewHolder(@NonNull ItemHeaderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
