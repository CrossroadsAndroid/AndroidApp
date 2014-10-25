package com.codepath.crossroads.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.UUID;

/**
 * Created by ar on 10/25/14.
 */
@ParseClassName("Item")
public class ParseItem extends ParseObject {
    public static ParseQuery<ParseItem> getQuery() {
        return ParseQuery.getQuery(ParseItem.class);
    }

    public String getDetails() {
        return getString("details");
    }

    public void setDetails(String details) {
        put("details", details);
    }

    public String getCondition() {
        return getString("condition");
    }

    public void setCondition(String condition) {
        put("condition", condition);
    }

    public String getState() {
        return getString("state");
    }

    public boolean isOffline() {
        return getBoolean("is_offline");
    }

    public void setIsOffline(boolean isOffline) {
        put("is_offline", isOffline);
    }

    public void setUuidString() {
        UUID uuid = UUID.randomUUID();
        put("uuid", uuid.toString());
    }


    // FIXME:
    // Photo
    // Link to offer etc.
}
