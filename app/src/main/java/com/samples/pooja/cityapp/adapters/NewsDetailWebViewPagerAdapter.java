package com.samples.pooja.cityapp.adapters;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

/**
 * Created by pooja on 1/25/2017.
 */

public class NewsDetailWebViewPagerAdapter extends PagerAdapter {

    private List<View> mListWebViews;
    private int mSelectedPosition;

    public NewsDetailWebViewPagerAdapter(List<View> listWebViews, int selectedPosition){
        mListWebViews = listWebViews;
        mSelectedPosition = selectedPosition;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        Log.d("k", "destroyItem");
        view.removeView(mListWebViews.get(position));
    }

    @Override
    public int getCount() {
        return mListWebViews.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        Log.d("k", "instantiateItem");
        view.addView(mListWebViews.get(position), 0);
        return mListWebViews.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        Log.d("k", "isViewFromObject");
        return view == (object);
    }
}
