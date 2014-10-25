package com.codepath.crossroads.activities.donors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.crossroads.Constants;
import com.codepath.crossroads.R;
import com.codepath.crossroads.adapters.ItemListAdapter;
import com.codepath.crossroads.models.ParseItem;
import com.codepath.crossroads.models.ParseOffer;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.SaveCallback;

public class DonorOfferActivity extends Activity {

    ListView lvItems;
    ParseOffer offer;
    String offerId;
    private ParseQueryAdapter<ParseItem> itemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_offer);
        if (getIntent().hasExtra("uuid")) {
            offerId = getIntent().getExtras().getString("uuid");
        }

        Log.i("", "Load offer " + offerId);

        if (offerId == null) {
            offer = new ParseOffer();
            offer.setUUID();
            offerId = offer.getUUID();
        } else {
            ParseQuery<ParseOffer> query = ParseOffer.getQuery();
            query.fromLocalDatastore();
            query.whereEqualTo("uuid", offerId);
            query.getFirstInBackground(new GetCallback<ParseOffer>() {

                @Override
                public void done(ParseOffer object, ParseException e) {
                    if (!isFinishing()) {
                        offer = object;
                        // Is this required?
                        Toast.makeText(DonorOfferActivity.this, "Filling offer stuff", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        // Set up the Parse query to use in the adapter
        ParseQueryAdapter.QueryFactory<ParseItem> factory = new ParseQueryAdapter.QueryFactory<ParseItem>() {
            public ParseQuery<ParseItem> create() {
                ParseQuery<ParseItem> query = ParseItem.getQuery()
                        .whereEqualTo("offer_uuid", offerId)
                        .orderByDescending("createdAt")
                        .fromLocalDatastore();

                return query;
            }
        };

        itemListAdapter = new ItemListAdapter(this, factory);
        lvItems = (ListView) findViewById(R.id.lvItems);
        // FIXME add a empty view
        lvItems.setAdapter(itemListAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ParseItem item = itemListAdapter.getItem(i);
                editItem(item);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.donor_offer, menu);
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

    public void addItem(View v) {
        Intent i = new Intent(this, AddItemActivity.class);
        i.putExtra("OfferUUID", offerId);
        startActivityForResult(i, Constants.EDIT_ITEM_CODE);
    }

    void editItem(ParseItem item) {
        Intent i = new Intent(this, AddItemActivity.class);
        i.putExtra("UUID", item.getUUID());
        i.putExtra("OfferUUID", offerId);
        startActivityForResult(i, Constants.EDIT_ITEM_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.EDIT_ITEM_CODE) {
                if (data.hasExtra("cmd")) {
                    String uuid = data.getExtras().getString("uuid");
                    Log.i("", "Got item " + uuid);

                    // Save it to offer
                    ParseQuery<ParseItem> query = ParseItem.getQuery();
                    query.fromLocalDatastore();
                    query.whereEqualTo("uuid", uuid);
                    query.getFirstInBackground(new GetCallback<ParseItem>() {

                        @Override
                        public void done(ParseItem object, ParseException e) {
                            if (!isFinishing()) {
                                // FIXME race
                                offer.addItem(object);
                                Log.i("", "Add " + object.getUUID() + " to " + offer.getUUID());
                                Toast.makeText(DonorOfferActivity.this, "New item added " + object.getUUID(), Toast.LENGTH_SHORT).show();
                                // syncTodosToParse();
                            }
                        }
                    });
                }
                itemListAdapter.loadObjects();
            }
        }
    }

    public void submitOffer(View v) {
        // add it to "ALL_OFFERS"
        offer.pinInBackground("ALL_OFFERS", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (isFinishing()) {
                    return;
                }

                if (e == null) {
                    Log.i("", "Saved offer " + offer.getUUID());
                    Intent i = new Intent();
                    i.putExtra("cmd", Constants.OFFER_OP_SAVE);
                    i.putExtra("uuid", offerId);
                    setResult(Activity.RESULT_OK, i);
                    finish();
                }
            }
        });
    }


    private void syncTodosToParse() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if ((ni != null) && (ni.isConnected())) {
            /*
            if (!ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
                // If we have a network connection and a current
                // logged in user, sync the todos
                Log.e("", "Remote save");
            } else {
                Log.e("", "No logged in user");
            }
            */
        } else {
            Toast.makeText(getApplicationContext(), "Your device appears to be offline.", Toast.LENGTH_LONG).show();
        }
    }
}
