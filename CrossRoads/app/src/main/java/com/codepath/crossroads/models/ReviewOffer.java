package com.codepath.crossroads.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonyleung on 10/12/14.
 */
public class ReviewOffer implements Parcelable{

    String                  parseID;
    String                  reviewState;
    User donor;
    User reviewer;
    ArrayList<ReviewItem>   items;

    private static final String	PARSE_OFFER_TABLE_NAME              = "Offer";

    private static final String	PARSE_OFFER_REVIEW_STATE_KEY        = "state";
    private static final String	PARSE_OFFER_DONOR_KEY               = "donor";
    private static final String	PARSE_OFFER_REVIEWER_KEY            = "reviewer";
    private static final String	PARSE_OFFER_ITEMS_KEY               = "items";

    private static final String PARSE_OFFER_NEEDS_REVIEWER_VALUE    = "Submitted";
    public static final  String PARSE_OFFER_UNDER_REVIEW_VALUE      = "UnderReview";
    public static final String PARSE_OFFER_REVIEW_COMPLETED_VALUE   = "ReviewCompleted";


    public ReviewOffer() {
        super();
        items   = new ArrayList<ReviewItem>();
    }

    public static ReviewOffer fromParse(ParseObject parseObject) {
        ReviewOffer offer = new ReviewOffer();
        try {
            offer.parseID               = parseObject.getObjectId();
            offer.reviewState           = parseObject.getString(PARSE_OFFER_REVIEW_STATE_KEY);
            offer.donor                 = User.fromParseObject(parseObject.getParseObject(PARSE_OFFER_DONOR_KEY).fetchIfNeeded());//ReviewOffer.getUserFromParseObject(parseObject, PARSE_OFFER_DONOR_KEY);
            offer.reviewer              = User.fromParseObject(parseObject.getParseObject(PARSE_OFFER_DONOR_KEY).fetchIfNeeded());//ReviewOffer.getUserFromParseObject(parseObject, PARSE_OFFER_REVIEWER_KEY);

            offer.items                 = new ArrayList<ReviewItem>();
            List<ParseObject> items     = parseObject.getList(PARSE_OFFER_ITEMS_KEY);

            if (null != items) {
                for (int i = 0; i < items.size(); i++) {
                    ReviewItem item     = ReviewItem.fromParseObject(items.get(i).fetchIfNeeded());

                    if (null != item) {
                        offer.items.add(item);
                    }
                }
            }

        } catch (Exception exception) {
            Log.d("Error", "Exception parsing ReviewOffer: " + exception.toString());
            exception.printStackTrace();
        }
        return offer;
    }


    /**
     * returns an array of offers that needs reviewers
     * @return
     */
    public static ArrayList<ReviewOffer> getNeedsReviewerOfferList() {
        ArrayList<ReviewOffer> offers = new ArrayList<ReviewOffer>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery(PARSE_OFFER_TABLE_NAME);
        query.whereEqualTo(PARSE_OFFER_REVIEW_STATE_KEY, PARSE_OFFER_NEEDS_REVIEWER_VALUE);
        try {
            List<ParseObject> parseObjects  =  query.find();
            // loop through each parseObject
            for (int i = 0; i < parseObjects.size(); i++) {
                ReviewOffer offer = ReviewOffer.fromParse(parseObjects.get(i));
                offers.add(offer);
            }

        } catch (Exception exception) {
            Log.d("Error", "Fetch Exception");
        }
        return offers;
    }


    /**
     * returns an array of offers under the user's review
     * @return
     */
    public static ArrayList<ReviewOffer> getOffersUnderUserReview() {
        ArrayList<ReviewOffer> offers = new ArrayList<ReviewOffer>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery(PARSE_OFFER_TABLE_NAME);
        query.whereEqualTo(PARSE_OFFER_REVIEW_STATE_KEY, PARSE_OFFER_UNDER_REVIEW_VALUE);
        query.whereEqualTo(PARSE_OFFER_REVIEWER_KEY, User.parseUserObject());

        try {
            List<ParseObject> parseObjects  =  query.find();

            for (int i = 0; i < parseObjects.size(); i++) {
                ReviewOffer offer = ReviewOffer.fromParse(parseObjects.get(i));
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
    public static ArrayList<ReviewOffer> getOffersReviewsCompleted() {
        ArrayList<ReviewOffer> offers = new ArrayList<ReviewOffer>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery(PARSE_OFFER_TABLE_NAME);
        query.whereEqualTo(PARSE_OFFER_REVIEW_STATE_KEY, PARSE_OFFER_REVIEW_COMPLETED_VALUE);
        try {
            List<ParseObject> parseObjects  =  query.find();

            for (int i = 0; i < parseObjects.size(); i++) {
                ReviewOffer offer = ReviewOffer.fromParse(parseObjects.get(i));
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


    /**
     * query the table for the object and modify it. then save
     */
    public void updateOffer() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(PARSE_OFFER_TABLE_NAME);

        // Retrieve the object by id
        query.getInBackground(parseID, new GetCallback<ParseObject>() {
            public void done(ParseObject offer, ParseException exception) {
                if (exception == null) {
                    offer.put(PARSE_OFFER_REVIEW_STATE_KEY, reviewState);
                    offer.put(PARSE_OFFER_REVIEWER_KEY, User.parseUserObject());
                    offer.saveInBackground();
                }
                else {
                    Log.d("Error", exception.toString());
                }
            }
        });
    }

    public String getReviewState() {
        return reviewState;
    }

    public void setReviewState(String reviewState) {
        this.reviewState    = reviewState;
    }

    public User getDonor() {
        return donor;
    }

    public void setDonor(User donor) {
        this.donor  = donor;
    }

    public User getReviewer() {
        return reviewer;
    }

    public ArrayList<ReviewItem> getItems() {
        return items;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(parseID);
        out.writeString(reviewState);
        out.writeParcelable(donor, flags);
        out.writeParcelable(reviewer, flags);
        out.writeList(items);
    }

    /**
     * static variable for parcelable
     */
    public static final Parcelable.Creator<ReviewOffer> CREATOR = new Parcelable.Creator<ReviewOffer>() {
        @Override
        public ReviewOffer createFromParcel(Parcel in) {
            return new ReviewOffer(in);
        }

        @Override
        public ReviewOffer[] newArray(int size) {
            return new ReviewOffer[size];
        }
    };

    /**
     * constructer that is built using the parcel
     * @param in
     */
    private ReviewOffer(Parcel in) {
        this();
        parseID         = in.readString();
        reviewState	    = in.readString();
        donor           = in.readParcelable(User.class.getClassLoader());
        reviewer        = in.readParcelable(User.class.getClassLoader());
        items           = in.readArrayList(ReviewItem.class.getClassLoader());
    }
}
