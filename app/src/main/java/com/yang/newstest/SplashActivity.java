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

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_time;
    FrameLayout layout_start_skip;

    Handler handler = new Handler();
    MCountDownTimer mCountDownTimer;
    Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();


    }

    @Override
    protected void onResume() {
        super.onResume();
        mRunnable = new MyRunnable();
        if (AppUtils.isRunBackground(this)) {
            handler.post(mRunnable);
        } else {
            mCountDownTimer = new MCountDownTimer(3000, 1000);
            mCountDownTimer.start();
            handler.postDelayed(mRunnable, 3000);
        }

    }

    private void initView() {
        tv_time = findViewById(R.id.tv_start_skip_count_down);

        layout_start_skip = findViewById(R.id.layout_start_skip);
        tv_time.setText("3" + getString(R.string.click_to_skip));
    }

    @Override
    protected void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        if (mRunnable != null) {
            handler.removeCallbacks(mRunnable);
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_start_skip:
                Log.i("Main", "onClick: ");
                handler.post(mRunnable);
                break;
        }
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

    class MyRunnable implements Runnable {

        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}