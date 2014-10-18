package com.codepath.crossroads.activities.reviewer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.codepath.crossroads.R;
import com.codepath.crossroads.models.Offer;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.*;

import java.util.ArrayList;
import java.util.List;

public class ReviewerDonorListActivity extends Activity {

    ArrayList<Offer> needReviewerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewer_donor_list);

        Parse.initialize(this, "ZwqdQKWXjs4vs9n22rqL0gQA0mBoFCooSMtA7BBG", "qp27sTi284lAm3u2DxUafAHwGNxiVxecN0DL1JuX");


        needReviewerList    = Offer.getNeedsReviewerOfferList();
//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();
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
