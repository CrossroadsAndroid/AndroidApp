package com.codepath.crossroads.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by tonyleung on 10/12/14.
 */
public class ReviewUser implements Parcelable {

    String                      parseID;
    String                      firstName;
    String                      lastName;
    String                      mobile;
    String                      district;
    String                      neighborhood;

    private static final String TEMP_USER_ID                    = "N069yaMv1E";
    private static final String	PARSE_USER_TABLE_NAME           = "User";

    private static final String	PARSE_USER_FIRST_NAME_KEY       = "firstName";
    private static final String	PARSE_USER_LAST_NAME_KEY        = "lastName";
    private static final String	PARSE_USER_MOBILE_KEY           = "mobile";
    private static final String	PARSE_USER_DISTRICT_KEY         = "district";
    private static final String	PARSE_USER_NEIGHBORHOOD_KEY     = "neighborhood";


    public ReviewUser() {
        super();
    }

    /**
     * Factory method that uses the given parse object to return an ReviewUser object
     * @param parseObject - parse object that corresponds to an ReviewUser
     * @return ReviewUser object
     */
    public static ReviewUser fromParseObject(ParseObject parseObject) {

        if (null == parseObject) {
            return null;
        }

        ReviewUser reviewUser = new ReviewUser();

        try {
            reviewUser.parseID      = parseObject.getObjectId();

            reviewUser.firstName    = parseObject.getString(PARSE_USER_FIRST_NAME_KEY);
            reviewUser.lastName     = parseObject.getString(PARSE_USER_LAST_NAME_KEY);
            reviewUser.mobile       = parseObject.getString(PARSE_USER_MOBILE_KEY);
            reviewUser.district     = parseObject.getString(PARSE_USER_DISTRICT_KEY);
            reviewUser.neighborhood = parseObject.getString(PARSE_USER_NEIGHBORHOOD_KEY);

        }catch (Exception exception) {
            Log.d("Error", "Exception parsing ReviewUser: " + exception.toString());
            exception.printStackTrace();
        }

        return reviewUser;
    }

    /**
     * return ReviewUser object from a parse objectID
     * @param objectID  - object ID for the corresponding ReviewUser
     * @return  - ReviewUser object
     */
    public static ReviewUser fromParseObjectID(String objectID) {
        if (null == objectID) {
            return null;
        }

        try {
            ParseQuery<ParseObject> query   = ParseQuery.getQuery(PARSE_USER_TABLE_NAME);
            return  ReviewUser.fromParseObject(query.get(objectID));
        }
        catch (Exception exception) {
            Log.d("Error", "Cannot retrieve " + objectID + " from the ReviewUser table:" + exception.toString());
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * return a parse object of the Application User
     * @return
     */
    public static ParseObject parseUserObject()
    {
        return ParseObject.createWithoutData(PARSE_USER_TABLE_NAME, TEMP_USER_ID);
    }


    public String getParseID() {
        return parseID;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {

        return firstName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getDistrict() {

        return district;
    }

    public String getNeighborhood() {

        return neighborhood;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(parseID);
        out.writeString(firstName);
        out.writeString(lastName);
        out.writeString(mobile);
        out.writeString(district);
        out.writeString(neighborhood);
    }

    /**
     * static variable for parcelable
     */
    public static final Parcelable.Creator<ReviewUser> CREATOR = new Parcelable.Creator<ReviewUser>() {
        @Override
        public ReviewUser createFromParcel(Parcel in) {
            return new ReviewUser(in);
        }

        @Override
        public ReviewUser[] newArray(int size) {
            return new ReviewUser[size];
        }
    };

    /**
     * constructer that is built using the parcel
     * @param in
     */
    private ReviewUser(Parcel in) {
        parseID         = in.readString();
        firstName	    = in.readString();
        lastName		= in.readString();
        mobile  		= in.readString();
        district	    = in.readString();
        neighborhood	= in.readString();
    }
}
