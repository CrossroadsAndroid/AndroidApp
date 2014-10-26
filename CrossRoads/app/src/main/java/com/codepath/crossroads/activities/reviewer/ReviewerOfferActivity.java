package com.codepath.crossroads.activities.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.crossroads.R;
import com.codepath.crossroads.fragments.ItemListFragment;
import com.codepath.crossroads.fragments.UserInfoFragment;
import com.codepath.crossroads.models.ReviewItem;
import com.codepath.crossroads.models.ReviewOffer;

public class ReviewerOfferActivity extends FragmentActivity implements ItemListFragment.OnItemSelectedListener {

    private final int 	        ITEM_REQUEST_CODE	    = 20;
    private ItemListFragment    itemListFragment;
    private UserInfoFragment    userInfoFragment;

    private ReviewOffer offer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewer_offer);

        offer						        = getIntent().getParcelableExtra(ReviewerOfferListActivity.INTENT_OFFER);

        FragmentTransaction transaction	    = getSupportFragmentManager().beginTransaction();
        itemListFragment                    = ItemListFragment.newInstance(offer.getItems());
        transaction.replace(R.id.flItems, itemListFragment);

        userInfoFragment                    = UserInfoFragment.newInstance(offer.getDonor());
        transaction.replace(R.id.userInfoFragment, userInfoFragment);

        transaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.reviewer_donor, menu);
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

    @Override
    /**
     * present intent for item
     */
    public void didClickItem(ReviewItem item, int index) {
        if (null == item) {
            return;
        }

        Intent intent	= new Intent(this, ReviewerItemActivity.class);
        intent.putExtra(ReviewerItemActivity.INTENT_ITEM, item);
        intent.putExtra(ReviewerItemActivity.INTENT_ITEM_INDEX, index);

        startActivityForResult(intent, ITEM_REQUEST_CODE);
    }


    /**
     * check the state has changed, then assign yourself as the reviewer
     * check if all the items to see the state if it is review complete. if it is, assign the state
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // make sure that it is the same request code as the
        if (resultCode == RESULT_OK && requestCode == ITEM_REQUEST_CODE) {
            // grab the position and newItem

            ReviewItem item     = intent.getExtras().getParcelable(ReviewerItemActivity.INTENT_ITEM);
            int index           = intent.getExtras().getInt(ReviewerItemActivity.INTENT_ITEM_INDEX);
            Boolean didChange   = intent.getExtras().getBoolean(ReviewerItemActivity.INTENT_ITEM_DID_CHANGE);

            offer.getItems().set(index, item);
            itemListFragment.refresh();

            // item change, submit update to offer
            if (didChange) {
                // if the review is complete, set state to complete
                if (isReviewComplete()) {
                    offer.setReviewState(ReviewOffer.PARSE_OFFER_REVIEW_COMPLETED_VALUE);
                }
                else {
                    offer.setReviewState(ReviewOffer.PARSE_OFFER_UNDER_REVIEW_VALUE);
                }
                offer.updateOffer();
            }
        }
    }

    /**
     * loop through each item in the offers to see if it is complete
     * @return
     */
    private Boolean isReviewComplete() {
        for (int i = 0; i < offer.getItems().size(); i++) {
            ReviewItem item = offer.getItems().get(i);

            // if any state is set to needs review, return false.
            if (item.getState().equalsIgnoreCase(ReviewItem.PARSE_ITEM_STATE_NEEDS_REVIEW)) {
                return false;
            }
        }
        return true;
    }
}
