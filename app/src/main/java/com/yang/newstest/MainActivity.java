package com.yang.newstest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.yang.newstest.adapter.MainFraAdapter;
import com.yang.newstest.bean.NewsBean;
import com.yang.newstest.fragment.FragmentWode;
import com.yang.newstest.fragment.FragmentYinshipin;
import com.yang.newstest.fragment.FragmentZhuanti;
import com.yang.newstest.fragment.FragmentZixun;
import com.yang.newstest.utils.StringUtils;
import com.yang.newstest.utils.URLUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<Fragment> fragments;
    BottomNavigationView bottomNavigationView;

    MyHandler handler = new MyHandler();
    MyRunnable myRunnable = new MyRunnable();
    List<NewsBean.DocsBean.ListBean> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_main);
        initView();

        initBna(bottomNavigationView);
        initViewPager(viewPager);
        new Thread(myRunnable).start();



    }


    private void initBna(BottomNavigationView bottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_nav_zixun:
                        viewPager.setCurrentItem(0, false);
                        break;
                    case R.id.bottom_nav_yinshipin:
                        viewPager.setCurrentItem(1, false);
                        break;
                    case R.id.bottom_nav_zhuanti:
                        viewPager.setCurrentItem(2, false);
                        break;
                    case R.id.bittom_nav_wode:
                        viewPager.setCurrentItem(3, false);
                        break;
                }
                return true;
            }
        });
    }

    public void initView() {
        viewPager = findViewById(R.id.activity_main_viewpager);
        bottomNavigationView = findViewById(R.id.activity_main_bna);
    }

    public void initFragmentData(List<Fragment> fragments) {
        fragments.add(new FragmentZixun(newsList));
        fragments.add(new FragmentYinshipin());
        fragments.add(new FragmentZhuanti());
        fragments.add(new FragmentWode());
    }

    public void initViewPager(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    class MyHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {

            super.handleMessage(msg);
            Bundle data = msg.getData();
            String value = data.getString("value");

            NewsBean newsBean = new Gson().fromJson(value, NewsBean.class);

            newsList = newsBean.getDocs().getList();//新闻数据列表

            fragments = new ArrayList<>();
            initFragmentData(fragments);
            MainFraAdapter adapter = new MainFraAdapter(getSupportFragmentManager(), fragments);
            viewPager.setAdapter(adapter);

        }
    }

    class MyRunnable implements Runnable{
        StringBuilder stringBuilder = new StringBuilder();

        String url = URLUtils.HTTP_URL;

        @Override
        public void run() {
            try {
                if (url != null) {
                    URL mUrl = new URL(url);
                    HttpURLConnection urlConnection = (HttpURLConnection) mUrl.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = urlConnection.getInputStream();
                        stringBuilder.append(StringUtils.convertInputStream(inputStream));
                        String result = stringBuilder.toString();
                        Message message = new Message();
                        Bundle data = new Bundle();
                        data.putString("value", result);
                        message.setData(data);
                        handler.sendMessage(message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}