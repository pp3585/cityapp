package com.samples.pooja.cityapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.samples.pooja.cityapp.R;
import com.samples.pooja.cityapp.adapters.NewsDetailPagerAdapter;
import com.samples.pooja.cityapp.adapters.NewsPagerAdapter;
import com.samples.pooja.cityapp.fragments.NewsDetailFragment;
import com.samples.pooja.cityapp.fragments.NewsListFragment;
import com.samples.pooja.cityapp.listeners.NewsDetailFragmentChangeListener;
import com.samples.pooja.cityapp.utilities.NewsPageConstants;
import com.samples.pooja.cityapp.webhandlers.News;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * News Detail activity shows the detailed news for user selected news item.
 */

public class NewsDetailActivity extends AppCompatActivity implements NewsDetailFragmentChangeListener {

    private NewsDetailPagerAdapter mNewsDetailPagerAdapter;
    private ViewPager mViewPager;
    private HashMap<Integer, String> mNewsDetailUrls;
    private int mSelectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_news_detail);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        mSelectedPosition = intent.getIntExtra(NewsPageConstants.KEY_SELECTED_POSITION, 0);
        mNewsDetailUrls = getNewsDetailViewUrls();

        mNewsDetailPagerAdapter = new NewsDetailPagerAdapter(getSupportFragmentManager(), mNewsDetailUrls, mSelectedPosition);
        // Set up the ViewPager with the adapter.
        mViewPager = (ViewPager) findViewById(R.id.vp_news_detail);
        mViewPager.setAdapter(mNewsDetailPagerAdapter);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setCurrentItem(mSelectedPosition);
    }

    private HashMap<Integer, String> getNewsDetailViewUrls() {
        return NewsListActivity.news.getNewsLinksMap();
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first page, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous page.
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onNewsDetailsFetchData() {
        //Get data from public static field of list activity

    }

    @Override
    public void onFragmentSelected(int position) {

    }

    @Override
    public void onFragmentReady() {
        //Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vp_news_detail + ":" + mViewPager.getCurrentItem());
        Fragment page = mNewsDetailPagerAdapter.getItem(mViewPager.getCurrentItem());
        if(page != null){
            ((NewsDetailFragment)page).loadWebView();
        }
    }
}
