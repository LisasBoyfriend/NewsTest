package com.yang.newstest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.yang.newstest.databinding.ActivityImageBinding;
import com.yang.newstest.helper.GlideHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ImageActivity extends AppCompatActivity  {

    ActivityImageBinding binding;
    private ImageSwitcher imageSwitcher;
    private ArrayList<String> imageSrc;
    private String src;
    private int position;
    private int index = 0;
    private float touchDown;
    private float touchUp;

    private PhotoView mPhotoView;
    private ImageView iv_back;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image);
//        setContentView(R.layout.activity_image);
        imageSrc = getIntent().getStringArrayListExtra("image_srcs");
        imageSrc = handlerSrc(imageSrc);
        src = getIntent().getStringExtra("image_src");
        position = getIntent().getIntExtra("position", 0);
        initView();
        Log.i("Image", "onCreate: "+imageSrc);
        for (int i = 0; i < imageSrc.size(); i++){
            Log.i("Image", "onCreate: "+imageSrc.get(i));
        }

//        initImageSwitcher();

    }

    private ArrayList<String> handlerSrc(ArrayList<String> list) {
        HashSet set = new HashSet(list);
        list.clear();
        list.addAll(set);
        return list;
    }


    public static void start(Context context, ArrayList<String> imageSrcs, String src, int position){
        Intent intent = new Intent(context, ImageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putStringArrayListExtra("image_srcs", imageSrcs);
        intent.putExtra("image_src", src);
        intent.putExtra("position", position);

        context.startActivity(intent);

    }

    private void initView() {
        iv_back = binding.ivBack;
        linearLayout = binding.layoutBack;
        mPhotoView = binding.photoView;
        mPhotoView.enable();
        Glide.with(this)
                .load(src)
                .apply(GlideHelper.getImageActivityOptions())
                .thumbnail(Glide.with(this).load(R.mipmap.loading2))
                .into(mPhotoView);

//        imageSwitcher = findViewById(R.id.image_switcher);
    }

    public void onClick(View view){
        if (view == binding.ivBack){
            finish();
            return;
        }else if (view == binding.photoView){
            if (linearLayout.getVisibility() == View.INVISIBLE){
                linearLayout.setVisibility(View.VISIBLE);
            }else {
                linearLayout.setVisibility(View.INVISIBLE);
            }
        }
    }
    public class MyHandler{
        public void back(View view){
            finish();
        }
        public void setLayoutVisibility(View view){
            if (linearLayout.getVisibility() == View.INVISIBLE){
                linearLayout.setVisibility(View.VISIBLE);
            }else {
                linearLayout.setVisibility(View.INVISIBLE);
            }
        }
    }


//    private void initImageSwitcher() {
//
//        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
//            @Override
//            public View makeView() {
//                PhotoView photoView = new PhotoView(ImageActivity.this);
//
//                Glide.with(ImageActivity.this)
//                        .load(src)
//                        .into(photoView);
//                return photoView;
//            }
//        });
//        imageSwitcher.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
//                    touchDown = motionEvent.getX();
//                    return true;
//                }else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
//                    touchUp = motionEvent.getAction();
//                    if (touchUp - touchDown > 100){
//                        index = index==0?imageSrc.size()-1:index-1;
//                        Glide.with(view)
//                                .load(imageSrc.get(index))
//                                .into((PhotoView)imageSwitcher.getCurrentView());
//                    }else if (touchDown - touchUp > 100){
//                        index = index==imageSrc.size() -1?0:index+1;
//                        Glide.with(view)
//                                .load(imageSrc.get(index))
//                                .into((PhotoView)imageSwitcher.getCurrentView());
//                    }
//                }
//                return false;
//            }
//        });
//    }
}