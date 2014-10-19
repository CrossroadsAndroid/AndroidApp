package com.codepath.crossroads.activities.donors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.crossroads.R;
import com.codepath.crossroads.fragments.ItemListFragment;
import com.codepath.crossroads.models.DonorItem;
import com.codepath.crossroads.models.DonorOffer;

public class DonorOfferActivity extends Activity {
    ItemListFragment fragItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_offer);
        fragItemList = (ItemListFragment) getFragmentManager().findFragmentById(R.id.fragItemList);

        if (getIntent().getExtras() != null) {
            DonorOffer currentOffer = getIntent().getExtras().getParcelable("offer");
            fragItemList.setOffer(currentOffer);
        }
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
        startActivityForResult(i, 1010);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1010) {
            if (resultCode == RESULT_OK) {
                DonorItem i = data.getExtras().getParcelable("item");
                int pos = data.getExtras().getInt("pos");
                Log.d("", i.toString());
                fragItemList.updateItem(i, pos);
            }
        }
    }

    public void submitOffer(View v) {
        DonorOffer currentOffer = fragItemList.getOffer();
        if (currentOffer != null) {
            currentOffer.setSubmitted();
        } else {
            Toast.makeText(this, "No valid offer!", Toast.LENGTH_SHORT).show();
        }
    }
}
