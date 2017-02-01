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
import com.samples.pooja.cityapp.datamodels.News;

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

        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                mViewPager.setCurrentItem(0);
                onFragmentSelected(0);
            }
        });
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
            doLanguageSwitch();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Actions to do when language is changed by user
     */
    private void doLanguageSwitch() {
        saveNewLanguage();
        invalidateOptionsMenu();
        changeTabTitles();
        notifyLanguageChanged();
    }

    /**
     * Set tab titles based on position
     */
    private void changeTabTitles() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        TabLayout.Tab tabNational = tabLayout.getTabAt(0);
        TabLayout.Tab tabCity = tabLayout.getTabAt(1);
        if (tabNational != null) {
            tabNational.setText(getTabName(0));
        }
        if(tabCity != null){
            tabCity.setText(getTabName(1));
        }
    }

    /**
     * Get tab name based on current position and current language
     * @param tabPosition position of current selected tab
     * @return name for the tab at provided position
     */
    private String getTabName(int tabPosition) {
        String tabTitle = "";
        int langCode = getLanguageCode();
        if(langCode == NewsPageConstants.LANG_CODE_EN){
            switch(tabPosition){
                case 0:
                    tabTitle = NewsPageConstants.STR_TAB_1_EN;
                    break;
                case 1:
                    tabTitle = NewsPageConstants.STR_TAB_2_EN;
                    break;
            }
        } else {
            switch(tabPosition){
                case 0:
                    tabTitle = NewsPageConstants.STR_TAB_1_HI;
                    break;
                case 1:
                    tabTitle = NewsPageConstants.STR_TAB_2_HI;
                    break;
            }
        }
        return tabTitle;
    }

    /**
     * Gets current language, switches it and saves the new language
     */
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

    /**
     * Get language code from shared preferences
     * @return language code
     */
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
        saveCurrentState();
        //Call detail screen
        Intent intent = new Intent(this, DetailPageActivity.class);
        intent.putExtra(NewsPageConstants.KEY_SELECTED_POSITION, position);
        startActivityForResult(intent, NewsPageConstants.REQUEST_CODE_DETAIL_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NewsPageConstants.REQUEST_CODE_DETAIL_ACTIVITY && resultCode == RESULT_CANCELED){
            restoreCurrentState();
        }
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

    /**
     * Saves current language and current selected tab to shared preferences
     */
    private void saveCurrentState() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        //Save current tab selection
        editor.putInt(NewsPageConstants.KEY_NEWS_TYPE_TAB, mViewPager.getCurrentItem());
        //Save current language selection
        editor.putInt(NewsPageConstants.KEY_LANGUAGE_CODE, getLanguageCode());
        editor.apply();
    }

    /**
     * Gets last selected tab and language from shared preferences
     */
    private void restoreCurrentState() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //Get last selected tab
        final int position = sharedPref.getInt(NewsPageConstants.KEY_NEWS_TYPE_TAB, NewsPageConstants.LOC_CODE_NATIONAL);
        //Get last selected language code
        int langCode = sharedPref.getInt(NewsPageConstants.KEY_LANGUAGE_CODE, NewsPageConstants.LANG_CODE_EN);
        //Initiate changes based on language
        setLanguageCode(langCode);
        invalidateOptionsMenu();
        changeTabTitles();
        //Set tab to last selected position
        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                mViewPager.setCurrentItem(position);
                notifyLanguageChanged();
            }
        });
    }

    @Override
    public void onFragmentReady() {

    }

    /*
     * Updates UI based on result of download of data.
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
