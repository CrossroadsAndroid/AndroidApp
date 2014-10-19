package com.codepath.crossroads.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.crossroads.adapters.ReviewerOfferArrayAdapter;
import com.codepath.crossroads.models.ReviewOffer;

/**
 * Created by tonyleung on 10/18/14.
 */
public class ReviewCompleteListFragment extends ReviewerOfferFragmentList {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        offers		= ReviewOffer.getOffersReviewsCompleted();
//        aOffers		= new ReviewerOfferArrayAdapter(getActivity(), offers);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        offers		= ReviewOffer.getOffersReviewsCompleted();
        aOffers		= new ReviewerOfferArrayAdapter(getActivity(), offers);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}