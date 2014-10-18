package com.codepath.crossroads.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by tonyleung on 10/12/14.
 */
public class Item {

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

    /**
     * Factory method that uses the given parse object to return an Item object
     * @param parseObject - parse object that corresponds to an Item
     * @return Item object
     */
    public static Item fromParseObject(ParseObject parseObject) {
        Item item                   = new Item();

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
            Log.d("Error", "Exception parsing User: " + exception.toString());
            exception.printStackTrace();
        }

        return item;
    }

    /**
     * return Item object from a parse objectID
     * @param objectID  - object ID for the corresponding Item
     * @return  - Item object
     */
    public static Item fromParseObjectID(String objectID) {
        if (null == objectID) {
            return null;
        }

        try {
            ParseQuery<ParseObject> query   = ParseQuery.getQuery(PARSE_ITEM_TABLE_NAME);
            return  Item.fromParseObject(query.get(objectID));
        }
        catch (Exception exception) {
            Log.d("Error", "Cannot retrieve " + objectID + " from the Item table:" + exception.toString());
            exception.printStackTrace();
        }
        return null;
    }
}
