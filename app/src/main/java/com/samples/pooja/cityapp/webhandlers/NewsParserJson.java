package com.samples.pooja.cityapp.webhandlers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Parser class for news
 */

class NewsParserJson extends NewsParser {

    private News mNews = new News();
    private List<NewsItem> mNewsItemList = new ArrayList<>();
    private HashMap<Integer, String> mNewsLinksMap = new HashMap<>();

    public News parse(String result) throws JSONException {
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
            mNewsLinksMap.put(i, newsItem.getLink());
            newsItem.setDate(jsonNewsItem.getString("pubDate"));
            mNewsItemList.add(newsItem);
        }

        mNews.setNewsListItems(mNewsItemList);
        mNews.setNewsLinksMap(mNewsLinksMap);
        return mNews;
    }
}
