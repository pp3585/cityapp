package com.samples.pooja.cityapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.samples.pooja.cityapp.R;
import com.samples.pooja.cityapp.datamodels.NewsItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter to provide data to the news list in NewsListFragment
 */

public class NewsListAdapter extends ArrayAdapter<NewsItem> {

    public NewsListAdapter(Context context, List<NewsItem> newsItemList) {
        super(context, R.layout.news_list_item, newsItemList);
    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtTitle;
        ImageView img;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder; // view lookup cache stored in tag
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            //Inflate the layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.news_list_item, parent, false);
            //Set up viewholder
            viewHolder = new ViewHolder();
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.news_title);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.news_icon);
            //Tag the view with the viewholder which stores the data
            convertView.setTag(viewHolder);
        } else {
            //Avoided using findViewById everytime by using the data already stored in viewholder
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Get the data item for this position
        NewsItem newsItem = getItem(position);
        if(newsItem != null) {
            // Get the TextView and ImageView from the ViewHolder
            // and set the text (item name) and image source
            viewHolder.txtTitle.setText(newsItem.getTitle());
            Picasso.with(getContext())
                    .load(newsItem.getImageSrc())
                    .placeholder(R.drawable.placeholder_small)
                    .error(R.drawable.error)
                    .resize(50, 50)
                    .centerCrop()
                    .into(viewHolder.img);
        }
        return convertView;
    }
}
