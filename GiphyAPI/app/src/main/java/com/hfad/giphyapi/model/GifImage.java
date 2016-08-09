package com.hfad.giphyapi.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by matthewtduffin on 09/08/16.
 */
public class GifImage {

    @SerializedName("fixed_height")
    private FixedHeight fixed_height;

    @SerializedName("fixed_height_still")
    private FixedHeight fixed_height_still;

    public FixedHeight getFixed_height() {
        return fixed_height;
    }

    public void setFixed_height(FixedHeight fixed_height) {
        this.fixed_height = fixed_height;
    }

    public FixedHeight getFixed_height_still() {
        return fixed_height_still;
    }

    public void setFixed_height_still(FixedHeight fixed_height_still) {
        this.fixed_height_still = fixed_height_still;
    }
}
