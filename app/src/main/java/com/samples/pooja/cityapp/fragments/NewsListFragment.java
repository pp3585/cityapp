package com.samples.pooja.cityapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.samples.pooja.cityapp.R;
import com.samples.pooja.cityapp.activities.NewsListActivity;
import com.samples.pooja.cityapp.adapters.NewsListAdapter;
import com.samples.pooja.cityapp.utilities.NewsPageConstants;
import com.samples.pooja.cityapp.webhandlers.NewsItem;

import java.util.List;

/**
 * This fragment displays the news feed and is reused for every tab.
 */
public class NewsListFragment extends ListFragment {

    private static final String KEY_TAB_TYPE = "tab_position_number";
    private Dialog pDialog;
    private int mNewsLocation;
    private NewsListAdapter mNewsListAdapter;
    private NewsListActivity mNewsListActivity;

    String[] itemname ={
            "How To Drive Safely When No One else Is.",
            "How To Drive Safely When No One else Is.",
            "Global",
            "How To Drive Safely When No One else Is.",
            "How To Drive Safely When No One else Is.",
            "How To Drive Safely When No One else Is.",
            "VLC Player",
            "How To Drive Safely When No One else Is."
    };

    Integer[] imgid={
            android.R.drawable.ic_menu_call,
            android.R.drawable.ic_menu_add,
            android.R.drawable.ic_menu_call,
            android.R.drawable.ic_menu_add,
            android.R.drawable.ic_menu_call,
            android.R.drawable.ic_menu_add,
            android.R.drawable.ic_menu_call,
            android.R.drawable.ic_menu_add,
    };

    public NewsListFragment() {

    }

    /**
     * Returns a new instance of this fragment for the given tab position
     * number.
     */
    public static NewsListFragment newInstance(int tabPosition) {
        return new NewsListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mNewsListActivity = (NewsListActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    public void loadData(int position){
        //Check if data is already loaded
        if(mNewsListAdapter == null || mNewsListAdapter.isEmpty()) {
            //Get current selected language
            int langCode = getCurrentLanguage();
            //Get news type - National or City
            int newsType = getNewsType(position);
            //Get URL based on news type and language
            String sNewsUrl = getNewsUrl(langCode, newsType);
            //Load data on creation
            mNewsListActivity.onNewsListStartDownload(sNewsUrl);
        }
    }

    private String getNewsUrl(int langCode, int mNewsLocation) {
        String sUrl;
        if(langCode == NewsPageConstants.LANG_CODE_EN) {
            if(mNewsLocation == NewsPageConstants.LOC_CODE_NATIONAL){
                sUrl = NewsPageConstants.NATL_EN_URL;
            } else {
                sUrl = NewsPageConstants.CITY_EN_URL;
            }
        } else {
            if(mNewsLocation == NewsPageConstants.LOC_CODE_NATIONAL){
                sUrl = NewsPageConstants.NATL_HI_URL;
            } else {
                sUrl = NewsPageConstants.CITY_HI_URL;
            }
        }
        return sUrl;
    }

    private int getNewsType(int position){
        int newsType;
        switch(position) {
            case 0:
            default:
                newsType = NewsPageConstants.LOC_CODE_NATIONAL;
                break;
            case 1:
                newsType = NewsPageConstants.LOC_CODE_CITY;
                break;
        }
        return newsType;
    }

    private int getCurrentLanguage() {
        int langCode;
        boolean langIsEn = mNewsListActivity.getEnLanguageState();
        if(langIsEn){
            langCode = NewsPageConstants.LANG_CODE_EN;
        } else {
            langCode = NewsPageConstants.LANG_CODE_HI;
        }
        return langCode;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //TO DO - Form url for detail page and find the current tab and pass to below line of code
        mNewsListActivity.onNewsItemSelected(position, null, NewsPageConstants.LOC_CODE_NATIONAL);
    }

    public void onDownloadComplete(List<NewsItem> result) {
        mNewsListAdapter = new NewsListAdapter(mNewsListActivity, result);
        setListAdapter(mNewsListAdapter);
    }

    public void onDownloadError(Exception exception) {
        //Create error text based on exceotion
        //Load empty view text with the error text
        Toast.makeText(mNewsListActivity, exception.getMessage(), Toast.LENGTH_LONG).show();//Need to make better texts and dialogs
    }
}
