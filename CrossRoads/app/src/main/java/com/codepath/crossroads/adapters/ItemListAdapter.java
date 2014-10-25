package com.codepath.crossroads.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.crossroads.R;
import com.codepath.crossroads.models.ParseItem;
import com.parse.ParseQueryAdapter;

/**
 * Created by ar on 10/25/14.
 */
public class ItemListAdapter extends ParseQueryAdapter<ParseItem> {
    public ItemListAdapter(Context context, QueryFactory<ParseItem> queryFactory) {
        super(context, queryFactory);
    }

    @Override
    public View getItemView(ParseItem object, View v, ViewGroup parent) {
        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.single_item, parent, false);
        }


        TextView tvDesc = (TextView) v.findViewById(R.id.tvDesc);
        ImageView ivItem = (ImageView) v.findViewById(R.id.ivItem);
        tvDesc.setText(object.getDetails());
        // ivItem.setImageBitmap(Utils.getImageForView(item.getLocalPath(), ivItem));
        return super.getItemView(object, v, parent);
    }
}
