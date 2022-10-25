package com.yang.newstest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.yang.newstest.utils.SharePreUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class DetailActivity extends AppCompatActivity {

    private static String TAG = "DetailActivity";
    private ImageView iv_back, iv_hearing, iv_more;
    private WebView wb_news;
    private FrameLayout mLayout;
    private View mCustomView;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    private ProgressBar progressBar;
    private String url;
    private SharedPreferences preferences;

    private int webViewSize = 0;

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
        progressBar = findViewById(R.id.progress);
        iv_more = findViewById(R.id.iv_more);
        wb_news = findViewById(R.id.wb_news);
        mLayout = findViewById(R.id.fl_video);
        initWebView(wb_news);

        wb_news.loadUrl(url);
    }

    private void initWebView(WebView mWebView) {
        mWebView.setWebViewClient(new MyWebClient());
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress >= 90){
                    progressBar.setProgress(90);
                }
                if (newProgress == 100){
                    progressBar.setProgress(95);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
                if (mCustomView != null){
                    callback.onCustomViewHidden();
                    return;
                }
                mCustomView = view;
                mCustomView.setVisibility(View.VISIBLE);
                mCustomViewCallback = callback;
                mLayout.addView(mCustomView);
                mLayout.setVisibility(View.VISIBLE);
                mLayout.bringToFront();

                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }

            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
                if (mCustomView == null){
                    return;
                }
                mCustomView.setVisibility(View.GONE);
                mLayout.removeView(mCustomView);
                mCustomView = null;
                mLayout.setVisibility(View.GONE);
                try {
                    mCustomViewCallback.onCustomViewHidden();
                }catch (Exception e){

                }
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            }
        });
        WebSettings settings = mWebView.getSettings();
        //设置可以与JavaScript交互
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        //设置自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        // 5.1以上默认禁止了https和http混用，以下方式是开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        // 开启H5(APPCache)缓存功能
        settings.setAppCacheEnabled(true);
// webView数据缓存分为两种：AppCache和DOM Storage(Web Storage)。
// 开启 DOM storage 功能
        settings.setDomStorageEnabled(true);
// 应用可以有数据库
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setAllowFileAccess(true);
        //设置默认字体大小
        settings.setTextZoom(webViewSize);
        mWebView.addJavascriptInterface(new JsObject(), "myObject");

    }

    public void showBottomSheetsDialog() {
        webViewSize = SharePreUtils.getIntInfoFromSP(SharePreUtils.WEBVIEW_SIZE, preferences);
        RadioButton rb_font_size;
        RadioGroup rg_font_size;
        TextView tv_cancel;
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_font_size);
        rg_font_size = dialog.getWindow().findViewById(R.id.rg_dialog);

        tv_cancel = dialog.getWindow().findViewById(R.id.tv_dialog_cancel);
        if (webViewSize == 80) {
            rb_font_size = dialog.getWindow().findViewById(R.id.rb_dialog_small);
        } else if (webViewSize == 120) {
            rb_font_size = dialog.getWindow().findViewById(R.id.rb_dialog_big);
        } else if (webViewSize == 140) {
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
                        SharePreUtils.putIntInfoToSP(SharePreUtils.WEBVIEW_SIZE, 80, preferences);
                        break;
                    case R.id.rb_dialog_medium:
                        wb_news.getSettings().setTextZoom(100);
                        SharePreUtils.putIntInfoToSP(SharePreUtils.WEBVIEW_SIZE, 100, preferences);
                        break;
                    case R.id.rb_dialog_big:
                        wb_news.getSettings().setTextZoom(120);
                        SharePreUtils.putIntInfoToSP(SharePreUtils.WEBVIEW_SIZE, 120, preferences);
                        break;
                    case R.id.rb_dialog_large:
                        wb_news.getSettings().setTextZoom(140);
                        SharePreUtils.putIntInfoToSP(SharePreUtils.WEBVIEW_SIZE, 140, preferences);
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

    @Override
    protected void onPause() {
        wb_news.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        wb_news.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        wb_news.setWebChromeClient(null);
        wb_news.setWebViewClient(null);
        wb_news.getSettings().setJavaScriptEnabled(false);
        wb_news.clearCache(true);
        wb_news.destroy();
        super.onDestroy();
    }

    class MyWebClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressBar.setVisibility(View.VISIBLE);


            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.i(TAG, "onPageFinished: ");

            //添加js代码
            view.loadUrl("javascript:function img(){" +
                    "var href=document.getElementsByTagName(\"img\");" +
                    "var srcs = [];\n"+
                    "\t\t for(var i=0;i<href.length;i++){\n" +
                    "\t\t \t   var a=document.getElementsByTagName(\"img\")[i];\n" +
                    "\t\t \t   srcs[i]=a.src;\n"+
                    "\t\t \t   a.onclick=function(){\n" +
                    "\t\t \t        window.myObject.showImage(this.src, srcs, i)" +
                    "\t\t \t   };\n" +
                    "\t\t }" +
                    "}");
            view.loadUrl("function video() {\n" +
                    "\t\t\t\tvar href = document.getElementById(\"videoPoster\");\n" +
                    "\t\t\t\tvar a = href.getElementsByTagName(\"source\");\n" +
                    "\t\t\t\tfor (var i = 0; i < a.length; i++) {\n" +
                    "\t\t\t\t\twindow.myObject.showVideo(a[0].src);\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t}");
            //执行js函数
            view.loadUrl("javascript:img()");
            view.loadUrl("javascript:video()");

            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);


        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
        }


    }
    class JsObject{
        @JavascriptInterface
        public void showImage(String src, String[] srcs, int position){
            ArrayList<String> srcsList = (ArrayList<String>) new ArrayList<>(Arrays.asList(srcs));
            ImageActivity.start(DetailActivity.this, srcsList, src, position);
            Log.i(TAG, "showImage: "+src);
        }
        @JavascriptInterface
        public void showVideo(String src){
            Toast.makeText(getApplicationContext(), "555", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "showVideo: "+src);

        }
    }

    /**
     * 横竖屏切换监听
     */
    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        switch (config.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                break;
        }
    }


}