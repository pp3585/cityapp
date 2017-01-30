package com.samples.pooja.cityapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.samples.pooja.cityapp.fragments.DetailPageFragment;

import java.util.HashMap;

/**
 * Pager adapter that provides data to the view pager in news detail activity
 */
public class NewsDetailPagerAdapter extends FragmentStatePagerAdapter {

    private final HashMap<Integer, String> mNewsUrlMap;
    private int mSelectedPosition;

    public NewsDetailPagerAdapter(FragmentManager fm, HashMap<Integer, String> newsHashMap, int selectedPosition) {
        super(fm);
        mNewsUrlMap = newsHashMap;
        mSelectedPosition = selectedPosition;
    }

    @Override
    public Fragment getItem(int position) {
        return DetailPageFragment.newInstance(mNewsUrlMap.get(position));
    }

    @Override
    public int getCount() {
        // Returns the number of fragments based on the number of news items.
        return mNewsUrlMap.size();
    }
}
