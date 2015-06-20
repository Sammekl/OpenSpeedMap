package com.sammekl.openspeedmap.model;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Samme on 20-6-2015.
 */
public class Highway {

    private long id;
    private int lanes;
    private int maxSpeed;
    private int maxSpeedConditional;
    private String roadName;

    private List<Long> nodes;

    public Highway() {

    }

    public Highway(long id, int lanes, int maxSpeed, int maxSpeedConditional, String roadName, List<Long> nodes) {
        this.id = id;
        this.lanes = lanes;
        this.maxSpeed = maxSpeed;
        this.maxSpeedConditional = maxSpeedConditional;
        this.roadName = roadName;
        this.nodes = nodes;
    }

    public int getLanes() {
        return lanes;
    }

    public void setLanes(int lanes) {
        this.lanes = lanes;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getMaxSpeedConditional() {
        return maxSpeedConditional;
    }

    public void setMaxSpeedConditional(int maxSpeedConditional) {
        this.maxSpeedConditional = maxSpeedConditional;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public List<Long> getNodes() {
        return nodes;
    }

    public void setNodes(List<Long> nodes) {
        this.nodes = nodes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
