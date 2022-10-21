package com.yang.newstest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity{

    ImageView iv_back, iv_hearing, iv_more;
    WebView wb_news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setContentView(R.layout.activity_detail);
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        iv_hearing = findViewById(R.id.iv_hearing);
        iv_more = findViewById(R.id.iv_more);
        wb_news = findViewById(R.id.wv_news);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.iv_more:
                Toast.makeText(this, "more", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_hearing:
                Toast.makeText(this, "hearing", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}