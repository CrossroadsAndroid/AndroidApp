package com.codepath.crossroads.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.crossroads.R;
import com.codepath.crossroads.models.ParseOffer;
import com.parse.ParseQueryAdapter;

/**
 * Created by ar on 10/25/14.
 */
public class OfferListAdapater extends ParseQueryAdapter<ParseOffer> {
    public OfferListAdapater(Context context, QueryFactory<ParseOffer> queryFactory) {
        super(context, queryFactory);
    }

    @Override
    public View getItemView(ParseOffer object, View v, ViewGroup parent) {
        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.single_offer, parent, false);
        }
        return v;
    }
}
