package com.samples.pooja.cityapp.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.samples.pooja.cityapp.R;
import com.samples.pooja.cityapp.adapters.NewsPagerAdapter;

/*
* This activity displays the news list with tabs for national and city news.
*/
public class NewsListActivity extends AppCompatActivity {

    private NewsPagerAdapter mNewsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        //TO DO : Toolbar contains the language toggle button
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the two
        // tabs of the activity.
        mNewsPagerAdapter = new NewsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mNewsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

}
