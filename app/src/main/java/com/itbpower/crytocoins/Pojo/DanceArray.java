package com.itbpower.crytocoins.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DanceArray implements Serializable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("videoId")
    @Expose
    private String videoID;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

}
