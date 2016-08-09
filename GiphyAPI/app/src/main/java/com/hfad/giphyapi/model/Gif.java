package com.hfad.giphyapi.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * Created by matthewtduffin on 09/08/16.
 */

public class Gif {
    @SerializedName("id")
    private String id;

    @SerializedName("url")
    private String url;

    public Gif(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
