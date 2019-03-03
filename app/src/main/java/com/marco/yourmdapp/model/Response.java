package com.marco.yourmdapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.marco.yourmdapp.model.primitives.Venue;

import java.util.List;

public class Response {
    @SerializedName("venues")
    @Expose
    private List<Venue> venues;

    public List<Venue> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }
}
