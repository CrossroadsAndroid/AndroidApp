package com.codepath.crossroads.fragments;

import android.os.Bundle;

import com.codepath.crossroads.adapters.ReviewerOfferArrayAdapter;
import com.codepath.crossroads.models.ReviewOffer;

/**
 * Created by tonyleung on 10/18/14.
 */
public class NeedsReviewListFragment extends ReviewerOfferFragmentList {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        offers		= ReviewOffer.getNeedsReviewerOfferList();
        aOffers		= new ReviewerOfferArrayAdapter(getActivity(), offers);
    }
}
