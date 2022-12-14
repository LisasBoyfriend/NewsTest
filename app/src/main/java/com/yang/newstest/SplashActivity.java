package com.yang.newstest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import com.yang.newstest.databinding.ActivitySplashBinding;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;
    Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();

        Observable.intervalRange(0, 4, 0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Long value) {
                        binding.tvStartSkipCountDown.setText(3 - value + "s 跳过");
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

        binding.tvStartSkipCountDown.setText("3" + getString(R.string.click_to_skip));
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }



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

}