package com.codepath.crossroads.activities.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.crossroads.R;
import com.codepath.crossroads.fragments.ItemListFragment;
import com.codepath.crossroads.models.ReviewItem;
import com.codepath.crossroads.models.ReviewOffer;

public class ReviewerOfferActivity extends FragmentActivity implements ItemListFragment.OnItemSelectedListener {

    public static final String INTENT_ITEM    = "ITEM";

    private ReviewOffer offer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewer_offer);

        offer						        = getIntent().getParcelableExtra(ReviewerOfferListActivity.INTENT_OFFER);
        // Within the activity
        FragmentTransaction transaction	    = getSupportFragmentManager().beginTransaction();
        ItemListFragment itemListFragment   = ItemListFragment.newInstance(offer.getItems());
        transaction.replace(R.id.flItems, itemListFragment);
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
    public void didClickItem(ReviewItem item) {
        if (null == item) {
            return;
        }

        Intent intent	= new Intent(this, ReviewerItemActivity.class);
        intent.putExtra(INTENT_ITEM, item);
        startActivity(intent);
    }
}
