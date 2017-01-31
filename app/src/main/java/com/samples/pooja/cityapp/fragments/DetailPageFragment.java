package com.samples.pooja.cityapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.samples.pooja.cityapp.R;
import com.samples.pooja.cityapp.activities.DetailPageActivity;
import com.samples.pooja.cityapp.activities.NewsListActivity;
import com.samples.pooja.cityapp.utilities.NewsPageConstants;
import com.samples.pooja.cityapp.webhandlers.DownloadTask;
import com.samples.pooja.cityapp.webhandlers.News;
import com.samples.pooja.cityapp.webhandlers.NewsDetail;
import com.squareup.picasso.Picasso;

/**
 * This fragment contains the news detail page
 */

public class DetailPageFragment extends Fragment {

    private DetailPageActivity mDetailPageActivity;
    private String mUrl;

    public static Fragment newInstance(String url) {
        DetailPageFragment detailPageFragment = new DetailPageFragment();
        Bundle b = new Bundle();
        b.putString(NewsPageConstants.KEY_NEWS_DETAIL_URL, url);
        detailPageFragment.setArguments(b);
        return detailPageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_page, container, false);
        mUrl = getArguments().getString(NewsPageConstants.KEY_NEWS_DETAIL_URL);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDetailPageActivity = (DetailPageActivity) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void loadData(int position, String url) {
        mDetailPageActivity.onNewsDetailsStartDownload(url);
    }

    public void onDownloadError(Exception exception) {
        Toast.makeText(mDetailPageActivity, exception.getMessage(), Toast.LENGTH_LONG).show();
    }

    public void onDownloadComplete(NewsDetail resultObject) {
        //Set content to textview and imageview
        if(getView() != null) {
            ImageView imgView = (ImageView) getView().findViewById(R.id.iv_detail_page);
            TextView txtImgDesc = (TextView) getView().findViewById(R.id.tv_detail_img_desc);
            TextView txtTitle = (TextView) getView().findViewById(R.id.tv_detail_title);
            TextView txtSrcPubDate = (TextView) getView().findViewById(R.id.tv_detail_src_pub_date);
            TextView txtDesc = (TextView) getView().findViewById(R.id.tv_detail_desc);

            Picasso.with(getContext())
                    //.load("http://timesofindia.indiatimes.com/thumb/msid-56868385,width-1200,resizemode-4/56868385.jpg")
                    .load(resultObject.getImgSrc())
                    .fit()
                    .centerInside()
                    //.placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(imgView);
            txtImgDesc.setText(resultObject.getImgDesc());
            txtTitle.setText(resultObject.getTitle());
            txtSrcPubDate.setText(resultObject.getSource() + " | " + resultObject.getPubDate());
            txtDesc.setText(resultObject.getDescription());
        }
    }
}
