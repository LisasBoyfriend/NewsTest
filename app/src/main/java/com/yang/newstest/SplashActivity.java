package com.yang.newstest;

import androidx.appcompat.app.AppCompatActivity;

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

import com.yang.newstest.utils.AppUtils;

import java.util.Observable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_time;
    FrameLayout layout_start_skip;
    MCountDownTimer mCountDownTimer;
    private static Boolean isExit;

//    Handler handler = new Handler();
//    Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();

//        mRunnable = new MyRunnable();


    }

    @Override
    protected void onResume() {
        super.onResume();
//改用RxJava设置启动页
        Flowable<Boolean> flowable = Flowable.create(new FlowableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(FlowableEmitter<Boolean> e) throws Exception {
                if (AppUtils.isRunBackground(getApplicationContext())){
                    e.onNext(true);
                }else {
                    mCountDownTimer = new MCountDownTimer(3000, 1000);
                    mCountDownTimer.start();
                    e.onNext(false);
                }
            }
        }, BackpressureStrategy.ERROR);

        flowable.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (!aBoolean){
                            Thread.sleep(3000);
                            if (!isExit){
                                turnToMain();
                            }
                        }else {
                            turnToMain();
                        }
                    }
                });
//        if (AppUtils.isRunBackground(this)) {
//            handler.post(mRunnable);
//        } else {
//            mCountDownTimer = new MCountDownTimer(3000, 1000);
//            mCountDownTimer.start();
//            handler.postDelayed(mRunnable, 3000);
//        }

    }

    private void initView() {
        tv_time = findViewById(R.id.tv_start_skip_count_down);

        layout_start_skip = findViewById(R.id.layout_start_skip);
        tv_time.setText("3" + getString(R.string.click_to_skip));
        isExit = false;
    }

    @Override
    protected void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
//        if (mRunnable != null) {
//            handler.removeCallbacks(mRunnable);
//        }
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_start_skip:
                Log.i("Main", "onClick: ");
                turnToMain();
                finish();
                isExit = true;
//                handler.post(mRunnable);
                break;
        }
    }

    public void turnToMain(){
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
    }
    class MCountDownTimer extends CountDownTimer {

        public MCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {

            if (tv_time != null) {
                tv_time.setText("" + (l + 1000) / 1000 + getString(R.string.click_to_skip));
            }

        }

        @Override
        public void onFinish() {
            if (tv_time != null) {
                tv_time.setText("0" + getString(R.string.click_to_skip));
            }

        }
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