package com.samples.pooja.cityapp.webhandlers;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Base class for parsing news. Based on data type, different parser classes inherit from base class.
 */

abstract class NewsParser {

    protected abstract Object parse(String result) throws JSONException, XmlPullParserException, IOException;
}
