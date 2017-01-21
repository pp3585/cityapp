package com.samples.pooja.cityapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.samples.pooja.cityapp.listeners.NewsListChangesListener;

/*
* This activity displays the news list with tabs for national and city news.
*/
public class NewsListActivity extends AppCompatActivity implements NewsListChangesListener {

    private NewsPagerAdapter mNewsPagerAdapter;
    private ViewPager mViewPager;

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

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
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
    public void onNewsListStartDownload(String sWebUrl, int newsType) {
        //Call API to load data
        //Pass result to appropriate fragment
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem());
        if(mViewPager.getCurrentItem() == 0 && page != null){
            //Fragment national - call method to pass result data
        } else if(page != null){
            //Fragment city - call method to pass result data
        }
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

}
