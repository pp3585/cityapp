package com.samples.pooja.cityapp.listeners;

/**
 * Created by pooja on 1/20/2017.
 */

public interface NewsListChangesListener {

    void onNewsListStartDownload(String sWebUrl, int newsType);
    void onNewsItemSelected(int position, String sDetailUrl, int newsType);
    void onNewsListRefresh(String sWebUrl, int newsType);
}
