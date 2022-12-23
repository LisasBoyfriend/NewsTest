package com.yang.newstest.itemviewbinder;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.yang.newstest.R;
import com.yang.newstest.adapter.ImageBannerAdapter;
import com.yang.newstest.bean.NewsBean;
import com.yang.newstest.databinding.ItemHeaderBinding;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.transformer.DepthPageTransformer;
import com.youth.banner.transformer.ScaleInTransformer;


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
        binding.banner.setBannerRound(5);
        binding.banner.setScrollTime(1000);
        binding.banner.setPageTransformer(new ScaleInTransformer());
        binding.banner.start();
    }

}
