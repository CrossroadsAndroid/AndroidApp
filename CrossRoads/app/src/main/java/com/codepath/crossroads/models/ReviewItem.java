package com.codepath.crossroads.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

/**
 * Created by tonyleung on 10/12/14.
 */
public class ReviewItem  implements Parcelable {

    String                      details;
    String                      condition;
    String                      state;
    String                      rejectionReason;
    Bitmap                      photo;

    private static final String	PARSE_ITEM_TABLE_NAME           = "Item";

    private static final String	PARSE_ITEM_DETAILS_KEY          = "details";
    private static final String	PARSE_ITEM_CONDITION_KEY        = "condition";
    private static final String	PARSE_ITEM_STATE_KEY            = "state";
    private static final String	PARSE_ITEM_REJECTION_REASON_KEY = "rejectionReason";
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
            item.details            = parseObject.getString(PARSE_ITEM_DETAILS_KEY);
            item.condition          = parseObject.getString(PARSE_ITEM_CONDITION_KEY);
            item.state              = parseObject.getString(PARSE_ITEM_STATE_KEY);
            item.rejectionReason    = parseObject.getString(PARSE_ITEM_REJECTION_REASON_KEY);

            ParseFile photoFile     = (ParseFile) parseObject.get(PARSE_ITEM_PHOTO_KEY);
            byte[] photoData        = photoFile.getData();
            item.photo              = BitmapFactory.decodeByteArray(photoData, 0, photoData.length);

        }catch (Exception exception) {
            Log.d("Error", "Exception parsing ReviewUser: " + exception.toString());
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

    public String getRejectionReason() {
        return rejectionReason;
    }

    public Bitmap getPhoto() {
        return photo;
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

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(details);
        out.writeString(condition);
        out.writeString(state);
        out.writeString(rejectionReason);
        out.writeParcelable(photo, flags);
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
        details				= in.readString();
        condition			= in.readString();
        state		        = in.readString();
        rejectionReason	    = in.readString();
        photo               = in.readParcelable(Bitmap.class.getClassLoader());
    }
}
