package com.samples.pooja.cityapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.samples.pooja.cityapp.R;
import com.samples.pooja.cityapp.activities.NewsListActivity;
import com.samples.pooja.cityapp.adapters.NewsListAdapter;
import com.samples.pooja.cityapp.utilities.NewsPageConstants;
import com.samples.pooja.cityapp.datamodels.News;

/**
 * This fragment displays the news feed and is reused for every tab.
 */
public class NewsListFragment extends ListFragment {

    private static final String KEY_TAB_TYPE = "tab_position_number";
    private Dialog pDialog;
    private NewsListAdapter mNewsListAdapter;
    private NewsListActivity mNewsListActivity;
    private boolean mForceRefresh = false;

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
        mNewsListActivity.onFragmentReady();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    public void loadData(int position, int langCode){
        //Get news type - National or City
        int newsType = getNewsType(position);
        //Get URL based on news type and language
        String sNewsUrl = getNewsUrl(langCode, newsType);
        //Load data on creation
        mNewsListActivity.onNewsListStartDownload(sNewsUrl);
    }

    /**
     * Actions to do when language is changed
     * @param position tab position to determine news type
     * @param langCode code for current language
     */
    public void onLanguageChanged(int position, int langCode){
        mForceRefresh = true;
        loadData(position, langCode);
    }

    /**
     * Get the url for the API
     * @param langCode code for current language
     * @param mNewsLocation code for news type national or city
     * @return url for api call
     */
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

    /**
     * Get the news type national or city
     * @param position current selected tab position
     * @return code representing national or city news
     */
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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mNewsListActivity.onNewsItemSelected(position, null, NewsPageConstants.LOC_CODE_NATIONAL);
    }

    /**
     * Called when download of API is completed by activity and result is success
     * @param result data model containing news
     */
    public void onDownloadComplete(News result) {
        mForceRefresh = false;
        mNewsListAdapter = new NewsListAdapter(mNewsListActivity, result.getNewsListItems());
        setListAdapter(mNewsListAdapter);
    }

    /**
     * Called when download of data is complete and has error
     * @param exception exception to display to user
     */
    public void onDownloadError(Exception exception) {
        mForceRefresh = false;
        //Create error text based on exception
        //Load empty view text with the error text
        Toast.makeText(mNewsListActivity, exception.getMessage(), Toast.LENGTH_LONG).show();//Need to make better texts and dialogs
        if(getView() != null) {
            TextView emptyTxtView = (TextView) getView().findViewById(android.R.id.empty);
            emptyTxtView.setText(exception.getMessage());
        }
    }
}
