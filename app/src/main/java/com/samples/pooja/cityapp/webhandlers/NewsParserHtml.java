package com.samples.pooja.cityapp.webhandlers;

import android.util.Log;

import com.samples.pooja.cityapp.utilities.NewsPageConstants;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Html parser for News details
 */
public class NewsParserHtml extends NewsParser {

    //private String tempData = NewsPageConstants.TEMPDATA;

    @Override
    protected Object parse(String result) throws JSONException, XmlPullParserException, IOException {
        NewsDetail newsDetail = new NewsDetail();
        Document doc = Jsoup.parse(result);
        String title = doc.select("div.metatitle").attr("data-meta-title");
        String source = doc.select("span.fl.darker").text();
        String pubDate = doc.select("div.clearfix > span.timeformat").attr("data-time");
        String description = doc.select("div.article-txt > div.clearfix").text();
        String imgSrc = doc.select("span.articleimg > span > img").attr("data-src");
        String imgDesc = doc.select("span.storyimg-caption").text();
        newsDetail.setTitle(title);
        newsDetail.setDescription(description);
        newsDetail.setImgDesc(imgDesc);
        if(imgSrc == ""){
            newsDetail.setImgSrc(null);
        } else {
            //Below line is required to adjust size for mobile
            imgSrc = imgSrc.replace("400", "1200");
            newsDetail.setImgSrc(imgSrc);
        }
        newsDetail.setSource(source);
        newsDetail.setPubDate(pubDate);
        return newsDetail;
    }
}
