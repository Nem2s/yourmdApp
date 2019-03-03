package com.marco.yourmdapp.model.primitives;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Location {
    @SerializedName("formattedAddress")
    @Expose
    private List<String> formattedAddress;

    public List<String> getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(List<String> formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getFullAddress() {
        StringBuilder builder = new StringBuilder();
        for (String s :
                formattedAddress) {
            builder.append(s);
        }
        return builder.toString();
    }
}
