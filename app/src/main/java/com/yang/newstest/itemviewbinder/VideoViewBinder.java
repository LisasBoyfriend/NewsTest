package com.yang.newstest.itemviewbinder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.yang.newstest.R;
import com.yang.newstest.bean.NewsBean;
import com.yang.newstest.databinding.ItemVideoBinding;

public class VideoViewBinder extends BaseViewBinder<NewsBean.DocsBean.ListBean, ItemVideoBinding>{
    public static final String TAG = "tag";

    @Override
    public int setLayoutId() {
        return R.layout.item_video;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ItemVideoBinding> itemVideoBindingBaseViewHolder, NewsBean.DocsBean.ListBean listBean) {
        itemVideoBindingBaseViewHolder.getBinding().tvVideoTitle.setText(listBean.getDocTitle());
        GSYVideoPlayer player = itemVideoBindingBaseViewHolder.getBinding().gsyPlayer;
        ImageView imageView = new ImageView(player.getContext());
        loadCover(imageView, listBean.getImgUrls().get(0));
        player.setThumbImageView(imageView);

        player.setUpLazy(listBean.getVideo().getUrl(), true, null, null, listBean.getDocTitle());

        player.getTitleTextView().setVisibility(View.GONE);
        player.getBackButton().setVisibility(View.GONE);
        player.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.startWindowFullscreen(v.getContext(), false, true);
            }
        });
        player.setPlayTag(TAG);
        player.setPlayPosition(itemVideoBindingBaseViewHolder.getPosition());
        player.setAutoFullWithSize(true);
        player.setReleaseWhenLossAudio(false);
        player.setShowFullAnimation(true);
        player.setIsTouchWiget(false);
        super.onBindViewHolder(itemVideoBindingBaseViewHolder, listBean);
    }
    private void loadCover(ImageView imageView, String url) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.loading2);
        Glide.with(imageView)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(3000000)
                                .centerCrop()
                                .error(R.mipmap.error)
                                .placeholder(R.mipmap.loading2))
                .load(url)
                .into(imageView);
    }

}
