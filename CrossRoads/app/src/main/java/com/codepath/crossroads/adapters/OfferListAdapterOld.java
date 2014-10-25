package com.codepath.crossroads.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.codepath.crossroads.R;
import com.codepath.crossroads.models.DonorOffer;

import java.util.List;

/**
 * Created by ar on 10/19/14.
 */
public class OfferListAdapterOld extends ArrayAdapter<DonorOffer> {

    public OfferListAdapterOld(Context context, List<DonorOffer> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DonorOffer offer = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_offer, parent, false);
        }
        return convertView;
    }
}
