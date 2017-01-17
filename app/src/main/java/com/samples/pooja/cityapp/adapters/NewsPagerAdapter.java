package com.samples.pooja.cityapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.samples.pooja.cityapp.fragments.NewsListFragment;
import com.samples.pooja.cityapp.utilities.NewsPageConstants;

/**
 * Returns a fragment for each of the tabs.
 */
public class NewsPagerAdapter extends FragmentPagerAdapter {

    public NewsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return NewsListFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        // Returns the number of fragments based on the number of tabs.
        return NewsPageConstants.TAB_NUM;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String sTabName;
        switch (position) {
            case 0:
            default:
                sTabName = NewsPageConstants.STR_TAB_1;
                break;
            case 1:
                sTabName = NewsPageConstants.STR_TAB_2;
        }
        return sTabName;
    }
}
