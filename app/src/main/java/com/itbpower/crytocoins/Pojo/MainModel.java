package com.itbpower.crytocoins.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MainModel implements Serializable {
    @SerializedName("danceArray")
    @Expose
    private List<DanceArray> danceArray = null;

    public List<DanceArray> getDanceArray() {
        return danceArray;
    }

    public void setDanceArray(List<DanceArray> danceArray) {
        this.danceArray = danceArray;
    }

}
