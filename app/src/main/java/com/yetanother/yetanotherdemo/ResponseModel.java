package com.yetanother.yetanotherdemo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mouli on 5/6/15.
 */
public class ResponseModel {
    @SerializedName("server_time")
    private String serverTime;
    MarkerModel[] markers;
    private String title;

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public MarkerModel[] getMarkers() {
        return markers;
    }

    public void setMarkers(MarkerModel[] markers) {
        this.markers = markers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
