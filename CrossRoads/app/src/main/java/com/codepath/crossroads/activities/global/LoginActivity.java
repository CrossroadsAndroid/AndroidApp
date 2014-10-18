package com.codepath.crossroads.activities.global;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.content.Intent;

import com.codepath.crossroads.R;
import com.codepath.crossroads.activities.reviewer.ReviewerDonorListActivity;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.*;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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

    public void showDonor(MenuItem menuItem)
    {
        Toast.makeText(this, "Donor", Toast.LENGTH_SHORT).show();
    }

    public void showReviewer(MenuItem menuItem)
    {
        Toast.makeText(this, "Reviewer", Toast.LENGTH_SHORT).show();
        Intent intent	= new Intent(this, ReviewerDonorListActivity.class);
        startActivity(intent);
    }
}
