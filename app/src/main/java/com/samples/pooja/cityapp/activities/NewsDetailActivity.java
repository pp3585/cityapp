package com.samples.pooja.cityapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.samples.pooja.cityapp.R;
import com.samples.pooja.cityapp.adapters.NewsDetailPagerAdapter;
import com.samples.pooja.cityapp.adapters.NewsPagerAdapter;
import com.samples.pooja.cityapp.listeners.NewsDetailFragmentChangeListener;
import com.samples.pooja.cityapp.webhandlers.News;

/**
 * News Detail activity shows the detailed news for user selected news item.
 */

public class NewsDetailActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, NewsDetailFragmentChangeListener {

    private NewsDetailPagerAdapter mNewsDetailPagerAdapter;
    private ViewPager mViewPager;
    private News mNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_news_detail);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getNewsData();

        mNewsDetailPagerAdapter = new NewsDetailPagerAdapter(getSupportFragmentManager(), mNews);
        // Set up the ViewPager with the adapter.
        mViewPager = (ViewPager) findViewById(R.id.vp_news_detail);
        mViewPager.setAdapter(mNewsDetailPagerAdapter);
        mViewPager.addOnPageChangeListener(this);


    }

    private void getNewsData() {
        mNews = NewsListActivity.news;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onNewsDetailsFetchData() {
        //Get data from public static field of list activity

    }

    @Override
    public void onFragmentSelected(int position) {

    }
}
