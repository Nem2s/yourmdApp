package com.marco.yourmdapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.marco.yourmdapp.model.primitives.Venue;

import java.util.List;

public class SearchVenueResponse {

    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}


