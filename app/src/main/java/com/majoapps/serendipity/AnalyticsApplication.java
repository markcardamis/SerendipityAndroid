package com.majoapps.serendipity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by Mark on 16/02/2016.
 */
public class AnalyticsApplication extends Application {

    private Tracker mTracker;
    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker("UA-73844028-4");
            mTracker.enableAutoActivityTracking(true);
        }
        return mTracker;
    }



}



