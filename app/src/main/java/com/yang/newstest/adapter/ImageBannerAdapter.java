package com.yang.newstest.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yang.newstest.DetailActivity;
import com.yang.newstest.UniteApplication;
import com.yang.newstest.bean.NewsBean;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class ImageBannerAdapter extends BannerAdapter<NewsBean.DocsBean.FocusesBean, ImageBannerAdapter.BannerViewHoler> {
    public ImageBannerAdapter(List<NewsBean.DocsBean.FocusesBean> datas) {
        super(datas);
    }

    @Override
    public ImageBannerAdapter.BannerViewHoler onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHoler(imageView);
    }

    @Override
    public void onBindView(ImageBannerAdapter.BannerViewHoler holder, NewsBean.DocsBean.FocusesBean data, int position, int size) {

        Glide.with(holder.imageView)
                .load(data.getFocusImageUrl())
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = data.getLinkUrl();
                DetailActivity.start(UniteApplication.getContext(), url);
            }
        });
    }

    protected class BannerViewHoler extends RecyclerView.ViewHolder {
        ImageView imageView;
        public BannerViewHoler(@NonNull View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView;
        }
    }
}
