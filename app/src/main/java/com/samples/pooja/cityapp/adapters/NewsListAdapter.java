package com.samples.pooja.cityapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.samples.pooja.cityapp.R;

/**
 * Adapter to provide data to the news list in NewsListFragment
 */

public class NewsListAdapter extends ArrayAdapter {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;

    public NewsListAdapter(Activity context, String[] itemname, Integer[] imgid) {//change to accept list of newsitems
        super(context, R.layout.news_list_item, itemname);
        this.context = context;
        this.itemname = itemname;
        this.imgid = imgid;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.news_list_item, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);//use viewholder pattern
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname[position]);//get from list of news items
        imageView.setImageResource(imgid[position]);
        extratxt.setText("Description " + itemname[position]);
        return rowView;

    };
}
