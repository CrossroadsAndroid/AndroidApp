package com.codepath.crossroads.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.crossroads.R;
import com.codepath.crossroads.models.Item;

import java.util.List;

/**
 * Created by tonyleung on 10/19/14.
 */
public class ItemListArrayAdapter extends ArrayAdapter<Item>{

    // view cache
    private static class ViewHolder {
        TextView    tvItemDetail;
        TextView    tvItemCondition;
        ImageView   ivItemPhoto;
    }

    public ItemListArrayAdapter(Context context, List<Item> items) {
        super(context, 0, items);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // take the data source
        // get data item
        Item item						= getItem(position);

        // use view holder pattern
        ViewHolder viewHolder;
        // Check if an existing view is being reused,
        // if it is new, inflate the view and set viewHolder as tag
        if (convertView == null) {
            viewHolder			        = new ViewHolder();
            convertView 			    = LayoutInflater.from(getContext()).inflate(R.layout.offer_item, parent, false);
            viewHolder.tvItemDetail     = (TextView) convertView.findViewById(R.id.tvItemDetail);
            viewHolder.tvItemCondition  = (TextView) convertView.findViewById(R.id.tvItemCondition);
            viewHolder.ivItemPhoto      = (ImageView) convertView.findViewById(R.id.ivItemPhoto);
            convertView.setTag(viewHolder);
        }
        // retrieve view holder from tag
        else {
            viewHolder					= (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.tvItemDetail.setText(item.getDetails());
        viewHolder.tvItemCondition.setText(item.getCondition());
        viewHolder.ivItemPhoto.setImageBitmap(item.getPhoto());

        // Return the completed view to render on screen
        return convertView;
    }
}