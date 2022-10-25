package com.yang.newstest.helper;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yang.newstest.R;

public class GlideHelper {
    public static RequestOptions getImageActivityOptions(){
        return new RequestOptions()
//.placeholder(placeholder == 0 ? R.drawable.img_loading : placeholder)
                .skipMemoryCache(false)  //用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存所有图片(原图,转换图)
                .fitCenter()   //fitCenter 缩放图片充满ImageView CenterInside大缩小原(图) CenterCrop大裁小扩充满ImageView  Center大裁(中间)小原
                .error(R.mipmap.error);
    }
}
