package com.samples.pooja.cityapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.samples.pooja.cityapp.R;
import com.samples.pooja.cityapp.adapters.NewsDetailPagerAdapter;
import com.samples.pooja.cityapp.adapters.NewsPagerAdapter;

/**
 * Created by pooja on 1/20/2017.
 */

public class NewsDetailActivity extends AppCompatActivity {

    private NewsDetailPagerAdapter mNewsDetailPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_news_detail);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mNewsDetailPagerAdapter = new NewsDetailPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the adapter.
        mViewPager = (ViewPager) findViewById(R.id.vp_news_detail);
        mViewPager.setAdapter(mNewsDetailPagerAdapter);
    }
}
