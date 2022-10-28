package com.yang.newstest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.common.eventbus.Subscribe;
import com.yang.newstest.databinding.ActivitySplashBinding;
import com.yang.newstest.utils.AppUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {
    TextView tv_time;
    FrameLayout layout_start_skip;
    ActivitySplashBinding binding;
    Disposable mDisposable;

//    Handler handler = new Handler();
//    Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        initView();

//        mRunnable = new MyRunnable();


    }

    @Override
    protected void onResume() {
        super.onResume();

        Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Long value) {
                        tv_time.setText(3 - value + "s 跳过");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        turnToMain();
                    }
                });

    }

    private void initView() {
        tv_time = binding.tvStartSkipCountDown;

        layout_start_skip = binding.layoutStartSkip;
        tv_time.setText("3" + getString(R.string.click_to_skip));
    }

    @Override
    protected void onDestroy() {

//        if (mRunnable != null) {
//            handler.removeCallbacks(mRunnable);
//        }
        super.onDestroy();
    }

//    public class Handlers {
//        public void skip(View view) {
//            turnToMain();
//            finish();
//            isExit = true;
//        }
//    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_start_skip:
                Log.i("Main", "onClick: ");
                turnToMain();
                finish();
                //                handler.post(mRunnable);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        super.onBackPressed();
    }

    public void turnToMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        finish();
    }


//    class MyRunnable implements Runnable {
//
//        @Override
//        public void run() {
//            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }

}