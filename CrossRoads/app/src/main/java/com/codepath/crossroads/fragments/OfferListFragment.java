package com.codepath.crossroads.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.crossroads.R;
import com.codepath.crossroads.adapters.OfferListAdapter;
import com.codepath.crossroads.models.DonorOffer;
import com.codepath.crossroads.models.ParseOffer;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;


public class OfferListFragment extends Fragment {

    protected ArrayList<DonorOffer> offers;
    ListView lvOffers;
    OfferListAdapter aOffers;

    public OfferListFragment() {
        // Required empty public constructor
    }

    public static OfferListFragment newInstance(String param1, String param2) {
        OfferListFragment fragment = new OfferListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_offer_list, container, false);
        lvOffers = (ListView) v.findViewById(R.id.lvOffers);

        // Set up the Parse query to use in the adapter
        ParseQueryAdapter.QueryFactory<ParseOffer> factory = new ParseQueryAdapter.QueryFactory<ParseOffer>() {
            public ParseQuery<ParseOffer> create() {
                ParseQuery<ParseOffer> query = ParseOffer.getQuery();
                query.orderByDescending("createdAt");
                query.fromLocalDatastore();
                return query;
            }
        };

        // FIXME empty offer view
        aOffers = new OfferListAdapter(getActivity(), factory);
        lvOffers.setAdapter(aOffers);
        return v;
    }
}
