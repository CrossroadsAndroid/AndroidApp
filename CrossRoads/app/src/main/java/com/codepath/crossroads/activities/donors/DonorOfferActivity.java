package com.codepath.crossroads.activities.donors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.crossroads.Constants;
import com.codepath.crossroads.R;
import com.codepath.crossroads.adapters.ItemListAdapter;
import com.codepath.crossroads.models.ParseItem;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class DonorOfferActivity extends Activity {

    ListView lvItems;
    private ParseQueryAdapter<ParseItem> itemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_offer);

        // Set up the Parse query to use in the adapter
        ParseQueryAdapter.QueryFactory<ParseItem> factory = new ParseQueryAdapter.QueryFactory<ParseItem>() {
            public ParseQuery<ParseItem> create() {
                ParseQuery<ParseItem> query = ParseItem.getQuery();
                query.orderByDescending("createdAt");
                query.fromLocalDatastore();
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

    void editItem(ParseItem item) {
        Intent i = new Intent(this, AddItemActivity.class);
        i.putExtra("UUID", item.getUUID());
        startActivityForResult(i, Constants.EDIT_ITEM_CODE);
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
        startActivityForResult(i, Constants.EDIT_ITEM_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.EDIT_ITEM_CODE) {
                itemListAdapter.loadObjects();
                /*
                DonorItem i = data.getExtras().getParcelable("item");
                int pos = data.getExtras().getInt("pos");
                Log.d("", i.toString());
                // fragItemList.updateItem(i, pos);
                */
            }
        }
    }

    public void submitOffer(View v) {
        /*
        DonorOffer currentOffer = fragItemList.getOffer();
        if (currentOffer != null) {
            currentOffer.setSubmitted();
        } else {
            Toast.makeText(this, "No valid offer!", Toast.LENGTH_SHORT).show();
        }
        */
    }
}
