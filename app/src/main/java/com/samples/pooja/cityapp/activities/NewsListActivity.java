package com.samples.pooja.cityapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.samples.pooja.cityapp.R;
import com.samples.pooja.cityapp.adapters.NewsPagerAdapter;
import com.samples.pooja.cityapp.fragments.NetworkFragment;
import com.samples.pooja.cityapp.fragments.NewsListFragment;
import com.samples.pooja.cityapp.listeners.NewsListChangesListener;
import com.samples.pooja.cityapp.webhandlers.DownloadCallback;
import com.samples.pooja.cityapp.webhandlers.DownloadTask;
import com.samples.pooja.cityapp.webhandlers.NewsItem;

import org.json.JSONObject;

import java.util.List;

/*
* This activity displays the news list with tabs for national and city news.
*/
public class NewsListActivity extends AppCompatActivity implements NewsListChangesListener, DownloadCallback, ViewPager.OnPageChangeListener {

    private NewsPagerAdapter mNewsPagerAdapter;
    private ViewPager mViewPager;
    // Keep a reference to the NetworkFragment, which owns the AsyncTask object
    // that is used to execute network ops.
    private NetworkFragment mNetworkFragment;
    // Boolean telling us whether a download is in progress, so we don't trigger overlapping
    // downloads with consecutive button clicks.
    private boolean mDownloading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setDefaultNewsLanguage();

        // Create the adapter that will return a fragment for each of the two
        // tabs of the activity.
        mNewsPagerAdapter = new NewsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mNewsPagerAdapter);
        mViewPager.addOnPageChangeListener(this);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager());
    }

    private void startDownload(String url) {
        if (/*!mDownloading && */mNetworkFragment != null) {
            // Execute the async download.
            mNetworkFragment.startDownload(url);
            mDownloading = true;
        }
    }

    private void setDefaultNewsLanguage() {
        setEnLanguageState(true);
    }

    private void setEnLanguageState(boolean b) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.key_is_language_eng), b);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds the language toggle button to the action bar.
        getMenuInflater().inflate(R.menu.menu_news_list, menu);

        //Get language menu item
        MenuItem menuItem = menu.getItem(0);
        //Check current state of language and set title of menu item accordingly
        boolean langIsEn = getEnLanguageState();
        if(langIsEn){
            //If current language is English, menu item provides option to switch to Hindi.
            // Hence the title Hindi.
            menuItem.setTitle(getResources().getString(R.string.action_language_hi));
        } else {
            menuItem.setTitle(getResources().getString(R.string.action_language_en));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =  item.getItemId();
        if(id == R.id.action_language) {
            //Check if language previously selected was English.
            boolean langIsEn = getEnLanguageState();
            langIsEn = !langIsEn;
            //Store the language state in shared preferences
            setEnLanguageState(langIsEn);
            //Change text of button
            invalidateOptionsMenu();
            //Refresh screens
            return true;//Temporary
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean getEnLanguageState() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPref.getBoolean(getString(R.string.key_is_language_eng), true);
    }

    @Override
    public void onNewsListStartDownload(String sWebUrl) {
        //Call API to load data
        startDownload(sWebUrl);

    }

    @Override
    public void onNewsItemSelected(int position, String sDetailUrl, int newsType) {
        //Call detail screen
        Intent intent = new Intent(this, NewsDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onNewsListRefresh(String sWebUrl, int newsType) {
        //Reload data
    }

    /*
    * Called when a fragment is selected by swiping.
    */
    @Override
    public void onFragmentSelected(int position) {
        //Get current fragment
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + position);
        if(page != null){
            ((NewsListFragment)page).loadData(position);
        }
    }

    /*
     * Updates UI based on result of download.
     */
    @Override
    public void updateFromDownload(Object result) {
        DownloadTask.Result finalResult = (DownloadTask.Result)result;
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem());
        //Check for exceptions
        if (finalResult.mException != null) {//Move Result to separate file
            if (page != null) {
                ((NewsListFragment) page).onDownloadError(finalResult.mException);
            }
        } else if (finalResult.mResultObject != null) {
            //Pass result to appropriate fragment
            if (page != null) {
                ((NewsListFragment) page).onDownloadComplete((List<NewsItem>) finalResult.mResultObject);
            }
        }
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
        mDownloading = false;
        if (mNetworkFragment != null) {
            mNetworkFragment.cancelDownload();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.onFragmentSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
