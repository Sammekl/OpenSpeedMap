package com.sammekl.openspeedmap.helpers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Samme on 25-6-2015.
 */
public class PreferenceHelper {

    public static String readDefaultPreference(String key, Activity activity) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(activity);
        return settings.getString(key, "");
    }
}
