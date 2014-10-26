package com.codepath.crossroads.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.codepath.crossroads.R;

/**
 * Created by ar on 10/26/14.
 */
public class ItemConditionAdapter extends BaseAdapter {

    Context mContext;
    private String[] conditions = {
            "New",
            "Lightly Used",
            "Heavily Used",
            "Broken"
    };

    public ItemConditionAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return conditions.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.single_condition, viewGroup, false);
        }

        TextView tvSingleCondition = (TextView) view.findViewById(R.id.tvSingleCondition);
        tvSingleCondition.setText(conditions[pos]);
        return view;
    }

}
