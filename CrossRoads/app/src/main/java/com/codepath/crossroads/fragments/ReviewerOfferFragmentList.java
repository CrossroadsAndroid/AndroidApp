package com.codepath.crossroads.fragments;

import com.codepath.crossroads.adapters.ReviewerOfferArrayAdapter;
import com.codepath.crossroads.models.ReviewOffer;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.codepath.crossroads.R;

import java.util.ArrayList;


public class ReviewerOfferFragmentList extends Fragment {

    /**
     * defining an offer selected listener
     */
    private OnOfferSelectedListener listener;

    // must handle on clickOffer
    public interface OnOfferSelectedListener {
        public void didClickOffer(ReviewOffer offer);
    }


    protected ArrayAdapter<ReviewOffer>	aOffers;
    protected ListView lvOffers;
    protected ArrayList<ReviewOffer> offers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        offers		= ReviewOffer.getNeedsReviewerOfferList();
        aOffers		= new ReviewerOfferArrayAdapter(getActivity(), offers);
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);

        if (activity instanceof OnOfferSelectedListener) {
            listener = (OnOfferSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement ReviewerOfferFragmentList.OnOfferSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout
        View view	= inflater.inflate(R.layout.fragment_reviewer_offer_list, container, false);
        // assign view references
        lvOffers	= (ListView) view.findViewById(R.id.lvOffers);

        lvOffers.setAdapter(aOffers);

        lvOffers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.didClickOffer(offers.get(i));
            }
        });

        // return view
        return view;
    }

    /**
     * add the given array list of tweets to the array adapter
     * @param offers
     */
    public void addAll(ArrayList<ReviewOffer> offers) {
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
