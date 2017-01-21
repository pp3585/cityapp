package com.samples.pooja.cityapp.webhandlers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser class for news
 */

public class NewsParser {

    private List<NewsItem> mNewsItemList = new ArrayList<>();

    public List<NewsItem> parseJsonNewsList(String result) throws JSONException {
        JSONObject resultObject = new JSONObject(result);
        JSONObject channelObj = resultObject.getJSONObject("channel");
        JSONArray newsItemsArr = channelObj.getJSONArray("item");
        JSONArray jsonNewsItems;

        for (int i = 0; i < newsItemsArr.length(); i++) {
            NewsItem newsItem = new NewsItem();
            JSONObject jsonNewsItem = newsItemsArr.getJSONObject(i);
            newsItem.setTitle(jsonNewsItem.getString("title"));
            newsItem.setDescription(jsonNewsItem.getString("description"));
            newsItem.setLink(jsonNewsItem.getString("link"));
            newsItem.setDate(jsonNewsItem.getString("pubDate"));
            mNewsItemList.add(newsItem);
        }

        return mNewsItemList;
    }
}
