package com.samples.pooja.cityapp.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.samples.pooja.cityapp.R;
import com.samples.pooja.cityapp.adapters.NewsDetailWebViewPagerAdapter;
import com.samples.pooja.cityapp.utilities.NewsPageConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsDetailWebViewActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private NewsDetailWebViewPagerAdapter mPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private List<View> mListWebViews;
    private HashMap<Integer, String> mNewsDetailUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_detail_web_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        int selectedPosition = intent.getIntExtra(NewsPageConstants.KEY_SELECTED_POSITION, 0);
        mNewsDetailUrls = getNewsDetailViewUrls();
        initializeViews();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mPagerAdapter = new NewsDetailWebViewPagerAdapter(mListWebViews, selectedPosition);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.vp_news_wv_detail);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(selectedPosition);
    }

    private void initializeViews() {
        mListWebViews = new ArrayList<>();
        for(int i = 0; i < mNewsDetailUrls.size(); i++){
            addView(mListWebViews, mNewsDetailUrls.get(i));
        }
    }

    private HashMap<Integer, String> getNewsDetailViewUrls() {
        return NewsListActivity.news.getNewsLinksMap();
    }

    private List<View> addView(List<View> viewList,String url) {
        WebView webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        viewList.add(webView);
        return viewList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds the language toggle button to the action bar.
        getMenuInflater().inflate(R.menu.menu_news_detail_web_view, menu);

        return true;
    }
}
