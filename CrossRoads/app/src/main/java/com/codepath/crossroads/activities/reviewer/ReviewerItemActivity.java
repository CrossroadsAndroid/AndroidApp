package com.codepath.crossroads.activities.reviewer;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.crossroads.R;
import com.codepath.crossroads.models.ReviewItem;

public class ReviewerItemActivity extends Activity {

    private ReviewItem  item;
    private ImageView   ivPhoto;
    private TextView    tvDetails;
    private TextView    tvCondition;
    private EditText    etReason;
    private EditText    etComment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewer_item);

        item            = getIntent().getParcelableExtra(ReviewerOfferActivity.INTENT_ITEM);
        ivPhoto         = (ImageView) findViewById(R.id.ivPhoto);
        tvDetails       = (TextView) findViewById(R.id.tvDetails);
        tvCondition     = (TextView) findViewById(R.id.tvCondition);
        etReason        = (EditText) findViewById(R.id.etReason);
        etComment       = (EditText) findViewById(R.id.etComment);

        populateFields();
    }

    /**
     * Populate the fields
     */
    private void populateFields() {
        if (null == item) {
            return;
        }

        ivPhoto.setImageBitmap(item.getPhoto());
        tvDetails.setText(item.getDetails());
        tvCondition.setText(item.getCondition());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.reviewer_item, menu);
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

    /**
     * set the item as accepted
     * @param view
     */
    public void acceptButtonDidPress(View view) {
        
    }

    /**
     * set the item as declined
     * @param view
     */
    public void declineButtonDidPress(View view) {

    }
}
