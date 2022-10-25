package com.yang.newstest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yang.newstest.adapter.MainFraAdapter;
import com.yang.newstest.bean.NewsBean;
import com.yang.newstest.fragment.FragmentWode;
import com.yang.newstest.fragment.FragmentYinshipin;
import com.yang.newstest.fragment.FragmentZhuanti;
import com.yang.newstest.fragment.FragmentZixun;
import com.yang.newstest.helper.RetrofitHelper;
import com.yang.newstest.helper.requestImpl.GetZixunRequest;
import com.yang.newstest.utils.URLUtils;


import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<Fragment> fragments;
    BottomNavigationView bottomNavigationView;
    //Retrofit获取网络

//    MyHandler handler = new MyHandler();
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
        setContentView(R.layout.activity_main);
        initView();

        initBna(bottomNavigationView);
        initViewPager(viewPager);
        //HttpURLConnection获取网络数据方式
//        new Thread(myRunnable).start();
        //Retrofit获取网络
//        request(URLUtils.BASE_URL);
        //RxJava+Retrofit获取网络信息
        mRetrofitHelper = new RetrofitHelper();
        requestDatas();

//        requestZixun();



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
        fragments.add(new FragmentZixun(newsList, pageCountOfZixun));
        fragments.add(new FragmentYinshipin(yinpinList, pageCountOfYinpin));
        fragments.add(new FragmentZhuanti(shipinList, pageCountOfShipin));
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

//    public void requestZixun(){
//        Retrofit retrofit = mRetrofitHelper.getRetrofit(URLUtils.BASE_URL);
//        mRetrofitHelper.makeRequest(URLUtils.PATH_FOR_ZIXUN_TUIJIAN, "", retrofit, new Consumer<NewsBean>() {
//            @Override
//            public void accept(NewsBean newsBean) throws Exception {
//
//                Log.i("Main", "accept: "+newsBean.getDocs().getList().get(1).getDocTitle());
//                newsList = newsBean.getDocs().getList();
//                pageCountOfZixun = newsBean.getDocs().getPager().getPageCount();
//                fragments = new ArrayList<>();
//                initFragmentData(fragments);
//                MainFraAdapter adapter = new MainFraAdapter(getSupportFragmentManager(), fragments);
//                viewPager.setAdapter(adapter);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                Toast.makeText(getApplicationContext(), "请检查网络设置", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

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
                        viewPager.setAdapter(adapter);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getApplicationContext(), "请检查网络设置", Toast.LENGTH_SHORT).show();

                    }
                });
    }
//retrofit处理网络数据
//    public void request(String baseUrl){
//        //获取OKHttp拦截器对象
//        OkHttpClient client = new InterceptorOKHttpClient().getClient();
//        //创建Retrofit对象
//        Retrofit retrofit = new Retrofit.Builder()
//                .client(client)
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        //创建网络请求接口实例
//        GetZixunRequest request = retrofit.create(GetZixunRequest.class);
//        //封装发送请求
//        Call<NewsBean> call = request.yourGet(URLUtils.PATH_FOR_ZIXUN);
//        //发送网络请求
//        call.enqueue(new Callback<NewsBean>() {
//            @Override
//            public void onResponse(Call<NewsBean> call, Response<NewsBean> response) {
//                Log.i("MainActivity", "onResponse: "+response.body().toString());
//                newsList = response.body().getDocs().getList();
//                pageCount = response.body().getDocs().getPager().getPageCount();
//                fragments = new ArrayList<>();
//                initFragmentData(fragments);
//                MainFraAdapter adapter = new MainFraAdapter(getSupportFragmentManager(), fragments);
//                viewPager.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(Call<NewsBean> call, Throwable t) {
//
//                Toast.makeText(getApplicationContext(), "请检查网络设置", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//httpUrlConnection处理网络数据
//    class MyHandler extends Handler {
//
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//
//            super.handleMessage(msg);
//            Bundle data = msg.getData();
//            String value = data.getString("value");
//
//            NewsBean newsBean = new Gson().fromJson(value, NewsBean.class);
//
//            newsList = newsBean.getDocs().getList();//新闻数据列表
//
//            fragments = new ArrayList<>();
//            initFragmentData(fragments);
//            MainFraAdapter adapter = new MainFraAdapter(getSupportFragmentManager(), fragments);
//            viewPager.setAdapter(adapter);
//
//        }
//    }

//    class MyRunnable implements Runnable{
//        StringBuilder stringBuilder = new StringBuilder();
//
//        String url = URLUtils.HTTP_URL;
//
//        @Override
//        public void run() {
//            try {
//                if (url != null) {
//                    URL mUrl = new URL(url);
//                    HttpURLConnection urlConnection = (HttpURLConnection) mUrl.openConnection();
//                    urlConnection.setRequestMethod("GET");
//                    urlConnection.connect();
//                    int responseCode = urlConnection.getResponseCode();
//                    if (responseCode == HttpURLConnection.HTTP_OK) {
//                        InputStream inputStream = urlConnection.getInputStream();
//                        stringBuilder.append(StringUtils.convertInputStream(inputStream));
//                        String result = stringBuilder.toString();
//                        Message message = new Message();
//                        Bundle data = new Bundle();
//                        data.putString("value", result);
//                        message.setData(data);
//                        handler.sendMessage(message);
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}