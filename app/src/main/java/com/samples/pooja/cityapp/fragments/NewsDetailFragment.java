package com.samples.pooja.cityapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.samples.pooja.cityapp.R;
import com.samples.pooja.cityapp.activities.NewsDetailActivity;
import com.samples.pooja.cityapp.activities.NewsListActivity;
import com.samples.pooja.cityapp.utilities.NewsPageConstants;

/**
 * This fragment contains the news detail page which shows the web content in a webview
 */
public class NewsDetailFragment extends Fragment{

    private NewsDetailActivity mNewsDetailActivity;
    private String mUrl;

    public static Fragment newInstance(String url) {
        NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
        Bundle b = new Bundle();
        b.putString(NewsPageConstants.KEY_NEWS_DETAIL_URL, url);
        newsDetailFragment.setArguments(b);
        return newsDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        mUrl = getArguments().getString(NewsPageConstants.KEY_NEWS_DETAIL_URL);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mNewsDetailActivity = (NewsDetailActivity) context;
        //mNewsDetailActivity.onFragmentReady();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mNewsDetailActivity.onFragmentReady();
        loadWebView();
    }

    public void loadWebView(){
        final Activity activity = getActivity();
        final ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.wv_detail_progress);
        progressBar.setScaleY(4f);
        //WebView webView = new WebView(mNewsDetailActivity);
        WebView webView = (WebView) getView().findViewById(R.id.wv_news_detail);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                activity.setProgress(newProgress * 100);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Toast.makeText(activity, "Cannot load page", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
        webView.loadUrl(mUrl);
    }
}
