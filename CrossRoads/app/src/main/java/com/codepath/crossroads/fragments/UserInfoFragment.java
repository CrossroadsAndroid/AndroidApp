package com.codepath.crossroads.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.crossroads.R;
import com.codepath.crossroads.models.ReviewUser;

/**
 * Created by tonyleung on 10/12/14.
 */
public class UserInfoFragment extends Fragment {

    private ReviewUser reviewUser;
    private TextView tvFullName;
    private TextView tvMobileNumber;
    private TextView tvDistrict;
    private TextView tvNeighborhood;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view       = inflater.inflate(R.layout.fragment_user_info, container, false);
        tvFullName      = (TextView) view.findViewById(R.id.tvfullName);
        tvMobileNumber  = (TextView) view.findViewById(R.id.tvMobileNumber);
        tvDistrict      = (TextView) view.findViewById(R.id.tvDistrict);
        tvNeighborhood  = (TextView) view.findViewById(R.id.tvNeighborhood);

        return view;
    }

    public ReviewUser getReviewUser() {
        return reviewUser;
    }

    public void setReviewUser(ReviewUser reviewUser) {
        this.reviewUser = reviewUser;

        tvFullName.setText(reviewUser.getFirstName() + " " + reviewUser.getLastName());
        tvMobileNumber.setText(reviewUser.getMobile());
        tvDistrict.setText(reviewUser.getDistrict());
        tvNeighborhood.setText(reviewUser.getNeighborhood());
    }
}
