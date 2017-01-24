package com.samples.pooja.cityapp.listeners;

/**
 * Interface to communicate between NewsDetailFragment and its parent activity.
 * Needs to be implemented by parent activity.
 */

public interface NewsDetailFragmentChangeListener {

    void onNewsDetailsFetchData();
   // void onNewsItemSelected(int position, String sDetailUrl, int newsType);
    //void onNewsListRefresh(String sWebUrl, int newsType);
    void onFragmentSelected(int position);

}
