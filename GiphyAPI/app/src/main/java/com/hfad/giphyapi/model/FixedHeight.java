package com.hfad.giphyapi.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by matthewtduffin on 09/08/16.
 */
public class FixedHeight {

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
