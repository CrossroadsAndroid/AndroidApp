package com.codepath.crossroads.activities.reviewer;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.crossroads.Listeners.FragmentTabListener;
import com.codepath.crossroads.R;
import com.codepath.crossroads.fragments.NeedsReviewListFragment;
import com.codepath.crossroads.fragments.OffersUnderUserReviewListFragment;
import com.codepath.crossroads.fragments.ReviewCompleteListFragment;
import com.parse.Parse;


public class ReviewerDonorListActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewer_donor_list);

        Parse.initialize(this, "ZwqdQKWXjs4vs9n22rqL0gQA0mBoFCooSMtA7BBG", "qp27sTi284lAm3u2DxUafAHwGNxiVxecN0DL1JuX");
        setupTabs();
    }

    private void setupTabs() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        Tab needReviewTab = actionBar
                .newTab()
                .setText("Needs Review")
                .setTag("ReviewerOfferFragmentList")
                .setTabListener(
                        new FragmentTabListener<NeedsReviewListFragment>(R.id.flDonorListContainer, this, "first",
                                NeedsReviewListFragment.class));

        actionBar.addTab(needReviewTab);
        actionBar.selectTab(needReviewTab);

        Tab reviewedByUserTab = actionBar
                .newTab()
                .setText("My Reviews")
                .setTag("OffersUnderUserReviewListFragment")
                .setTabListener(
                        new FragmentTabListener<OffersUnderUserReviewListFragment>(R.id.flDonorListContainer, this, "second",
                                OffersUnderUserReviewListFragment.class));

        actionBar.addTab(reviewedByUserTab);


        Tab reviewCompletedTab = actionBar
                .newTab()
                .setText("Completed")
                .setTag("ReviewCompleteListFragment")
                .setTabListener(
                        new FragmentTabListener<ReviewCompleteListFragment>(R.id.flDonorListContainer, this, "third",
                                ReviewCompleteListFragment.class));

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
}
