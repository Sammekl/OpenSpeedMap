package com.sammekl.openspeedmap.utils;

import android.app.Activity;

import com.sammekl.openspeedmap.tasks.RoadTask;

/**
 * Created by Samme on 20-6-2015.
 */
public class OpenSpeedMapService {

    public void getHighwayData(final Activity activity, int range, double latitude, double longitude) {
        final RoadTask roadTask = new RoadTask(activity);
        String url = String.format(Constants.PREF_API_GET_ROADS_URL, range, latitude, longitude);
    }
}
