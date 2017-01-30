package com.samples.pooja.cityapp.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.samples.pooja.cityapp.R;
import com.samples.pooja.cityapp.adapters.NewsDetailPagerAdapter;
import com.samples.pooja.cityapp.fragments.DetailPageFragment;
import com.samples.pooja.cityapp.fragments.NetworkFragment;
import com.samples.pooja.cityapp.fragments.NewsListFragment;
import com.samples.pooja.cityapp.listeners.NewsDetailFragmentChangeListener;
import com.samples.pooja.cityapp.utilities.NewsPageConstants;
import com.samples.pooja.cityapp.webhandlers.DownloadCallback;
import com.samples.pooja.cityapp.webhandlers.DownloadTask;
import com.samples.pooja.cityapp.webhandlers.News;
import com.samples.pooja.cityapp.webhandlers.NewsDetail;

import java.util.HashMap;

/**
 * News Detail activity shows the detailed news for user selected news item.
 */

public class DetailPageActivity extends AppCompatActivity implements NewsDetailFragmentChangeListener, DownloadCallback, ViewPager.OnPageChangeListener {

    private NewsDetailPagerAdapter mNewsDetailPagerAdapter;
    private ViewPager mViewPager;
    private HashMap<Integer, String> mNewsDetailUrls;
    private int mSelectedPosition;
    private NetworkFragment mNetworkFragment;

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
        mViewPager.addOnPageChangeListener(this);
        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                mViewPager.setCurrentItem(mSelectedPosition);
                if(mSelectedPosition == 0){
                    onFragmentSelected(0);
                }
            }
        });

        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager());
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
    public void onNewsDetailsStartDownload(String url) {
        //Call API to get data
        startDownload(url);
    }

    private void startDownload(String url) {
        if (mNetworkFragment != null) {
            // Execute the async download.
            mNetworkFragment.startDownload(url);
        }
    }

    @Override
    public void onFragmentSelected(int position) {
        Fragment page = (Fragment) mNewsDetailPagerAdapter.instantiateItem(mViewPager, position);
        if(page != null){
            ((DetailPageFragment)page).loadData(position, mNewsDetailUrls.get(position));
        }
    }

    @Override
    public void onFragmentReady() {

    }

    @Override
    public void updateFromDownload(Object result) {
        DownloadTask.Result finalResult = (DownloadTask.Result)result;
        Fragment page = (Fragment) mNewsDetailPagerAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
        //Check for exceptions
        if (finalResult.mException != null) {//Move Result to separate file
            if (page != null) {
                ((DetailPageFragment) page).onDownloadError(finalResult.mException);
            }
        }else if (finalResult.mResultObject != null) {
            //Pass result to appropriate fragment
            if (page != null) {
                ((DetailPageFragment) page).onDownloadComplete((NewsDetail) finalResult.mResultObject);
            }
        }
        /*ProgressBar progressBar = (ProgressBar) findViewById(R.id.pb_detail);
        progressBar.setVisibility(View.GONE);*/
    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {
        switch(progressCode) {
            // You can add UI behavior for progress updates here.
            case Progress.ERROR:
                break;
            case Progress.CONNECT_SUCCESS:
                break;
            case Progress.GET_INPUT_STREAM_SUCCESS:
                break;
            case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:
                break;
        }
    }

    @Override
    public void finishDownloading() {
        if (mNetworkFragment != null) {
            mNetworkFragment.cancelDownload();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mSelectedPosition = position;
        onFragmentSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
