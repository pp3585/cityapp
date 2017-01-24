package com.samples.pooja.cityapp.webhandlers;

import android.util.Xml;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * XML parser for News.
 */

class NewsParserXml extends NewsParser {
    // We don't use namespaces
    private static final String ns = null;
    private HashMap<Integer, String> mNewsLinksMap = new HashMap<>();
    private int mMapKey = 0;

    @Override
    public News parse(String result) throws JSONException, XmlPullParserException, IOException {
        //Instantiate parser
        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(new StringReader(result));
        parser.nextTag();
        //Read XML feed and return data model
        List<NewsItem> newsItemList = readFeed(parser);
        return new News(newsItemList, mNewsLinksMap);
    }
    private List<NewsItem> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<NewsItem> newsItemList = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, "rss");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if(name.equals("channel")){
                continue;
            }
            // Starts by looking for the item tag
            if (name.equals("item")) {
                newsItemList.add(readItem(parser));
            } else {
                skip(parser);
            }
        }
        return newsItemList;
    }

    // Parses the contents of an item. If it encounters a title, description, pubDate or link tag, hands them off
    // to their respective "read" methods for processing. Otherwise, skips the tag.
    private NewsItem readItem(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String title = null;
        String description = null;
        String link = null;
        String pubDate = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                title = readTitle(parser);
            } else if (name.equals("description")) {
                description = readDescription(parser);
            } else if (name.equals("link")) {
                link = readLink(parser);
            } else if (name.equals("pubDate")) {
                pubDate = readPubDate(parser);
            } else {
                skip(parser);
            }
        }
        return new NewsItem(title, description, null, pubDate, link);
    }

    // Processes title tags in the feed.
    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    // Processes link tags in the feed.
    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        /*String link = "";
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String tag = parser.getName();
        String relType = parser.getAttributeValue(null, "rel");
        if (tag.equals("link")) {
            if (relType.equals("alternate")){
                link = parser.getAttributeValue(null, "href");
                parser.nextTag();
            }
        }
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;*/
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String link = readText(parser);
        addToLinksMap(link);
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }

    //Add all links to hashmap
    private void addToLinksMap(String link) {
        mNewsLinksMap.put(mMapKey, link);
        mMapKey++;
    }

    // Processes description tags in the feed.
    private String readDescription(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "description");
        return description;
    }

    // Processes pubDate tags in the feed.
    private String readPubDate(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "pubDate");
        String pubDate = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "pubDate");
        return pubDate;
    }

    // For the tags title and description, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}