package com.codepath.crossroads.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.codepath.crossroads.IdGen;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by tonyleung on 10/12/14.
 */
public class DonorItem implements Parcelable {
    public static final Parcelable.Creator<DonorItem> CREATOR
            = new Parcelable.Creator<DonorItem>() {
        public DonorItem createFromParcel(Parcel in) {
            return new DonorItem(in);
        }

        public DonorItem[] newArray(int size) {
            return new DonorItem[size];
        }
    };
    public String objId;
    ParseObject obj;
    private String desc;

    public DonorItem(String desc, String localPath) {
        obj = new ParseObject("aritems");
        obj.put("desc", desc);
        obj.put("localpath", localPath);
        noThrowSave();
    }

    public DonorItem(ParseObject obj) {
        Log.i("", "Construct with id" + obj.getString("local_id"));
        this.obj = obj;
        noThrowSave();
    }

    private DonorItem(Parcel in) {
        objId = in.readString();
        getObj();
    }

    public ParseObject getObj() {
        if (obj == null) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("aritems");
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

    public String getDesc() {
        return getObj().getString("desc");
    }

    public void setDesc(String desc) {
        obj.put("desc", desc);
        noThrowSave();
    }

    public String getLocalPath() {
        return getObj().getString("localpath");
    }

    public void setLocalPath(String localPath) {
        obj.put("localpath", localPath);
        noThrowSave();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(objId);
    }

    private void noThrowSave() {
        String id = obj.getString("local_id");
        if (id == null || id.length() == 0) {
            id = IdGen.get();
            obj.put("local_id", id);
        }
        try {
            Log.i("", obj.getString("desc"));
            Log.i("", obj.getString("localpath"));
            obj.pin();
            obj.saveEventually();
        } catch (Exception e) {
            Log.e("", "Unable to save object");
        }

        objId = obj.getString("local_id");
        Log.i("", "Saved " + objId);
    }

    public String getLocalId() {
        return getObj().getString("local_id");
    }
}
