package com.codepath.crossroads.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.codepath.crossroads.R;
import com.codepath.crossroads.Utils;
import com.codepath.crossroads.models.ParseItem;
import com.codepath.crossroads.models.ParseOffer;
import com.parse.ParseQueryAdapter;

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

        Utils.loadAnyItemPicLocal(items, 0, ivOfferImg);
        return v;
    }
}
