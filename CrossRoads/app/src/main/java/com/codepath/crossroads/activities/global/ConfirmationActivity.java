package com.codepath.crossroads.activities.global;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.crossroads.R;
import com.codepath.crossroads.activities.donors.DonorOfferListActivity;
import com.codepath.crossroads.activities.reviewer.ReviewerOfferListActivity;
import com.codepath.crossroads.models.User;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.HashMap;

public class ConfirmationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.confirmation, menu);
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

    public void onConfirmationClick(View view) {
        EditText code = (EditText) findViewById(R.id.confirmInput);

        HashMap<String,String> parameters  = new HashMap<String,String> ();

        parameters.put("confirmationCode", code.getText().toString());
        parameters.put("objectId", User.USER_ID);


        ParseCloud.callFunctionInBackground("authenticateConfirmation", parameters, new FunctionCallback<String>() {
            @Override
            public void done(String result, ParseException e) {

                if (e == null) {
                    ParseObject user = User.parseUserObject();

                    try {
                        user.fetchIfNeeded();
                    } catch (ParseException innerE) {
                        innerE.printStackTrace();
                    }

                    boolean isAdmin = user.getBoolean("isAdmin");

                    if (isAdmin) {
                        Intent i = new Intent(getBaseContext(), ReviewerOfferListActivity.class);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(getBaseContext(), DonorOfferListActivity.class);
                        startActivity(i);
                    }
                }
                else {
                    Toast.makeText(getBaseContext(),R.string.automaticSMSError,Toast.LENGTH_LONG).show();
                }
            }
        });
    }
 public void resendConfirmation(View view) {
     HashMap<String,String> parameters  = new HashMap<String,String> ();
     parameters.put("objectId", User.USER_ID);

     ParseCloud.callFunctionInBackground("sendConfirmation",parameters,new FunctionCallback() {

         @Override
         public void done(Object o, ParseException e) {
             Toast.makeText(getBaseContext(),R.string.resentNotification,Toast.LENGTH_LONG).show();
         }
     });
 }
}
