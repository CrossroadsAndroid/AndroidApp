package com.codepath.crossroads.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.codepath.crossroads.IdGen;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonyleung on 10/12/14.
 */
public class DonorOffer implements Parcelable {
    public static final Parcelable.Creator<DonorOffer> CREATOR
            = new Parcelable.Creator<DonorOffer>() {
        public DonorOffer createFromParcel(Parcel in) {
            return new DonorOffer(in);
        }

        public DonorOffer[] newArray(int size) {
            return new DonorOffer[size];
        }
    };
    ParseObject obj;
    String objId;

    public DonorOffer(ArrayList<DonorItem> items) {
        assert items.size() > 0;
        obj = new ParseObject("aroffers");
        ArrayList<ParseObject> pItems = new ArrayList<ParseObject>();
        for (int i = 0; i < items.size(); i++) {
            pItems.add(items.get(i).getObj());
        }
        obj.addAll("items", pItems);
        obj.put("state", "PENDING");
        noThrowSave();
    }

    public DonorOffer(ParseObject obj) {
        Log.i("", "Construct with id" + obj.getString("local_id"));
        this.obj = obj;
        noThrowSave();
    }

    public DonorOffer(Parcel in) {
        objId = in.readString();
        getObj();
    }

    public void add(DonorItem t) {
        getObj().add("items", t.getObj());
        noThrowSave();
    }

    public ParseObject getObj() {
        if (obj == null) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("aroffers");
            query.fromLocalDatastore();
            try {
                obj = query.whereEqualTo("local_id", objId).getFirst();
            } catch (Exception e) {
                Log.e("", "couldn't find object " + objId);
                e.printStackTrace();
            }
        }
        return obj;
    }

    private void noThrowSave() {
        String id = obj.getString("local_id");
        if (id == null || id.length() == 0) {
            id = IdGen.get();
            obj.put("local_id", id);
        }
        try {
            obj.pin();
            obj.saveEventually();
        } catch (Exception e) {
            Log.e("", "Unable to save object");
        }

        objId = obj.getString("local_id");
        Log.i("", "Saved " + objId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(objId);

    }

    public void setSubmitted() {
        obj.put("state", "REQUIRE_REVIEW");
        noThrowSave();
    }

    public ArrayList<DonorItem> getItems() {
        ArrayList<DonorItem> result = new ArrayList<DonorItem>();
        ParseObject o = getObj();
        List<ParseObject> objs = o.getList("items");
        for (int i = 0; i < objs.size(); i++) {
            result.add(new DonorItem(objs.get(i)));
        }
        return result;
    }

}
