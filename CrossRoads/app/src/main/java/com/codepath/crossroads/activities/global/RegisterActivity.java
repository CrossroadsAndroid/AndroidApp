package com.codepath.crossroads.activities.global;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.crossroads.R;
import com.codepath.crossroads.models.User;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.HashMap;
import java.util.Random;

public class RegisterActivity extends Activity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Spinner spinner = (Spinner) findViewById(R.id.districtSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.districts_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        prefs = getSharedPreferences("com.codepath.crossroads", Context.MODE_PRIVATE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
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

    public void onRegisterClick (View view) {
        EditText mobilePhone = (EditText) findViewById(R.id.mobilePhoneEdit);
        EditText firstName = (EditText) findViewById(R.id.firstNameEdit);
        EditText lastName = (EditText) findViewById(R.id.lastNameEdit);
        Spinner  district = (Spinner) findViewById(R.id.districtSpinner);
        CheckBox reviewer = (CheckBox) findViewById(R.id.reviewerCheck);
        Random code = new Random();
        int confirmationCode = code.nextInt(10000)+1000;

        final ParseObject user = new ParseObject("User2");
        //user.put();
        user.put("mobile", mobilePhone.getText().toString());
        user.put("firstName", firstName.getText().toString());
        user.put("lastName", lastName.getText().toString());
        user.put("district",district.getSelectedItem().toString());
        user.put("isAdmin", reviewer.isChecked());
        user.put("hasConfirmed",false);
        user.put("confirmationCode",Integer.toString(confirmationCode));
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {


                User.USER_ID = user.getObjectId();
                prefs.edit().putString("user_id",User.USER_ID).apply();


                HashMap<String,String> parameters  = new HashMap<String,String> ();
                parameters.put("objectId", User.USER_ID);

                ParseCloud.callFunctionInBackground("sendConfirmation",parameters,new FunctionCallback() {

                    @Override
                    public void done(Object o, ParseException e) {

                    }
                });
            }
        });

        Intent i = new Intent(this, ConfirmationActivity.class);
        startActivity(i);
    }

}
