package com.yang.newstest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.yang.newstest.DetailActivity;
import com.yang.newstest.R;
import com.yang.newstest.UniteApplication;
import com.yang.newstest.bean.NewsBean;
import com.yang.newstest.databinding.BannerBinding;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;
import java.util.zip.Inflater;

public class ImageBannerAdapter extends BannerAdapter<NewsBean.DocsBean.FocusesBean, ImageBannerAdapter.BannerViewHoler> {
    public ImageBannerAdapter(List<NewsBean.DocsBean.FocusesBean> datas) {
        super(datas);
    }

    @Override
    public ImageBannerAdapter.BannerViewHoler onCreateHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        BannerBinding binding = DataBindingUtil.inflate(inflater, R.layout.banner, parent, false);
        return new BannerViewHoler(binding);
    }

    @Override
    public void onBindView(ImageBannerAdapter.BannerViewHoler holder, NewsBean.DocsBean.FocusesBean data, int position, int size) {

        Glide.with(holder.getBinding().getRoot())
                .load(data.getFocusImageUrl())
                .into(holder.getBinding().ivBanner);

        holder.getBinding().tvTitle.setText(data.getFocusImageTitle());
        holder.getBinding().ivBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = data.getLinkUrl();
                DetailActivity.start(UniteApplication.getContext(), url);
            }
        });
    }

    protected class BannerViewHoler extends RecyclerView.ViewHolder {
        BannerBinding binding;

        public BannerBinding getBinding() {
            return binding;
        }

        public BannerViewHoler(@NonNull BannerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
