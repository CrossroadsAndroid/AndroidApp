package com.codepath.crossroads.activities.reviewer;

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

import com.codepath.crossroads.R;
import com.codepath.crossroads.fragments.NeedsReviewListFragment;
import com.codepath.crossroads.fragments.OffersUnderUserReviewListFragment;
import com.codepath.crossroads.fragments.ReviewCompleteListFragment;
import com.codepath.crossroads.fragments.ReviewerOfferFragmentList;
import com.codepath.crossroads.models.ReviewOffer;


public class ReviewerOfferListActivity extends FragmentActivity implements ReviewerOfferFragmentList.OnOfferSelectedListener{

    public static final String INTENT_OFFER = "OFFER";
    private final int ITEM_REQUEST_CODE	    = 20;

    ViewPager vpOffers;
    OfferPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewer_offer_list);

        vpOffers = (ViewPager) findViewById(R.id.vpOffers);
        setupTabs();
    }

    private void setupTabs() {
        adapterViewPager = new OfferPagerAdapter(getSupportFragmentManager());
        vpOffers.setAdapter(adapterViewPager);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);


        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

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
                .setText("Needs Review")
                .setTag("NeedsReviewListFragment")
                .setTabListener(tabListener);

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        ActionBar.Tab tab2 = actionBar
                .newTab()
                .setText("My Reviews")
                .setTag("OffersUnderUserReviewListFragment")
                .setTabListener(tabListener);
        actionBar.addTab(tab2);

        ActionBar.Tab tab3 = actionBar
                .newTab()
                .setText("Completed")
                .setTag("ReviewCompleteListFragment")
                .setTabListener(tabListener);
        actionBar.addTab(tab3);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.reviewer_donor_list, menu);
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

    /**
     * handle clicks on offer by opening an ReviewerOfferActivity with the given activity
     * @param offer - the activy selected to be opened
     */
    public void didClickOffer(ReviewOffer offer) {
        if (null == offer) {
            return;
        }

        Intent intent	= new Intent(this, ReviewerOfferActivity.class);
        intent.putExtra(INTENT_OFFER, offer);
        startActivityForResult(intent, ITEM_REQUEST_CODE);
    }

    /**
     * check the state has changed, then assign yourself as the reviewer
     * check if all the items to see the state if it is review complete. if it is, assign the state
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // make sure that it is the same request code as the
        if (resultCode == RESULT_OK && requestCode == ITEM_REQUEST_CODE) {

        }
    }

    public static class OfferPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;
        public NeedsReviewListFragment              fNeedsReviewList;
        public OffersUnderUserReviewListFragment    fOffersUnderUserReviewList;
        public ReviewCompleteListFragment           fCompletedList;


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
                    if (fNeedsReviewList == null) {
                        fNeedsReviewList = new NeedsReviewListFragment();
                    }
                    return fNeedsReviewList;
                case 1:
                    if (fOffersUnderUserReviewList == null) {
                        fOffersUnderUserReviewList = new OffersUnderUserReviewListFragment();
                    }
                    return fOffersUnderUserReviewList;
                case 2:
                    if (fCompletedList == null) {
                        fCompletedList = new ReviewCompleteListFragment();
                    }
                    return fCompletedList;
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Needs Review";
                case 1:
                    return "My Reviews";
                case 2:
                    return "Completed";
                default:
                    return null;
            }
        }
    }

}
