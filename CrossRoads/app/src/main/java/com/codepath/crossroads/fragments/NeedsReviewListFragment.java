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
public class NeedsReviewListFragment extends ReviewerOfferFragmentList {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        new NeedsReviewListAsyncTask(getActivity()).execute();

        offers      = new ArrayList<ReviewOffer>();
        aOffers		= new ReviewerOfferArrayAdapter(getActivity(), offers);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // The types specified here are the input data type, the progress type, and the result type
    private class NeedsReviewListAsyncTask extends AsyncTask<Void, Void, ArrayList<ReviewOffer>> {

        private Context context;
        private ProgressDialog progressDialog;

        public NeedsReviewListAsyncTask (Context context) {
            this.context    = context;
        }

        @Override
        protected void onPreExecute() {
            try {
                // show progress
                progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_DARK);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("Loading offers...");
                progressDialog.show();

            } catch (final Throwable th) {
                //TODO
            }
        }

        protected void onPostExecute(ArrayList<ReviewOffer> offers) {
            progressDialog.dismiss();
            NeedsReviewListFragment.this.removeAll();
            NeedsReviewListFragment.this.addAll(offers);
        }

        protected ArrayList<ReviewOffer> doInBackground(Void... queries) {
            return ReviewOffer.getNeedsReviewerOfferList();
        }
    }


    public void refresh() {
        new NeedsReviewListAsyncTask(getActivity()).execute();
    }
}
