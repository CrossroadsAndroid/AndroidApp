package com.codepath.crossroads.fragments;

import com.codepath.crossroads.adapters.ReviewerOfferArrayAdapter;
import com.codepath.crossroads.models.Offer;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.codepath.crossroads.R;

import java.util.ArrayList;


public class ReviewerOfferFragmentList extends Fragment {


    protected ArrayAdapter<Offer>	aOffers;
    protected ListView lvTweets;
    protected ArrayList<Offer> offers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        offers		= Offer.getNeedsReviewerOfferList();
        aOffers		= new ReviewerOfferArrayAdapter(getActivity(), offers);
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout
        View view	= inflater.inflate(R.layout.fragment_reviewer_offer_list, container, false);
        // assign view references
        lvTweets	= (ListView) view.findViewById(R.id.lvOffers);

        lvTweets.setAdapter(aOffers);
        // return view
        return view;
    }

    /**
     * add the given array list of tweets to the array adapter
     * @param offers
     */
    public void addAll(ArrayList<Offer> offers) {
        aOffers.addAll(offers);
    }

    /**
     * remove all the tweets
     */
    public void removeAll() {
        aOffers.clear();
        aOffers.notifyDataSetChanged();
    }

}
