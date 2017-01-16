package com.samples.pooja.cityapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.samples.pooja.cityapp.fragments.NewsListFragment;

/**
 * Returns a fragment corresponding to one of the tabs.
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
        // Show 4 total pages.
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "NATIONAL NEWS(EN)";
            case 1:
                return "NATIONAL NEWS(HI)";
            case 2:
                return "CITY NEWS(EN)";
            case 3:
                return "CITY NEWS(HI)";
        }
        return null;
    }
}
