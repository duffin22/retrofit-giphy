package com.hfad.giphyapi.api_stuff;

import com.hfad.giphyapi.model.Gif;
import com.hfad.giphyapi.model.GifResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by matthewtduffin on 09/08/16.
 */

public interface ApiInterface {
    @GET("v1/gifs/search")
    Call<GifResponse> getGif(@Query("api_key") String apiKey, @Query("q") String query, @Query("limit") int limit);

}
