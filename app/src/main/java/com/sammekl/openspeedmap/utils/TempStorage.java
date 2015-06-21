package com.sammekl.openspeedmap.utils;

import com.sammekl.openspeedmap.model.Highway;
import com.sammekl.openspeedmap.model.Node;

import java.util.List;

/**
 * Created by Samme on 20-6-2015.
 */
public class TempStorage {

    public static List<Highway> allHighways;
    public static List<Node> allNodes;

    public static String updateCall = "<osm> %s </osm>";

    public static List<Highway> getAllHighways() {
        return allHighways;
    }

    public static void setAllHighways(List<Highway> allHighways) {
        TempStorage.allHighways = allHighways;
    }

    public static List<Node> getAllNodes() {
        return allNodes;
    }

    public static void setAllNodes(List<Node> allNodes) {
        TempStorage.allNodes = allNodes;
    }

    public static String getUpdateCall() {
        return updateCall;
    }

    public static void setUpdateCall(String updateCall) {
        TempStorage.updateCall = String.format(TempStorage.updateCall, updateCall);
    }
}
