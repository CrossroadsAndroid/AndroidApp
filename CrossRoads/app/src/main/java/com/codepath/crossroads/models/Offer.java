package com.codepath.crossroads.models;

import android.util.Log;

import com.codepath.crossroads.models.*;


import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonyleung on 10/12/14.
 */
public class Offer {

    String          reviewState;
    User            donor;
    User            reviewer;
    ArrayList<Item> items;


    private static final String	PARSE_OFFER_REVIEW_STATE_KEY        = "reviewState";
    private static final String	PARSE_OFFER_DONOR_KEY               = "donor";
    private static final String	PARSE_OFFER_REVIEWER_KEY            = "reviewer";
    private static final String	PARSE_OFFER_ITEMS_KEY               = "items";


    private static final String PARSE_OFFER_NEEDS_REVIEWER_VALUE    = "NeedsReviewer";
    private static final String PARSE_OFFER_UNDER_REVIEW_VALUE      = "UnderReview";
    private static final String PARSE_OFFER_REVIEW_COMPLETED_VALUE  = "ReviewCompleted";

    public static Offer fromParse(ParseObject parseObject) {
        Offer offer = new Offer();
        try {
            offer.reviewState       = parseObject.getString(PARSE_OFFER_REVIEW_STATE_KEY);
            offer.donor             = Offer.getUserFromParseObject(parseObject, PARSE_OFFER_DONOR_KEY);
            offer.reviewer          = Offer.getUserFromParseObject(parseObject, PARSE_OFFER_REVIEWER_KEY);

            offer.items              = new ArrayList<Item>();
            List<String> itemIds    = parseObject.getList(PARSE_OFFER_ITEMS_KEY);

            if (null != itemIds) {
                for (int i = 0; i < itemIds.size(); i++) {
                    Item item   = Item.fromParseObjectID(itemIds.get(i));

                    if (null != item) {
                        offer.items.add(item);
                    }
                }
            }

        } catch (Exception exception) {
            Log.d("Error", "Exception parsing Offer: " + exception.toString());
            exception.printStackTrace();
        }
        return offer;
    }

    /**
     * returns an array of offers that needs reviewers
     * @return
     */
    public static ArrayList<Offer> getNeedsReviewerOfferList() {
        ArrayList<Offer> offers = new ArrayList<Offer>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Offer");
        query.whereEqualTo(PARSE_OFFER_REVIEW_STATE_KEY, PARSE_OFFER_NEEDS_REVIEWER_VALUE);
        try {
            List<ParseObject> parseObjects  =  query.find();

            for (int i = 0; i < parseObjects.size(); i++) {
                Offer offer = Offer.fromParse(parseObjects.get(i));
                offers.add(offer);
            }

        } catch (Exception exception) {

        }
        return offers;
    }


    /**
     * returns an array of offers under the user's review
     * @return
     */
    public static ArrayList<Offer> getOffersUnderUserReview() {
        ArrayList<Offer> offers = new ArrayList<Offer>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Offer");
        query.whereEqualTo(PARSE_OFFER_REVIEW_STATE_KEY, PARSE_OFFER_UNDER_REVIEW_VALUE);

        ParseObject user    = ParseObject.createWithoutData("User", "N069yaMv1E");
        query.whereEqualTo(PARSE_OFFER_REVIEWER_KEY, user);

        try {
            List<ParseObject> parseObjects  =  query.find();

            for (int i = 0; i < parseObjects.size(); i++) {
                Offer offer = Offer.fromParse(parseObjects.get(i));
                offers.add(offer);
            }

        } catch (Exception exception) {

        }
        return offers;
    }


    /**
     * returns an array of offers that have completed the review
     * @return
     */
    public static ArrayList<Offer> getOffersReviewsCompleted() {
        ArrayList<Offer> offers = new ArrayList<Offer>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Offer");
        query.whereEqualTo(PARSE_OFFER_REVIEW_STATE_KEY, PARSE_OFFER_REVIEW_COMPLETED_VALUE);
        try {
            List<ParseObject> parseObjects  =  query.find();

            for (int i = 0; i < parseObjects.size(); i++) {
                Offer offer = Offer.fromParse(parseObjects.get(i));
                offers.add(offer);
            }

        } catch (Exception exception) {

        }
        return offers;
    }


    /**
     * return an user given the parse object and key
     * @param parseObject
     * @param key
     * @return
     */
    private static User getUserFromParseObject(ParseObject parseObject, String key) {
        // parse reviewer
        ParseObject reviewerObject  = parseObject.getParseObject(key);
        // only parse reviewer if it exists
        if (null != reviewerObject) {
            return User.fromParseObjectID(reviewerObject.getObjectId());
        }

        return null;
    }


    public String getReviewState() {
        return reviewState;
    }

    public User getDonor() {
        return donor;
    }

    public User getReviewer() {
        return reviewer;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

}
