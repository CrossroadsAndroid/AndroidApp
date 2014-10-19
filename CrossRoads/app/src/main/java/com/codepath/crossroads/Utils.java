package com.codepath.crossroads;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.codepath.crossroads.models.DonorOffer;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ar on 10/18/14.
 */
public class Utils {

    public static Bitmap getImageForView(String path, ImageView ivImage) {
        Log.e("", "Path: " + path);
        int targetW = 100;
        int targetH = 100;

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        return bitmap;
    }

    // FIXME do not query in UI thread
    public static ArrayList<DonorOffer> getPendingOffers() {
        // FIXME add a user id
        ParseQuery<ParseObject> query = ParseQuery.getQuery("aroffers")
                .fromLocalDatastore()
                .whereMatches("state", "PENDING");

        ArrayList<DonorOffer> results = new ArrayList<DonorOffer>();

        try {
            List<ParseObject> qResults = query.find();
            Log.i("", "Found " + String.valueOf(qResults.size()));
            for (int i = 0; i < qResults.size(); i++) {
                results.add(new DonorOffer(qResults.get(i)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("", "Query failed");
        }
        return results;
    }

    // FIXME do not query in UI thread
    public static ArrayList<DonorOffer> getSubmittedOffers() {
        // FIXME add a user id
        ParseQuery<ParseObject> query = ParseQuery.getQuery("aroffers")
                .fromLocalDatastore()
                .whereMatches("state", "REQUIRE_REVIEW");

        ArrayList<DonorOffer> results = new ArrayList<DonorOffer>();

        try {
            List<ParseObject> qResults = query.find();
            Log.i("", "Found " + String.valueOf(qResults.size()));
            for (int i = 0; i < qResults.size(); i++) {
                results.add(new DonorOffer(qResults.get(i)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("", "Query failed");
        }
        return results;

    }
}
