package com.samples.pooja.cityapp.webhandlers;

import org.json.JSONException;

/**
 * Created by pooja on 1/22/2017.
 */

public abstract class NewsParser {

    protected abstract Object parse(String result) throws JSONException;
}
