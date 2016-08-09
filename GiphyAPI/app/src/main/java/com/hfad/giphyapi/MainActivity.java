package com.hfad.giphyapi;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hfad.giphyapi.R;
import com.hfad.giphyapi.api_stuff.ApiInterface;
import com.hfad.giphyapi.api_stuff.ApiClient;
import com.hfad.giphyapi.model.FixedHeight;
import com.hfad.giphyapi.model.Gif;
import com.hfad.giphyapi.model.GifImage;
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

        final InputMethodManager inputManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        final ImageView searchButton = (ImageView) findViewById(R.id.searchButton);
        final EditText searchText = (EditText) findViewById(R.id.editText);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = searchText.getText().toString();

                searchText.clearFocus();
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


                Call<GifResponse> call = apiService.getGif(API_KEY, text,1);

                call.enqueue(new Callback<GifResponse>() {
                    @Override
                    public void onResponse(Call<GifResponse> call, Response<GifResponse> response) {
                        int statusCode = response.code();

                        Log.i(TAG,"Response code is "+ statusCode);

                        List<Gif> gifs = response.body().getData();

                        GifImage gifImage = gifs.get(0).getImage();


                        FixedHeight fixyGif = gifImage.getFixed_height();
                        String gifUrl = fixyGif.getUrl();

                        FixedHeight fixyStill = gifImage.getFixed_height_still();
                        String stillUrl = fixyStill.getUrl();

                        ImageView immyGif = (ImageView) findViewById(R.id.imageView);

                        Ion.with(immyGif)
                                .placeholder(R.color.colorAccent)
                                .error(R.color.colorPrimary)
                                .load(gifUrl);


                        ImageView immyStill = (ImageView) findViewById(R.id.imageView2);

                        Ion.with(immyStill)
                                .placeholder(R.color.colorAccent)
                                .error(R.color.colorPrimary)
                                .load(stillUrl);




                        Log.i(TAG,"URL is: "+gifUrl);
                    }

                    @Override
                    public void onFailure(Call<GifResponse> call, Throwable t) {
                        Log.i(TAG,"onFailure for callback");
                        t.printStackTrace();
                    }
                });


            }
        });








    }
}
