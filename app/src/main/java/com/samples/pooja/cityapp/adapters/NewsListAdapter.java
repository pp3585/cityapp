package com.samples.pooja.cityapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.samples.pooja.cityapp.R;
import com.samples.pooja.cityapp.webhandlers.NewsItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter to provide data to the news list in NewsListFragment
 */

public class NewsListAdapter extends ArrayAdapter<NewsItem> {

    private final Context context;
/*    private final String[] itemname;
    private final Integer[] imgid;*/

    public NewsListAdapter(Context context, List<NewsItem> newsItemList) {
        super(context, R.layout.news_list_item, newsItemList);
        this.context = context;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtTitle;
        ImageView img;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;
        // Get the data item for this position
        NewsItem newsItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.news_list_item, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.news_title);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.news_icon);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txtTitle.setText(newsItem.getTitle());
        Picasso.with(context)
                .load(newsItem.getImageSrc())
                .into(viewHolder.img);
        /*viewHolder.img.setImageURI();
        txtTitle.setText(itemname[position]);//get from list of news items
        imageView.setImageResource(imgid[position]);
        extratxt.setText("Description " + itemname[position]);*/
        /*viewHolder.info.setTag(position);*/
        return convertView;

    };
}
