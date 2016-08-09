package com.hfad.giphyapi.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by matthewtduffin on 09/08/16.
 */
public class GifImage {

    @SerializedName("fixed_height")
    private FixedHeight fixed_height;

    public FixedHeight getFixed_height() {
        return fixed_height;
    }

    public void setFixed_height(FixedHeight fixed_height) {
        this.fixed_height = fixed_height;
    }
}
