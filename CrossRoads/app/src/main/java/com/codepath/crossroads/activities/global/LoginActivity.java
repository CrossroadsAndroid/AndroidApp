package com.codepath.crossroads.activities.global;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.crossroads.R;
import com.codepath.crossroads.activities.reviewer.ReviewerOfferListActivity;
import com.codepath.crossroads.models.ParseItem;
import com.codepath.crossroads.models.ParseOffer;
import com.parse.Parse;
import com.parse.ParseObject;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(ParseItem.class);
        ParseObject.registerSubclass(ParseOffer.class);
        Parse.initialize(this, "ZwqdQKWXjs4vs9n22rqL0gQA0mBoFCooSMtA7BBG", "qp27sTi284lAm3u2DxUafAHwGNxiVxecN0DL1JuX");
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

    public void showDonor(MenuItem menuItem) {
        Toast.makeText(this, "Donor", Toast.LENGTH_SHORT).show();
    }

    public void showReviewer(MenuItem menuItem) {
        Toast.makeText(this, "Reviewer", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ReviewerOfferListActivity.class);
        startActivity(intent);
    }

    public void onLoginCLick(View view) {
//        Intent i = new Intent(this, RegisterActivity.class);

        Intent i = new Intent(this, ReviewerOfferListActivity.class);
        startActivity(i);
    }

}
