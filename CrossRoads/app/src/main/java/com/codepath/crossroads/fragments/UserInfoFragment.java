package com.codepath.crossroads.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.crossroads.R;
import com.codepath.crossroads.models.User;

/**
 * Created by tonyleung on 10/12/14.
 */
public class UserInfoFragment extends Fragment {

    private User user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;

        tvFullName.setText(user.getFirstName() + " " + user.getLastName());
        tvMobileNumber.setText(user.getMobile());
        tvDistrict.setText(user.getDistrict());
        tvNeighborhood.setText(user.getNeighborhood());
    }
}
