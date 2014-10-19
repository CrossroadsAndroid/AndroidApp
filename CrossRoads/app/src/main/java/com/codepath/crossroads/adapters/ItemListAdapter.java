package com.codepath.crossroads.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.crossroads.R;
import com.codepath.crossroads.Utils;
import com.codepath.crossroads.models.DonorItem;

import java.util.List;

/**
 * Created by ar on 10/18/14.
 */
public class ItemListAdapter extends ArrayAdapter<DonorItem> {

    public ItemListAdapter(Context context, List<DonorItem> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DonorItem item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_item, parent, false);
        }

        TextView tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);
        ImageView ivItem = (ImageView) convertView.findViewById(R.id.ivItem);

        tvDesc.setText(item.getDesc());
        ivItem.setImageBitmap(Utils.getImageForView(item.getLocalPath(), ivItem));

        return convertView;
    }
}
