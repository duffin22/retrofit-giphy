package com.hfad.giphyapi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by matthewtduffin on 09/08/16.
 */

public class GifResponse {

    @SerializedName("data")
    private List<Gif> data;

    public List<Gif> getData() {
        return data;
    }

    public void setData(List<Gif> data) {
        this.data = data;
    }
}
