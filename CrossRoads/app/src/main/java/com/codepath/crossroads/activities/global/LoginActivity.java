package com.codepath.crossroads.activities.global;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.crossroads.R;
import com.codepath.crossroads.activities.donors.DonorOfferActivity;
import com.codepath.crossroads.activities.donors.DonorOfferListActivity;
import com.codepath.crossroads.activities.reviewer.ReviewerOfferListActivity;
import com.codepath.crossroads.models.User;
import com.parse.ParseException;
import com.parse.ParseObject;


public class LoginActivity extends Activity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = getSharedPreferences("com.codepath.crossroads", Context.MODE_PRIVATE);
        String user_value = prefs.getString("user_id", null);

        if (user_value != null) {
            User.USER_ID = user_value;
            ParseObject parseUser = User.parseUserObject();
            Intent i;

            try {
                parseUser.fetchIfNeeded();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (parseUser.getBoolean("hasConfirmed")) {

                if (parseUser.getBoolean("isAdmin")) {
                    i = new Intent(this,ReviewerOfferListActivity.class);
                } else {
                    i = new Intent(this, DonorOfferListActivity.class);
                }

            } else {
                i = new Intent(this,ConfirmationActivity.class);
            }
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
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
        // Intent i = new Intent(this, RegisterActivity.class);
        Intent i = new Intent(this, DonorOfferActivity.class);
        startActivity(i);
    }

}
