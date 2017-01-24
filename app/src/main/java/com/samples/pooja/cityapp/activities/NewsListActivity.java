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

import com.samples.pooja.cityapp.R;
import com.samples.pooja.cityapp.adapters.NewsPagerAdapter;
import com.samples.pooja.cityapp.fragments.NetworkFragment;
import com.samples.pooja.cityapp.fragments.NewsListFragment;
import com.samples.pooja.cityapp.listeners.NewsListFragmentChangesListener;
import com.samples.pooja.cityapp.utilities.NewsPageConstants;
import com.samples.pooja.cityapp.webhandlers.DownloadCallback;
import com.samples.pooja.cityapp.webhandlers.DownloadTask;
import com.samples.pooja.cityapp.webhandlers.News;

/*
* This activity displays the news list with tabs for national and city news.
*/
public class NewsListActivity extends AppCompatActivity implements NewsListFragmentChangesListener, DownloadCallback, ViewPager.OnPageChangeListener {

    private NewsPagerAdapter mNewsPagerAdapter;
    private ViewPager mViewPager;
    //Hold the News data in this static field to be referenced by detail pages.
    public static News news;
    // Keep a reference to the NetworkFragment, which owns the AsyncTask object
    // that is used to execute network ops.
    private NetworkFragment mNetworkFragment;

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
        if (mNetworkFragment != null) {
            // Execute the async download.
            mNetworkFragment.startDownload(url);
        }
    }

    private void setDefaultNewsLanguage() {
        setLanguageCode(NewsPageConstants.LANG_CODE_EN);
    }

    private void setLanguageCode(int langCode) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.key_current_language_code), langCode);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds the language toggle button to the action bar.
        getMenuInflater().inflate(R.menu.menu_news_list, menu);

        //Get language menu item
        MenuItem menuItem = menu.getItem(0);
        //Check current language code and set title of menu item accordingly
        int langCode = getLanguageCode();
        if(langCode == 1){
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
            saveNewLanguage();
            invalidateOptionsMenu();
            notifyLanguageChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNewLanguage() {
        //Get current language.
        int langCode = getLanguageCode();
        if(langCode == NewsPageConstants.LANG_CODE_EN){
            //Save new language
            setLanguageCode(NewsPageConstants.LANG_CODE_HI);
        } else {
            setLanguageCode(NewsPageConstants.LANG_CODE_EN);
        }
    }

    /*
    * Notify fragment when language is changed by user
    */
    private void notifyLanguageChanged() {
        int position = mViewPager.getCurrentItem();
        int langCode = getLanguageCode();
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + position);
        if(page != null){
            ((NewsListFragment)page).onLanguageChanged(position, langCode);
        }
    }

    public int getLanguageCode() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPref.getInt(getString(R.string.key_current_language_code), NewsPageConstants.LANG_CODE_EN);
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
        //Get current language
        int langCode = getLanguageCode();
        //Get current fragment
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + position);
        if(page != null){
            ((NewsListFragment)page).loadData(position, langCode);
        }
    }

    @Override
    public void onFragmentReady() {
        onFragmentSelected(0);
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
            //Store the news data in a static field to be accessed by list page and detailed page
            news = (News)finalResult.mResultObject;
            //Pass result to appropriate fragment
            if (page != null) {
                ((NewsListFragment) page).onDownloadComplete((News)finalResult.mResultObject);
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
