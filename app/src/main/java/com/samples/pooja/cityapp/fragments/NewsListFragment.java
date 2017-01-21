package com.samples.pooja.cityapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.samples.pooja.cityapp.R;
import com.samples.pooja.cityapp.activities.NewsListActivity;
import com.samples.pooja.cityapp.adapters.NewsListAdapter;
import com.samples.pooja.cityapp.utilities.NewsPageConstants;

/**
 * This fragment displays the news feed and is reused for every tab.
 */
public class NewsListFragment extends ListFragment {

    private static final String KEY_TAB_TYPE = "tab_position_number";
    private Dialog pDialog;
    //private String mNewsUrl;
    private static int mNewsLocation;
    //private static String mNewsLanguage;
    private NewsListFragment mNewsListFragment;
    private NewsListActivity mNewsListActivity;

    String[] itemname ={
            "How To Drive Safely When No One else Is.",
            "How To Drive Safely When No One else Is.",
            "Global",
            "How To Drive Safely When No One else Is.",
            "How To Drive Safely When No One else Is.",
            "How To Drive Safely When No One else Is.",
            "VLC Player",
            "How To Drive Safely When No One else Is."
    };

    Integer[] imgid={
            android.R.drawable.ic_menu_call,
            android.R.drawable.ic_menu_add,
            android.R.drawable.ic_menu_call,
            android.R.drawable.ic_menu_add,
            android.R.drawable.ic_menu_call,
            android.R.drawable.ic_menu_add,
            android.R.drawable.ic_menu_call,
            android.R.drawable.ic_menu_add,
    };

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
    public void onAttach(Context context) {
        super.onAttach(context);
        mNewsListActivity = (NewsListActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NewsListAdapter adapter = new NewsListAdapter(getActivity(), itemname, imgid);
        setListAdapter(adapter);
        //News type for the fragment is decided based on tabposition during initialization and
        //stored in the field mNewsLocation
        //Get current selected language
        int langCode = getCurrentLanguage();
        //Get URL based on news type and language
        String sNewsUrl = getNewsUrl(langCode, mNewsLocation);
        //Load data on creation
        mNewsListActivity.onNewsListStartDownload(sNewsUrl, mNewsLocation);//Is 2nd param reqd??No i think
    }

    private String getNewsUrl(int langCode, int mNewsLocation) {
        String sUrl;
        if(langCode == NewsPageConstants.LANG_CODE_EN) {
            if(mNewsLocation == NewsPageConstants.LOC_CODE_NATIONAL){
                sUrl = "national en url";
            } else {
                sUrl = "city en url";
            }
        } else {
            if(mNewsLocation == NewsPageConstants.LOC_CODE_NATIONAL){
                sUrl = "national hi url";
            } else {
                sUrl = "city hi url";
            }
        }
        return sUrl;
    }

    private int getCurrentLanguage() {
        int langCode;
        boolean langIsEn = mNewsListActivity.getEnLanguageState();
        if(langIsEn){
            langCode = NewsPageConstants.LANG_CODE_EN;
        } else {
            langCode = NewsPageConstants.LANG_CODE_HI;
        }
        return langCode;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //TO DO - Form url for detail page and find the current tab and pass to below line of code
        mNewsListActivity.onNewsItemSelected(position, null, NewsPageConstants.LOC_CODE_NATIONAL);
    }

    /*private class HttpAsyncTask extends AsyncTask<String, Void, Object> {
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
    }*/
}
