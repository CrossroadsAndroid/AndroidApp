package com.codepath.crossroads.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.crossroads.adapters.ReviewerOfferArrayAdapter;
import com.codepath.crossroads.models.ReviewOffer;

import java.util.ArrayList;

/**
 * Created by tonyleung on 10/18/14.
 */
public class ReviewCompleteListFragment extends ReviewerOfferFragmentList {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        new ReviewCompleteListAsyncTask(getActivity()).execute();
        offers      = new ArrayList<ReviewOffer>();
        aOffers		= new ReviewerOfferArrayAdapter(getActivity(), offers);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // The types specified here are the input data type, the progress type, and the result type
    private class ReviewCompleteListAsyncTask extends AsyncTask<Void, Void, ArrayList<ReviewOffer>> {
        private Context context;
        private ProgressDialog progressDialog;

        public ReviewCompleteListAsyncTask (Context context) {
            this.context    = context;
        }

        @Override
        protected void onPreExecute() {
            try {
                progressDialog = ProgressDialog.show(context, "", "Loading", true);
            } catch (final Throwable th) {
                //TODO
            }
        }

        protected ArrayList<ReviewOffer> doInBackground(Void... queries) {
            return ReviewOffer.getOffersReviewsCompleted();
        }

        protected void onPostExecute(ArrayList<ReviewOffer> offers) {
            progressDialog.dismiss();
            ReviewCompleteListFragment.this.removeAll();
            ReviewCompleteListFragment.this.addAll(offers);
        }
    }
}