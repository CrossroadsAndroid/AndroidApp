package com.codepath.crossroads.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by tonyleung on 10/12/14.
 */
public class ReviewItem  implements Parcelable {

    public static final String  PARSE_ITEM_STATE_ACCEPTED       = "Accepted";
    public static final String  PARSE_ITEM_STATE_REJECTED       = "Rejected";
    public static final String  PARSE_ITEM_STATE_NEEDS_REVIEW   = "Pending";

    String                      parseID;
    String                      details;
    String                      condition;
    String                      state;
    String                      rejectionReason;
    String                      comments;
    Bitmap                      itemImage;

    private static final String	PARSE_ITEM_TABLE_NAME           = "Item";

    private static final String	PARSE_ITEM_DETAILS_KEY          = "details";
    private static final String	PARSE_ITEM_CONDITION_KEY        = "condition";
    private static final String	PARSE_ITEM_STATE_KEY            = "state";
    private static final String	PARSE_ITEM_REJECTION_REASON_KEY = "rejectionReason";
    private static final String	PARSE_ITEM_COMMENTS_KEY         = "comments";
    private static final String	PARSE_ITEM_PHOTO_KEY            = "photo";

    public ReviewItem() {
        super();
    }
    /**
     * Factory method that uses the given parse object to return an Item object
     * @param parseObject - parse object that corresponds to an Item
     * @return Item object
     */
    public static ReviewItem fromParseObject(ParseObject parseObject) {
        ReviewItem item             = new ReviewItem();

        if (null == parseObject) {
            return item;
        }

        try {
            item.parseID            = parseObject.getObjectId();
            item.details            = parseObject.getString(PARSE_ITEM_DETAILS_KEY);
            item.condition          = parseObject.getString(PARSE_ITEM_CONDITION_KEY);
            item.state              = parseObject.getString(PARSE_ITEM_STATE_KEY);
            item.rejectionReason    = parseObject.getString(PARSE_ITEM_REJECTION_REASON_KEY);
            item.comments           = parseObject.getString(PARSE_ITEM_COMMENTS_KEY);

//            ParseFile photoFile     = (ParseFile) parseObject.get(PARSE_ITEM_PHOTO_KEY);
//            byte[] photoData        = photoFile.getData();
//            item.photo              = BitmapFactory.decodeByteArray(photoData, 0, photoData.length);

        }catch (Exception exception) {
            Log.d("Error", "Exception parsing User: " + exception.toString());
            exception.printStackTrace();
        }

        return item;
    }

    public String getDetails() {
        return details;
    }

    public String getCondition() {
        return condition;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state  = state;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason    = rejectionReason;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Bitmap getItemImage() {
        return itemImage;
    }

    public String getParseID() {
        return parseID;
    }

    /**
     * fetch the image in background and place it in the image view
     * @param imageView
     */
    public void getImageView(ImageView imageView) {
        new ImageLoadAsyncTask(imageView).execute(parseID);
    }

    /**
     * return Item object from a parse objectID
     * @param objectID  - object ID for the corresponding Item
     * @return  - Item object
     */
    public static ReviewItem fromParseObjectID(String objectID) {
        if (null == objectID) {
            return null;
        }

        try {
            ParseQuery<ParseObject> query   = ParseQuery.getQuery(PARSE_ITEM_TABLE_NAME);
            return  ReviewItem.fromParseObject(query.get(objectID));
        }
        catch (Exception exception) {
            Log.d("Error", "Cannot retrieve " + objectID + " from the Item table:" + exception.toString());
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * query the table for the object and modify it. then save
     */
    public void updateItem() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(PARSE_ITEM_TABLE_NAME);

        // Retrieve the object by id
        query.getInBackground(parseID, new GetCallback<ParseObject>() {
            public void done(ParseObject item, ParseException exception) {
                if (exception == null) {
                    item.put(PARSE_ITEM_DETAILS_KEY, details);
                    item.put(PARSE_ITEM_CONDITION_KEY, condition);
                    item.put(PARSE_ITEM_STATE_KEY, state);
                    item.put(PARSE_ITEM_REJECTION_REASON_KEY, rejectionReason);
                    item.put(PARSE_ITEM_COMMENTS_KEY, comments);
                    item.saveInBackground();
                }
                else {
                    Log.d("Error", exception.toString());
                }
            }
        });
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(parseID);
        out.writeString(details);
        out.writeString(condition);
        out.writeString(state);
        out.writeString(rejectionReason);
        out.writeString(comments);
    }

    /**
     * static variable for parcelable
     */
    public static final Parcelable.Creator<ReviewItem> CREATOR = new Parcelable.Creator<ReviewItem>() {
        @Override
        public ReviewItem createFromParcel(Parcel in) {
            return new ReviewItem(in);
        }

        @Override
        public ReviewItem[] newArray(int size) {
            return new ReviewItem[size];
        }
    };

    /**
     * constructer that is built using the parcel
     * @param in
     */
    private ReviewItem(Parcel in) {
        parseID             = in.readString();
        details				= in.readString();
        condition			= in.readString();
        state		        = in.readString();
        rejectionReason	    = in.readString();
        comments            = in.readString();
    }

    // The types specified here are the input data type, the progress type, and the result type
    private class ImageLoadAsyncTask extends AsyncTask<String, Void, Void> {

        private ImageView   imageView;
        private Bitmap      image;

        public ImageLoadAsyncTask (ImageView imageView) {
            this.imageView    = imageView;
        }

        @Override
        protected void onPreExecute() {

        }

        protected void onPostExecute(Void item) {
            String itemId = (String) imageView.getTag();
            if (ReviewItem.this.parseID.equals(itemId)) {
                imageView.setImageBitmap(image);
            }
            ReviewItem.this.itemImage   = image;
        }

        protected Void doInBackground(String... strings) {
            String objectID = strings[0];

            try {
                ParseQuery<ParseObject> query   = ParseQuery.getQuery(PARSE_ITEM_TABLE_NAME);
                ParseObject parseObject         = query.get(objectID);

                ParseFile photoFile     = (ParseFile) parseObject.get(PARSE_ITEM_PHOTO_KEY);
                byte[] photoData        = photoFile.getData();
                image                   = BitmapFactory.decodeByteArray(photoData, 0, photoData.length);
            }
            catch (Exception exception) {
                Log.d("Error", "Cannot retrieve " + objectID + " from the Item table:" + exception.toString());
                exception.printStackTrace();
            }
            return null;
        }

    }
}
