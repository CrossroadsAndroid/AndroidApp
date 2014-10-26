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

    private static final String     ARGS_USER   = "USER";

    private User user;
    private TextView                tvFullName;
    private TextView                tvMobileNumber;
    private TextView                tvDistrict;

    // Creates a new fragment given an user
    public static UserInfoFragment newInstance(User user) {
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
        tvFullName      = (TextView) view.findViewById(R.id.tvFullName);
        tvMobileNumber  = (TextView) view.findViewById(R.id.tvMobileNumber);
        tvDistrict      = (TextView) view.findViewById(R.id.tvDistrict);

        tvFullName.setText(user.getFirstName() + " " + user.getLastName());
        tvMobileNumber.setText("Mobile: " + user.getMobile());
        tvDistrict.setText("District: " + user.getDistrict());

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get back arguments
        user = getArguments().getParcelable(ARGS_USER);
    }
}
