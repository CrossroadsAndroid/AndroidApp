package com.codepath.crossroads.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.crossroads.Constants;

public class SubmittedOfferListFragment extends OfferListFragment {
    public SubmittedOfferListFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFilter(Constants.OFFER_STATE_SUBMITTED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        lvOffers.setClickable(false);
        return v;
    }

    @Override
    protected boolean canEdit() {
        return false;
    }
}
