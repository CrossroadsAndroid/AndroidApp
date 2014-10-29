package com.codepath.crossroads.activities.reviewer;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.crossroads.Listeners.FragmentTabListener;
import com.codepath.crossroads.R;
import com.codepath.crossroads.fragments.NeedsReviewListFragment;
import com.codepath.crossroads.fragments.OffersUnderUserReviewListFragment;
import com.codepath.crossroads.fragments.ReviewCompleteListFragment;
import com.codepath.crossroads.fragments.ReviewerOfferFragmentList;
import com.codepath.crossroads.models.ReviewOffer;


public class ReviewerOfferListActivity extends FragmentActivity implements ReviewerOfferFragmentList.OnOfferSelectedListener{

    public static final String INTENT_OFFER = "OFFER";
    private final int ITEM_REQUEST_CODE	    = 20;
//    private NeedsReviewListFragment              fNeedsReviewList;
//    private OffersUnderUserReviewListFragment    fOffersUnderUserReviewList;
//    private ReviewCompleteListFragment           fCompletedList;
    private FragmentTabListener<NeedsReviewListFragment> needsListener;
    private FragmentTabListener<OffersUnderUserReviewListFragment> userReviewListener;
    private FragmentTabListener<ReviewCompleteListFragment> completedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("");
        setContentView(R.layout.activity_reviewer_offer_list);
        if (savedInstanceState == null) {
            setupTabs();
        }

    }

    private void setupTabs() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        needsListener = new FragmentTabListener<NeedsReviewListFragment>(R.id.flDonorListContainer, this, "first", NeedsReviewListFragment.class);
        Tab needReviewTab = actionBar
                .newTab()
                .setText("Needs Review")
                .setTag("ReviewerOfferFragmentList")
                .setTabListener(needsListener);

        actionBar.addTab(needReviewTab);
        actionBar.selectTab(needReviewTab);

        userReviewListener = new FragmentTabListener<OffersUnderUserReviewListFragment>(R.id.flDonorListContainer, this, "second", OffersUnderUserReviewListFragment.class);
        Tab reviewedByUserTab = actionBar
                .newTab()
                .setText("My Reviews")
                .setTag("OffersUnderUserReviewListFragment")
                .setTabListener(userReviewListener);
        actionBar.addTab(reviewedByUserTab);


        completedListener = new FragmentTabListener<ReviewCompleteListFragment>(R.id.flDonorListContainer, this, "third", ReviewCompleteListFragment.class);
        Tab reviewCompletedTab = actionBar
                .newTab()
                .setText("Completed")
                .setTag("ReviewCompleteListFragment")
                .setTabListener(completedListener);

        actionBar.addTab(reviewCompletedTab);
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
        if (requestCode == ITEM_REQUEST_CODE) {

            ReviewerOfferFragmentList fNeedsReviewList            = (ReviewerOfferFragmentList) needsListener.mFragment;
            ReviewerOfferFragmentList fOffersUnderUserReviewList  = (ReviewerOfferFragmentList) userReviewListener.mFragment;
            ReviewerOfferFragmentList fCompletedList              = (ReviewerOfferFragmentList) completedListener.mFragment;

            if (null != fNeedsReviewList) {
                fNeedsReviewList.refresh();
            }
            if (null != fOffersUnderUserReviewList) {
                fOffersUnderUserReviewList.refresh();
            }
            if (null != fCompletedList) {
                fCompletedList.refresh();
            }
        }
    }

}
