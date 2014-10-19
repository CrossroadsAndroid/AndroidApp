package com.codepath.crossroads.models;

import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.security.PrivateKey;

/**
 * Created by tonyleung on 10/12/14.
 */
public class User {

    String                      firstName;
    String                      lastName;
    String                      district;
    String                      neighborhood;

    private static final String TEMP_USER_ID                    = "N069yaMv1E";
    private static final String	PARSE_USER_TABLE_NAME           = "User";

    private static final String	PARSE_USER_FIRST_NAME_KEY       = "firstName";
    private static final String	PARSE_USER_LAST_NAME_KEY        = "lastName";
    private static final String	PARSE_USER_DISTRICT_KEY         = "district";
    private static final String	PARSE_USER_NEIGHBORHOOD_KEY     = "neighborhood";

    /**
     * Factory method that uses the given parse object to return an User object
     * @param parseObject - parse object that corresponds to an User
     * @return User object
     */
    public static User fromParseObject(ParseObject parseObject) {

        if (null == parseObject) {
            return null;
        }

        User user               = new User();

        try {
            user.firstName      = parseObject.getString("firstName");
            user.lastName       = parseObject.getString(PARSE_USER_LAST_NAME_KEY);
            user.district       = parseObject.getString(PARSE_USER_DISTRICT_KEY);
            user.neighborhood   = parseObject.getString(PARSE_USER_NEIGHBORHOOD_KEY);

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
     * return the userID of the current user
     * @return
     */
    public static String userID()
    {
        return TEMP_USER_ID;
    }



    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {

        return firstName;
    }

    public String getDistrict() {

        return district;
    }

    public String getNeighborhood() {

        return neighborhood;
    }
}
