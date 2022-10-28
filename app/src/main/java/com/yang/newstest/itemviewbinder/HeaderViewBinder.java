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
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.transformer.DepthPageTransformer;

import java.util.List;

public class HeaderViewBinder extends BaseViewBinder<NewsBean.DocsBean, ItemHeaderBinding> {
    Fragment fragment;
    public HeaderViewBinder (Fragment fragment){
        super();
        this.fragment = fragment;
    }

    @Override
    public int setLayoutId() {
        return R.layout.item_header;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ItemHeaderBinding> itemHeaderBindingBaseViewHolder, NewsBean.DocsBean docsBean) {
        super.onBindViewHolder(itemHeaderBindingBaseViewHolder, docsBean);
        ItemHeaderBinding binding = itemHeaderBindingBaseViewHolder.getBinding();
        binding.banner.setAdapter(new ImageBannerAdapter(docsBean.getFocuses()));
        binding.banner.setIndicator(new RectangleIndicator(fragment.getActivity()));
        binding.banner.addBannerLifecycleObserver(fragment);
        binding.banner.setBannerRound(20);
        binding.banner.setPageTransformer(new DepthPageTransformer());
        binding.banner.start();
    }

}
