package com.codepath.crossroads.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by tonyleung on 10/12/14.
 */
public class User implements Parcelable {

    String                      parseID;
    String                      firstName;
    String                      lastName;
    String                      mobile;
    String                      district;
    String                      neighborhood;


    public static  String USER_ID;
    private static final String	PARSE_USER_TABLE_NAME           = "User2";

    private static final String	PARSE_USER_FIRST_NAME_KEY       = "firstName";
    private static final String	PARSE_USER_LAST_NAME_KEY        = "lastName";
    private static final String	PARSE_USER_MOBILE_KEY           = "mobile";
    private static final String	PARSE_USER_DISTRICT_KEY         = "district";
    private static final String	PARSE_USER_NEIGHBORHOOD_KEY     = "neighborhood";


    public User() {
        super();
    }

    /**
     * Factory method that uses the given parse object to return an User object
     * @param parseObject - parse object that corresponds to an User
     * @return User object
     */
    public static User fromParseObject(ParseObject parseObject) {

        if (null == parseObject) {
            return null;
        }

        User user = new User();

        try {
            user.parseID      = parseObject.getObjectId();

            user.firstName    = parseObject.getString(PARSE_USER_FIRST_NAME_KEY);
            user.lastName     = parseObject.getString(PARSE_USER_LAST_NAME_KEY);
            user.mobile       = parseObject.getString(PARSE_USER_MOBILE_KEY);
            user.district     = parseObject.getString(PARSE_USER_DISTRICT_KEY);
            user.neighborhood = parseObject.getString(PARSE_USER_NEIGHBORHOOD_KEY);

        }catch (Exception exception) {
            Log.d("Error", "Exception parsing User: " + exception.toString());
            exception.printStackTrace();
        }

        return user;
    }

    /**
     * return User object from a parse objectID
     * @param objectID  - object ID for the corresponding User
     * @return  - User object
     */
    public static User fromParseObjectID(String objectID) {
        if (null == objectID) {
            return null;
        }

        try {
            ParseQuery<ParseObject> query   = ParseQuery.getQuery(PARSE_USER_TABLE_NAME);
            return  User.fromParseObject(query.get(objectID));
        }
        catch (Exception exception) {
            Log.d("Error", "Cannot retrieve " + objectID + " from the User table:" + exception.toString());
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
        return ParseObject.createWithoutData(PARSE_USER_TABLE_NAME, USER_ID);
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
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    /**
     * constructer that is built using the parcel
     * @param in
     */
    private User(Parcel in) {
        parseID         = in.readString();
        firstName	    = in.readString();
        lastName		= in.readString();
        mobile  		= in.readString();
        district	    = in.readString();
        neighborhood	= in.readString();
    }
}
