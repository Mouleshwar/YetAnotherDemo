package com.yetanother.yetanotherdemo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mouli on 5/6/15.
 */
public class MarkerModel {
    private int full;
    private String description;
    @SerializedName("course_class")
    private String courseClass;
    private String route;
    @SerializedName("idle_time")
    private String idleTime;
    private int display;
    private int discrete;
    private int idle;
    @SerializedName("lastupdated_time")
    private String lastUpdatesTime;
    private String lwidth;
    private String lat;
    private String lng;
    private String speed;
    private String id;
    private String rid;

    public int getFull() {
        return full;
    }

    public void setFull(int full) {
        this.full = full;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseClass() {
        return courseClass;
    }

    public void setCourseClass(String courseClass) {
        this.courseClass = courseClass;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(String idleTime) {
        this.idleTime = idleTime;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public int getDiscrete() {
        return discrete;
    }

    public void setDiscrete(int discrete) {
        this.discrete = discrete;
    }

    public int getIdle() {
        return idle;
    }

    public void setIdle(int idle) {
        this.idle = idle;
    }

    public String getLastUpdatesTime() {
        return lastUpdatesTime;
    }

    public void setLastUpdatesTime(String lastUpdatesTime) {
        this.lastUpdatesTime = lastUpdatesTime;
    }

    public String getLwidth() {
        return lwidth;
    }

    public void setLwidth(String lwidth) {
        this.lwidth = lwidth;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }
}
