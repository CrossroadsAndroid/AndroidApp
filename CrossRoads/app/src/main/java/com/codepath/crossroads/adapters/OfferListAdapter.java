package com.codepath.crossroads.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.crossroads.R;
import com.codepath.crossroads.Utils;
import com.codepath.crossroads.models.ParseItem;
import com.codepath.crossroads.models.ParseOffer;
import com.parse.ParseQueryAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ar on 10/25/14.
 */
public class OfferListAdapter extends ParseQueryAdapter<ParseOffer> {
    public OfferListAdapter(Context context, QueryFactory<ParseOffer> queryFactory) {
        super(context, queryFactory);
    }

    @Override
    public View getItemView(ParseOffer object, View v, ViewGroup parent) {
        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.single_offer, parent, false);
        }

        List<ParseItem> items = object.getItems();
        final ImageView ivOfferImg = (ImageView) v.findViewById(R.id.ivOfferImg);
        TextView tvOfferDesc = (TextView) v.findViewById(R.id.tvOfferDesc);
        TextView tvOfferCreated = (TextView) v.findViewById(R.id.tvOfferCreated);

        int itemCount = object.getItems().size();
        tvOfferDesc.setText(String.valueOf(itemCount) + " items.");

        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, MMMM d 'at' hh:mm");
        tvOfferCreated.setText("Created: " + dateFormatter.format(new Date()));
        Utils.loadAnyItemPicLocal(items, 0, ivOfferImg);
        return v;
    }
}
