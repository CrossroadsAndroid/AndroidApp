package com.codepath.crossroads.activities.donors;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.codepath.crossroads.Constants;
import com.codepath.crossroads.R;
import com.codepath.crossroads.Utils;
import com.codepath.crossroads.adapters.ItemConditionAdapter;
import com.codepath.crossroads.models.ParseItem;
import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddItemActivity extends Activity {

    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 3010;

    Spinner spCondition;
    ImageView ivItemImage;
    EditText etDescription;
    File nextCameraCaptureLocation;

    ParseItem item;
    String itemId;
    String offerUUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Item Details");
        setContentView(R.layout.activity_add_item);

        if (getIntent().hasExtra("UUID")) {
            itemId = getIntent().getExtras().getString("UUID");
        }

        offerUUID = getIntent().getExtras().getString("OfferUUID");

        if (itemId == null) {
            item = new ParseItem();
            item.setState(Constants.ITEM_STATE_PENDING);
            item.setUUID();
            item.setOfferUUID(offerUUID);
            itemId = item.getUUID();
        } else {
            ParseQuery<ParseItem> query = ParseItem.getQuery();
            query.fromLocalDatastore();
            query.whereEqualTo("uuid", itemId);
            query.getFirstInBackground(new GetCallback<ParseItem>() {

                @Override
                public void done(ParseItem object, ParseException e) {
                    if (!isFinishing()) {
                        item = object;
                        etDescription.setText(item.getDetails());
                        try {
                            if (object.getPhoto() != null) {
                                byte[] data = object.getPhoto().getData();
                                ivItemImage.setImageBitmap(Utils.byteArrToBitmap(data));
                            }
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
        }

        spCondition = (Spinner) findViewById(R.id.spCondition);
        ivItemImage = (ImageView) findViewById(R.id.ivItemImg);
        etDescription = (EditText) findViewById(R.id.etDescription);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.condition_values, android.R.layout.simple_spinner_dropdown_item);
        spCondition.setAdapter(adapter);

        GridView gvCondition = (GridView) findViewById(R.id.gvCondition);
        gvCondition.setAdapter(new ItemConditionAdapter(this));
        gvCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("", "Selected " + String.valueOf(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ivItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCamera();
            }
        });

    }

    private void loadCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Uri fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                // Toast.makeText(this, "Image saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();
                readCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
                Toast.makeText(this, "Capture failed! Please try again.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void saveItem(View v) {
        item.setDetails(etDescription.getText().toString());
        item.setCondition(spCondition.getSelectedItem().toString());
        item.setIsOffline(true);

        item.pinInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (isFinishing()) {
                    return;
                }

                if (e == null) {
                    Log.i("", "Saved item " + item.getUUID() + " to offer " + item.getOfferUUID());
                    finishAdd();
                } else {
                    Toast.makeText(AddItemActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void finishAdd() {
        Intent i = new Intent();
        i.putExtra("cmd", Constants.ITEM_OP_SAVE);
        i.putExtra("uuid", item.getUUID());
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    public void deleteItem(View v) {
        // FIXME test if this automatically deletes from parent
        item.unpinInBackground(new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    if (item.getObjectId() != null) {
                        item.deleteInBackground();
                    }
                }
            }
        });

        Intent i = new Intent();
        i.putExtra("cmd", Constants.ITEM_OP_DELETE);
        i.putExtra("uuid", item.getUUID());
        setResult(RESULT_OK, i);
        finish();
    }

    private void readCapturedImage() {
        Bitmap bmp = Utils.getStoredImage(nextCameraCaptureLocation.getAbsolutePath());
        byte[] data = Utils.bitmapToByteArr(bmp);
        ParseFile img = new ParseFile(itemId + ".png", data);
        img.saveInBackground();
        item.setPhoto(img);
        ivItemImage.setImageBitmap(bmp);
    }

    /**
     * Create a file Uri for saving an image or video
     */
    private Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Crossroads");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("CrossRoads", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        if (type == MEDIA_TYPE_IMAGE) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            nextCameraCaptureLocation = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return nextCameraCaptureLocation;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_item, menu);
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

}
