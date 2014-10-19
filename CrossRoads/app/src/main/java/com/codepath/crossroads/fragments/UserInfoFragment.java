package com.codepath.crossroads.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.crossroads.R;
import com.codepath.crossroads.adapters.ItemListArrayAdapter;
import com.codepath.crossroads.models.ReviewItem;
import com.codepath.crossroads.models.ReviewUser;

import java.util.ArrayList;

/**
 * Created by tonyleung on 10/12/14.
 */
public class UserInfoFragment extends Fragment {

    private static final String     ARGS_USER   = "USER";

    private ReviewUser              reviewUser;
    private TextView                tvFullName;
    private TextView                tvMobileNumber;
    private TextView                tvDistrict;
    private TextView                tvNeighborhood;

    // Creates a new fragment given an user
    public static UserInfoFragment newInstance(ReviewUser user) {
        UserInfoFragment userInfoFragment = new UserInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_USER, user);
        userInfoFragment.setArguments(args);
        return userInfoFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view       = inflater.inflate(R.layout.fragment_user_info, container, false);
        tvFullName      = (TextView) view.findViewById(R.id.tvfullName);
        tvMobileNumber  = (TextView) view.findViewById(R.id.tvMobileNumber);
        tvDistrict      = (TextView) view.findViewById(R.id.tvDistrict);
        tvNeighborhood  = (TextView) view.findViewById(R.id.tvNeighborhood);

        tvFullName.setText(reviewUser.getFirstName() + " " + reviewUser.getLastName());
        tvMobileNumber.setText(reviewUser.getMobile());
        tvDistrict.setText(reviewUser.getDistrict());
        tvNeighborhood.setText(reviewUser.getNeighborhood());

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get back arguments
        reviewUser  = getArguments().getParcelable(ARGS_USER);
    }


//    public ReviewUser getReviewUser() {
//        return reviewUser;
//    }
//
//    public void setReviewUser(ReviewUser reviewUser) {
//        this.reviewUser = reviewUser;
//
//        tvFullName.setText(reviewUser.getFirstName() + " " + reviewUser.getLastName());
//        tvMobileNumber.setText(reviewUser.getMobile());
//        tvDistrict.setText(reviewUser.getDistrict());
//        tvNeighborhood.setText(reviewUser.getNeighborhood());
//    }
}
