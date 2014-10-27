package com.codepath.crossroads.activities.donors;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.codepath.crossroads.Constants;
import com.codepath.crossroads.R;
import com.codepath.crossroads.fragments.OfferListFragment;
import com.codepath.crossroads.fragments.PendingOfferListFragment;
import com.codepath.crossroads.fragments.SubmittedOfferListFragment;

public class DonorOfferListActivity extends FragmentActivity {

    ViewPager vpOffers;
    OfferPagerAdapter adapterViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_offer_list);
        vpOffers = (ViewPager) findViewById(R.id.vpOffers);
        setupTabs();
    }

    private void setupTabs() {
        adapterViewPager = new OfferPagerAdapter(getSupportFragmentManager());
        vpOffers.setAdapter(adapterViewPager);

        /*
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);


        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                vpOffers.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };


        ActionBar.Tab tab1 = actionBar
                .newTab()
                .setText("Pending")
                .setTag("PendingOfferListFragment")
                .setTabListener(tabListener);

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        ActionBar.Tab tab2 = actionBar
                .newTab()
                .setText("Submitted")
                .setTag("SubmittedOfferListFragment")
                .setTabListener(tabListener);
        actionBar.addTab(tab2);
        */

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.donor_offers, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addOffer(View v) {
        startActivityForResult(new Intent(this, DonorOfferActivity.class), Constants.EDIT_OFFER_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.EDIT_OFFER_CODE) {
                OfferListFragment fragment = adapterViewPager.getCurrentShown();
                fragment.update();
            }

        }
    }

    public static class OfferPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        OfferListFragment currentShown;


        public OfferPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    currentShown = new PendingOfferListFragment();
                    return currentShown;
                case 1:
                    currentShown = new SubmittedOfferListFragment();
                    return currentShown;
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Pending";
                case 1:
                    return "Submitted";
                default:
                    return null;
            }
        }

        public OfferListFragment getCurrentShown() {
            return currentShown;
        }
    }
}
