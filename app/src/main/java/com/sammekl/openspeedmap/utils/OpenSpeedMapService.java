package com.sammekl.openspeedmap.utils;

import android.app.Activity;

import com.sammekl.openspeedmap.model.Highway;
import com.sammekl.openspeedmap.tasks.GetHighwayTask;
import com.sammekl.openspeedmap.tasks.RoadTask;
import com.sammekl.openspeedmap.tasks.UpdateTask;

/**
 * Created by Samme on 20-6-2015.
 */
public class OpenSpeedMapService {

    public void getHighwayData(final Activity activity, int range, double latitude, double longitude) {
        final RoadTask roadTask = new RoadTask(activity);
        String url = String.format(Constants.PREF_API_GET_ROADS_URL, range, latitude, longitude);
        roadTask.setUrl(url);
        roadTask.execute();
    }
    public void getHighwayXml(final Activity activity, Highway highway, int maxspeed) {
        final GetHighwayTask getHighwayTask = new GetHighwayTask(activity);
        String url = Constants.PREF_API_GET_WAY + highway.getId();
        getHighwayTask.setUrl(url);
        getHighwayTask.setUsername("sammekleijn@gmail.com");
        getHighwayTask.setPassword("zwTjD1QMIzYVOn152oIv");
        getHighwayTask.setMaxspeed(maxspeed);
        getHighwayTask.execute();
    }

    public void updateSpeed(Activity activity, Highway highway, String xml) {
        final UpdateTask updateTask = new UpdateTask(activity);
        String url = Constants.PREF_API_UPDATE_WAY + highway.getId();
        updateTask.setUrl(url);
        updateTask.setUsername("sammekleijn@gmail.com");
        updateTask.setPassword("zwTjD1QMIzYVOn152oIv");
        updateTask.setXml(xml);
        updateTask.execute();

    }
}
