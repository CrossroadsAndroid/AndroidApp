package com.codepath.crossroads.activities.global;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.crossroads.R;
import com.codepath.crossroads.activities.donors.DonorOfferActivity;
import com.codepath.crossroads.activities.donors.DonorOfferListActivity;
<<<<<<< HEAD
=======
import com.codepath.crossroads.activities.reviewer.ReviewerDonorListActivity;
>>>>>>> abee00aa7d05a6fab70e1b2b5a696d8db76a9135
import com.codepath.crossroads.activities.reviewer.ReviewerOfferListActivity;
import com.newrelic.agent.android.NewRelic;
import com.parse.Parse;
import com.parse.ParseObject;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "ZwqdQKWXjs4vs9n22rqL0gQA0mBoFCooSMtA7BBG", "qp27sTi284lAm3u2DxUafAHwGNxiVxecN0DL1JuX");
        try {
            ParseObject testObject = new ParseObject("ar-TestObject");
            testObject.put("foo", "bar");
            testObject.save();

            Log.i("", testObject.getObjectId());
        } catch (Exception ex) {
        }

        NewRelic.withApplicationToken("AA86ac9a75f3d317e7cfb8d7284b9210d60298b80e").start(this.getApplication());
    }


    /*
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    */

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

    public void showDonor(MenuItem menuItem)
    {
        Toast.makeText(this, "Donor", Toast.LENGTH_SHORT).show();
    }

    public void showReviewer(MenuItem menuItem) {
        Toast.makeText(this, "Reviewer", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ReviewerOfferListActivity.class);
        startActivity(intent);
    }

    public void onLoginCLick(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

}
