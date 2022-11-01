package com.yang.newstest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.yang.newstest.adapter.MainFraAdapter;
import com.yang.newstest.bean.NewsBean;
import com.yang.newstest.databinding.ActivityMainBinding;
import com.yang.newstest.fragment.FragmentWode;
import com.yang.newstest.fragment.FragmentYinshipin;
import com.yang.newstest.fragment.FragmentZhuanti;
import com.yang.newstest.fragment.FragmentZixun;
import com.yang.newstest.helper.RetrofitHelper;
import com.yang.newstest.helper.requestImpl.GetZixunRequest;
import com.yang.newstest.utils.URLUtils;



import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding dataBinding;
    private List<Fragment> fragments;
    //Retrofit获取网络

    List<NewsBean> allDataList = new ArrayList<>();
    List<NewsBean.DocsBean.ListBean> newsList;//传给fragment的新闻数据
    List<NewsBean.DocsBean.ListBean> yinpinList;//音频数据
    List<NewsBean.DocsBean.ListBean> shipinList;//视频数据

    int pageCountOfZixun = 0;
    int pageCountOfYinpin = 0;
    int pageCountOfShipin = 0;


    RetrofitHelper mRetrofitHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initBna(dataBinding.activityMainBna);
        initViewPager(dataBinding.activityMainViewpager);

        mRetrofitHelper = new RetrofitHelper();
        requestDatas();




    }


    private void initBna(BottomNavigationView bottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_nav_zixun:
                        dataBinding.activityMainViewpager.setCurrentItem(0, false);
                        break;
                    case R.id.bottom_nav_yinshipin:
                        dataBinding.activityMainViewpager.setCurrentItem(1, false);
                        break;
                    case R.id.bottom_nav_zhuanti:
                        dataBinding.activityMainViewpager.setCurrentItem(2, false);
                        break;
                    case R.id.bittom_nav_wode:
                        dataBinding.activityMainViewpager.setCurrentItem(3, false);
                        break;
                }
                return true;
            }
        });
    }


    public void initFragmentData(List<Fragment> fragments) {
        fragments.add(new FragmentZixun(newsList, pageCountOfZixun));
        fragments.add(new FragmentYinshipin(yinpinList, pageCountOfYinpin));
        fragments.add(new FragmentZhuanti(shipinList, pageCountOfShipin));
        fragments.add(new FragmentWode(shipinList, pageCountOfShipin));
    }

    public void initViewPager(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dataBinding.activityMainBna.getMenu().getItem(position).setChecked(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    //请求fragment显示数据
    public void requestDatas(){
        Retrofit retrofit = mRetrofitHelper.getRetrofit(URLUtils.BASE_URL);
        GetZixunRequest request = retrofit.create(GetZixunRequest.class);
        request.getCallUseRxJava(URLUtils.PATH_FOR_ZIXUN_TUIJIAN)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<NewsBean, Flowable<NewsBean>>() {
                    @Override
                    public Flowable<NewsBean> apply(NewsBean newsBean) throws Exception {
                        allDataList.add(newsBean);
                        return request.getCallUseRxJava(URLUtils.PATH_FOR_YINSHIPIN_SHENGYIN);
                    }
                })
                .flatMap(new Function<NewsBean, Flowable<NewsBean>>() {
                    @Override
                    public Flowable<NewsBean> apply(NewsBean newsBean) throws Exception {
                        allDataList.add(newsBean);
                        return request.getCallUseRxJava(URLUtils.PATH_FOR_YINSHIPIN_SHIPIN);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsBean>() {
                    @Override
                    public void accept(NewsBean newsBean) throws Exception {
                        allDataList.add(newsBean);

                        //获取到了3个接口的newBean

                        newsList = allDataList.get(0).getDocs().getList();
                        pageCountOfZixun = allDataList.get(0).getDocs().getPager().getPageCount();
                        yinpinList = allDataList.get(1).getDocs().getList();
                        pageCountOfYinpin = allDataList.get(1).getDocs().getPager().getPageCount();
                        shipinList = allDataList.get(2).getDocs().getList();
                        pageCountOfShipin = allDataList.get(2).getDocs().getPager().getPageCount();
                        fragments = new ArrayList<>();
                        initFragmentData(fragments);
                        MainFraAdapter adapter = new MainFraAdapter(getSupportFragmentManager(), fragments);
                        dataBinding.activityMainViewpager.setAdapter(adapter);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getApplicationContext(), "请检查网络设置", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
    }
}