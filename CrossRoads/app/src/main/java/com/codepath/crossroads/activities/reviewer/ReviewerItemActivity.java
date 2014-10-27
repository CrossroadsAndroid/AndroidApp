package com.codepath.crossroads.activities.reviewer;

import android.app.Activity;
import android.content.Intent;
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

    public static final String  INTENT_ITEM             = "ITEM";
    public static final String  INTENT_ITEM_INDEX        = "ITEM_INDEX";
    public static final String  INTENT_ITEM_DID_CHANGE  = "DID_CHANGE";

    private ReviewItem          item;
    private int                 index;
    private ImageView           ivPhoto;
    private TextView            tvDetails;
    private TextView            tvCondition;
    private TextView            tvStatus;
    private EditText            etReason;
    private EditText            etComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewer_item);

        item            = getIntent().getParcelableExtra(INTENT_ITEM);
        index           = getIntent().getIntExtra(INTENT_ITEM_INDEX, 0);
        ivPhoto         = (ImageView) findViewById(R.id.ivPhoto);
        tvDetails       = (TextView) findViewById(R.id.tvDetails);
        tvCondition     = (TextView) findViewById(R.id.tvCondition);
        tvStatus        = (TextView) findViewById(R.id.tvStatus);
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

        ivPhoto.setTag(item.getParseID());
        item.getImageView(ivPhoto);
        tvDetails.setText(item.getDetails());
        tvCondition.setText(item.getCondition());
        tvStatus.setText(item.getState());

        etReason.setText(item.getRejectionReason());
        etComment.setText(item.getComments());
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
        item.setState(ReviewItem.PARSE_ITEM_STATE_ACCEPTED);
        item.setRejectionReason(etReason.getText().toString());
        item.setComments(etComment.getText().toString());
        item.updateItem();

        // create data intent and finish
        Intent intent	= new Intent();
        intent.putExtra(INTENT_ITEM, item);
        intent.putExtra(INTENT_ITEM_INDEX, index);
        intent.putExtra(INTENT_ITEM_DID_CHANGE, true);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * set the item as declined
     * @param view
     */
    public void rejectButtonDidPress(View view) {
        item.setState(ReviewItem.PARSE_ITEM_STATE_REJECTED);
        item.setRejectionReason(etReason.getText().toString());
        item.setComments(etComment.getText().toString());
        item.updateItem();

        // create data intent and finish
        Intent intent	= new Intent();
        intent.putExtra(INTENT_ITEM, item);
        intent.putExtra(INTENT_ITEM_INDEX, index);
        intent.putExtra(INTENT_ITEM_DID_CHANGE, true);
        setResult(RESULT_OK, intent);
        finish();
    }
}
