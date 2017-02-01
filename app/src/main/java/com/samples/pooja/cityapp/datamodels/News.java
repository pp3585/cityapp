package com.samples.pooja.cityapp.datamodels;

import java.util.HashMap;
import java.util.List;

/**
 * The consolidated News object contains the list of newsitems for the news list
 * and a hashmap of position and news links to display in news detail page.
 */

public class News {

    private List<NewsItem> mNewsListItems;
    private HashMap<Integer, String> mNewsLinksMap;

    public News(){

    }

    public News(List<NewsItem> newsItemList, HashMap<Integer, String> newsLinksMap){
        mNewsListItems = newsItemList;
        mNewsLinksMap = newsLinksMap;
    }
    public List<NewsItem> getNewsListItems() {
        return mNewsListItems;
    }

    public void setNewsListItems(List<NewsItem> mNewsListItems) {
        this.mNewsListItems = mNewsListItems;
    }

    public HashMap<Integer, String> getNewsLinksMap() {
        return mNewsLinksMap;
    }

    public void setNewsLinksMap(HashMap<Integer, String> mNewsLinksMap) {
        this.mNewsLinksMap = mNewsLinksMap;
    }

}
