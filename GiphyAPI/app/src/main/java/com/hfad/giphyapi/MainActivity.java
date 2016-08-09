package com.hfad.giphyapi;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.hfad.giphyapi.R;
import com.hfad.giphyapi.api_stuff.ApiInterface;
import com.hfad.giphyapi.api_stuff.ApiClient;
import com.hfad.giphyapi.model.Gif;
import com.hfad.giphyapi.model.GifResponse;
import com.koushikdutta.ion.Ion;

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

                String url = gifs.get(0).getUrl();
                String id = gifs.get(0).getId();

                TextView texty = (TextView) findViewById(R.id.textView);
                texty.setText(id);

                ImageView immy = (ImageView) findViewById(R.id.imageView);

                Ion.with(immy)
                        .placeholder(R.color.colorAccent)
                        .error(R.color.colorPrimary)
//                        .animateLoad(AnimationUtils.loadAnimation(DetailViewActivity.this, R.anim.rotator))
//                        .animateIn(R.anim.fade_in_long)
                        .load(url);

                Log.i(TAG,"URL is: "+url);
            }

            @Override
            public void onFailure(Call<GifResponse> call, Throwable t) {
                Log.i(TAG,"onFailure for callback");
            }
        });






    }
}
