package com.sammekl.openspeedmap.utils;

/**
 * Created by Samme on 20-6-2015.
 */
public class Constants {

    // API //

    public static final String PREF_API_GET_ROADS_URL = "http://overpass-api.de/api/interpreter?data=[out:json];" +
            "(way(around:%s,%s,%s)" +
            "[highway!=cycleway]" +
            "[highway!=footway]" +
            "[highway!=bridleway]" +
            "[highway!=steps]" +
            "[highway!=path]" +
            "[maxspeed!~'.']" +
            "[name]" +
            "[highway];%%3E;);out;";

    public static final String PREF_API_ELEMENTS = "elements";
    public static final String PREF_API_TYPE = "type";

    // WAY //
    public static final String PREF_API_WAY = "way";

    public static final String PREF_API_WAY_ID = "id";
    public static final String PREF_API_WAY_TAGS = "tags";
    public static final String PREF_API_WAY_LANES = "lanes";
    public static final String PREF_API_WAY_MAXSPEED = "maxspeed";
    public static final String PREF_API_WAY_MAXSPEED_CONDITIONAL = "maxspeed:conditional";
    public static final String PREF_API_WAY_ROAD_REF = "ref";
    public static final String PREF_API_WAY_ROAD_NAME = "name";
    public static final String PREF_API_NO_ROAD_NAME = "Onbekende weg";
    public static final String PREF_API_WAY_NODES = "nodes";

    // NODE //
    public static final String PREF_API_NODE = "node";
    public static final String PREF_API_NODE_ID = "id";
    public static final String PREF_API_NODE_LAT = "lat";
    public static final String PREF_API_NODE_LON = "lon";
}
