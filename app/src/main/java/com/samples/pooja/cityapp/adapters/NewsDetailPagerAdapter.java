package com.samples.pooja.cityapp.adapters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.samples.pooja.cityapp.R;
import com.samples.pooja.cityapp.fragments.NewsDetailFragment;
import com.samples.pooja.cityapp.fragments.NewsListFragment;
import com.samples.pooja.cityapp.utilities.NewsPageConstants;
import com.samples.pooja.cityapp.webhandlers.News;

/**
 * Pager adapter that provides data to the view pager in news detail activity
 */
public class NewsDetailPagerAdapter extends FragmentStatePagerAdapter {

    public NewsDetailPagerAdapter(FragmentManager fm, News news) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return NewsDetailFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        // Returns the number of fragments based on the number of news items.
        return 8;
    }

    /*@Override
    public CharSequence getPageTitle(int position) {
        String sTitle;
        switch (position) {
            case 0:
            default:
                sTitle = "Maharashtra finance department bans purchases of more than 50K";
                break;
            case 1:
                sTitle = "Bhiwandi boy is all-India rank 2 in CA final exam";
                break;
            case 2:
                sTitle = "Have you adopted 1994 wetlands map? : HC to Maharashtra";
                break;
            case 3:
                sTitle = "Have you adopted 1994 wetlands map? : HC to Maharashtra";
                break;
            case 4:
                sTitle = "Have you adopted 1994 wetlands map? : HC to Maharashtra";
                break;
            case 5:
                sTitle = "Have you adopted 1994 wetlands map? : HC to Maharashtra";
                break;
            case 6:
                sTitle = "Have you adopted 1994 wetlands map? : HC to Maharashtra";
                break;
            case 7:
                sTitle = "Have you adopted 1994 wetlands map? : HC to Maharashtra";
                break;
        }
        return sTitle;
    }*/
}
