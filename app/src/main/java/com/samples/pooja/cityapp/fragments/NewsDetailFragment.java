package com.samples.pooja.cityapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samples.pooja.cityapp.R;
import com.samples.pooja.cityapp.activities.NewsDetailActivity;
import com.samples.pooja.cityapp.activities.NewsListActivity;

/**
 * Created by pooja on 1/20/2017.
 */
public class NewsDetailFragment extends Fragment{

    private NewsDetailActivity mNewsDetailActivity;

    public static Fragment newInstance(int position) {
        return new NewsDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_detail, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mNewsDetailActivity = (NewsDetailActivity) context;
    }

    public void loadData(){

        //Check if data is already loaded
        /*if(mNewsListAdapter == null || mNewsListAdapter.isEmpty()) {
            //Get current selected language
            int langCode = getCurrentLanguage();
            //Get news type - National or City
            int newsType = getNewsType(position);
            //Get URL based on news type and language
            String sNewsUrl = getNewsUrl(langCode, newsType);
            //Load data on creation
            mNewsListActivity.onNewsListStartDownload(sNewsUrl);
        }*/
    }
}
