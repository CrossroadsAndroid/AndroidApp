package com.codepath.crossroads.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;
import java.util.UUID;

/**
 * Created by ar on 10/25/14.
 */
@ParseClassName("Offer")
public class ParseOffer extends ParseObject {
    public static ParseQuery<ParseOffer> getQuery() {
        return ParseQuery.getQuery(ParseOffer.class);
    }

    public String getState() {
        return getString("state");
    }

    public void setState(String state) {
        put("state", state);
    }

    public ParseObject getDonor() {
        return getParseObject("donor");
    }

    public void setDonor(ParseObject currentUser) {
        put("donor", currentUser);
    }

    public void addItems(List<ParseItem> items) {
        addAllUnique("items", items);
    }

    public List<ParseItem> getItems() {
        return getList("items");
    }

    public void addItem(ParseItem item) {
        addUnique("items", item);
    }

    public void setUUID() {
        UUID uuid = UUID.randomUUID();
        put("uuid", "offer-" + uuid.toString());
    }

    public String getUUID() {
        return getString("uuid");
    }
}
