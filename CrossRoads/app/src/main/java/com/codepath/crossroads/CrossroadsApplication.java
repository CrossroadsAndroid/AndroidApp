package com.codepath.crossroads;

import android.app.Application;

import com.codepath.crossroads.models.ParseItem;
import com.codepath.crossroads.models.ParseOffer;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by ivan on 10/26/14.
 */
public class CrossroadsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    protected void init() {
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(ParseItem.class);
        ParseObject.registerSubclass(ParseOffer.class);
        Parse.initialize(this, "ZwqdQKWXjs4vs9n22rqL0gQA0mBoFCooSMtA7BBG", "qp27sTi284lAm3u2DxUafAHwGNxiVxecN0DL1JuX");

    }

}
