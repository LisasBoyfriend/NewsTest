package com.yang.newstest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yang.newstest.adapter.MainFraAdapter;
import com.yang.newstest.fragment.FragmentWode;
import com.yang.newstest.fragment.FragmentYinshipin;
import com.yang.newstest.fragment.FragmentZhuanti;
import com.yang.newstest.fragment.FragmentZixun;
import com.yang.newstest.utils.URLUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<Fragment> fragments;
    BottomNavigationView bottomNavigationView;

    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_main);
        initView();


        fragments = new ArrayList<>();
        initFragmentData(fragments);
        MainFraAdapter adapter = new MainFraAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);


        initBna(bottomNavigationView);
        initViewPager(viewPager);

        initNewsData();
    }

    private void initNewsData() {
        result = URLUtils.getHttpResponse(URLUtils.HTTP_URL);
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
        fragments.add(new FragmentZixun());
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
}