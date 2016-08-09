package com.hfad.giphyapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hfad.giphyapi.R;
import com.hfad.giphyapi.api_stuff.ApiInterface;
import com.hfad.giphyapi.api_stuff.ApiClient;
import com.hfad.giphyapi.model.Gif;
import com.hfad.giphyapi.model.GifResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MATT-TEST";
    private final static String API_KEY = "dc6zaTOxFJmzC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<GifResponse> call = apiService.getGif(API_KEY, "funny cat",1);

        call.enqueue(new Callback<GifResponse>() {
            @Override
            public void onResponse(Call<GifResponse> call, Response<GifResponse> response) {
                int statusCode = response.code();
                List<Gif> gifs = response.body().getData();

                Log.i(TAG,"onResponse for callback");
            }

            @Override
            public void onFailure(Call<GifResponse> call, Throwable t) {
                Log.i(TAG,"onFailure for callback");
            }
        });






    }
}
