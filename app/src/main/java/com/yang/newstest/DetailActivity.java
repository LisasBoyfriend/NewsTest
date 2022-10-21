package com.yang.newstest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.yang.newstest.utils.SharePreUtils;

public class DetailActivity extends AppCompatActivity {

    private static String TAG = "DetailActivity";
    ImageView iv_back, iv_hearing, iv_more;
    WebView wb_news;
    String url;
    SharedPreferences preferences;

    int webViewSize = 0;

    public static void start(Context context) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        url = getIntent().getStringExtra("url");

        preferences = SharePreUtils.getSharedPreferences(SharePreUtils.WEBVIEW_INFO, MODE_PRIVATE);
        webViewSize = SharePreUtils.getIntInfoFromSP(SharePreUtils.WEBVIEW_SIZE, preferences);
        initView();

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_more:
                showBottomSheetsDialog();
                break;
            case R.id.iv_hearing:
                break;
        }
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        iv_hearing = findViewById(R.id.iv_hearing);
        iv_more = findViewById(R.id.iv_more);
        wb_news = findViewById(R.id.wb_news);
        initWebView(wb_news);

        wb_news.loadUrl(url);
    }

    private void initWebView(WebView mWebView) {
        mWebView.setWebViewClient(new WebViewClient());
        WebSettings settings = mWebView.getSettings();
        //设置可以与JavaScript交互
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        //设置自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);

        // 5.1以上默认禁止了https和http混用，以下方式是开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        settings.setLoadsImagesAutomatically(true); //支持自动加载图片

        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//允许使用缓存

        //设置默认字体大小
        settings.setTextZoom(webViewSize);

    }

    public void showBottomSheetsDialog() {
        RadioButton rb_font_size;
        RadioGroup rg_font_size;
        TextView tv_cancel;
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_font_size);
        rg_font_size = dialog.getWindow().findViewById(R.id.rg_dialog);

        tv_cancel = dialog.getWindow().findViewById(R.id.tv_dialog_cancel);
        if (webViewSize == 90) {
            rb_font_size = dialog.getWindow().findViewById(R.id.rb_dialog_small);
        } else if (webViewSize == 110) {
            rb_font_size = dialog.getWindow().findViewById(R.id.rb_dialog_big);
        } else if (webViewSize == 120) {
            rb_font_size = dialog.getWindow().findViewById(R.id.rb_dialog_large);
        } else {
            rb_font_size = dialog.getWindow().findViewById(R.id.rb_dialog_medium);
        }
        rb_font_size.setChecked(true);

        rg_font_size.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_dialog_small:
                        wb_news.getSettings().setTextZoom(80);
                        SharePreUtils.putIntInfoToSP(SharePreUtils.WEBVIEW_SIZE, 90, preferences);
                        break;
                    case R.id.rb_dialog_medium:
                        wb_news.getSettings().setTextZoom(100);
                        SharePreUtils.putIntInfoToSP(SharePreUtils.WEBVIEW_SIZE, 100, preferences);
                        break;
                    case R.id.rb_dialog_big:
                        wb_news.getSettings().setTextZoom(120);
                        SharePreUtils.putIntInfoToSP(SharePreUtils.WEBVIEW_SIZE, 110, preferences);
                        break;
                    case R.id.rb_dialog_large:
                        SharePreUtils.putIntInfoToSP(SharePreUtils.WEBVIEW_SIZE, 120, preferences);
                        wb_news.getSettings().setTextZoom(140);
                        break;
                }
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


}