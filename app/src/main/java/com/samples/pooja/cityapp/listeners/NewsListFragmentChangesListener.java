package com.samples.pooja.cityapp.listeners;

/**
 * Interface to communicate between NewsListFragment and its parent activity.
 * Needs to be implemented by parent activity.
 */

public interface NewsListFragmentChangesListener {

    void onNewsListStartDownload(String sWebUrl);
    void onNewsItemSelected(int position, String sDetailUrl, int newsType);
    void onNewsListRefresh(String sWebUrl, int newsType);
    void onFragmentSelected(int position);
    void onFragmentReady();

}
