package com.samples.pooja.cityapp.fragments;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.samples.pooja.cityapp.R;
import com.samples.pooja.cityapp.utilities.NewsPageConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This fragment displays the news feed and is reused for every tab.
 */
public class NewsListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private static final String KEY_TAB_TYPE = "tab_position_number";
    private Dialog pDialog;
    //private static String sNewsUrl;
    private static int mNewsLocation;
    //private static String mNewsLanguage;
    private NewsListFragment mNewsListFragment;

    public NewsListFragment() {

    }

    /**
     * Returns a new instance of this fragment for the given tab position
     * number.
     */
    public static NewsListFragment newInstance(int tabPosition) {
        switch(tabPosition) {
            case 0:
                mNewsLocation = NewsPageConstants.LOC_CODE_NATIONAL;
            case 1:
                mNewsLocation = NewsPageConstants.LOC_CODE_CITY;
        }
        return new NewsListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        String[] strings = {"A", "B", "C", "D"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strings);
        setListAdapter(arrayAdapter);
        getListView().setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, Object> {
        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection httpURLConnection = null;
            String jsonReply;
            Boolean success = false;
            try {
                URL url = new URL(NewsPageConstants.NATL_EN_URL);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("Content-length", "0");
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setAllowUserInteraction(false);
                httpURLConnection.setConnectTimeout(1000);
                httpURLConnection.setReadTimeout(1000);
                httpURLConnection.connect();
                int status = httpURLConnection.getResponseCode();

                switch (status) {
                    case 200:
                    case 201:
                        success = true;
                        InputStream response = httpURLConnection.getInputStream();
                        jsonReply = convertStreamToString(response);
                        return jsonReply;
                }

            } catch (MalformedURLException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (httpURLConnection != null) {
                    try {
                        httpURLConnection.disconnect();
                    } catch (Exception ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            return null;

        }

        private String convertStreamToString(InputStream is) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }
}
