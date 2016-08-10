package com.hfad.giphyapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.giphyapi.R;
import com.hfad.giphyapi.api_stuff.ApiInterface;
import com.hfad.giphyapi.api_stuff.ApiClient;
import com.hfad.giphyapi.model.FixedHeight;
import com.hfad.giphyapi.model.Gif;
import com.hfad.giphyapi.model.GifImage;
import com.hfad.giphyapi.model.GifResponse;
import com.koushikdutta.ion.Ion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MATT-TEST";
    private final static String API_KEY = "dc6zaTOxFJmzC";

    private ImageView immyStill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        immyStill = (ImageView) findViewById(R.id.imageView2);

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
                        final String gifUrl = fixyGif.getUrl();

                        FixedHeight fixyStill = gifImage.getFixed_height_still();
                        final String stillUrl = fixyStill.getUrl();

                        Button magicButton = (Button) findViewById(R.id.magicButton);
                        magicButton.setVisibility(View.VISIBLE);

                        ImageView immyGif = (ImageView) findViewById(R.id.imageView);

                        Ion.with(immyGif)
                                .placeholder(R.color.colorAccent)
                                .error(R.color.colorPrimary)
                                .load(gifUrl);

                        Ion.with(immyStill)
                                .placeholder(R.color.colorAccent)
                                .error(R.color.colorPrimary)
                                .load(stillUrl);

                        magicButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "Magic Button Pressed!", Toast.LENGTH_SHORT).show();

                                new ImageProcessingAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,stillUrl);

                            }
                        });


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


    private class ImageProcessingAsyncTask extends AsyncTask<String,Integer,Bitmap> {
        int code;

        public ImageProcessingAsyncTask() {
        }


        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                Log.i(TAG,"Url is "+params[0]);
                return invertImageColors(image);
            } catch (MalformedURLException e) {
                Log.i(TAG, "Malformed URL");
            } catch (IOException e) {
                Log.i(TAG,"IOException");
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
//            mProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            immyStill.setImageBitmap(bitmap);
//            mProgressBar.setVisibility(View.INVISIBLE);

//      final int left = mImageView.getLeft();
//      final int top = mImageView.getTop();
//
//      final int[] location = new int[2];
//      mImageView.getLocationOnScreen(location);


//      Log.i("MATT_TEST", "left/top: ("+left+","+top +")");

//            mImageView.setOnTouchListener(new View.OnTouchListener(){
//                @Override
//                public boolean onTouch(View v, MotionEvent event){
//
//                    Bitmap bitty = ((BitmapDrawable)mImageView.getDrawable()).getBitmap();
//                    Bitmap mutableBitty = bitty.copy(bitty.getConfig(),true);
//
//                    int x = (int)event.getX();
//                    int y = (int)event.getY();
//
//                    int px = mutableBitty.getPixel(x,y);
//                    int red = Color.red(px);
//                    int green = Color.green(px);
//                    int blue = Color.blue(px);
//                    int avg = (red+blue+green)/3;
//                    int rev = Color.argb(255,avg,avg,avg);
//
//                    for (int i = x; i < x+50 && i <mutableBitty.getWidth(); i++) {
//                        for(int j = y; j < y+50 && i <mutableBitty.getHeight(); j++){
//                            mutableBitty.setPixel(i, j, rev);
//                        }
//                    }
//
//                    mImageView.setImageBitmap(mutableBitty);
//
//                    Log.i("MATT-TEST","RGB: ("+red+","+green+","+blue+")");
//
//                    return true;
//                }
//            });

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mProgressBar.setVisibility(View.VISIBLE);
//            mProgressBar.setProgress(0);
        }

        private Bitmap invertImageColors(Bitmap bitmap){
            //You must use this mutable Bitmap in order to modify the pixels
            Bitmap mutableBitmap = bitmap.copy(bitmap.getConfig(),true);

            //Loop through each pixel, and invert the colors
            for (int i = 0; i < mutableBitmap.getWidth(); i++) {
                for(int j = 0; j < mutableBitmap.getHeight(); j++){
                    int px = mutableBitmap.getPixel(i,j);
                    int red = Color.red(px);
                    int green = Color.green(px);
                    int blue = Color.blue(px);
                    int rev = Color.argb(255,255-red,255-green,255-blue);
                    mutableBitmap.setPixel(i,j,rev);
                }
//                int progressVal = Math.round((long) (100*(i/(1.0*mutableBitmap.getWidth()))));
//                mProgressBar.setProgress(progressVal);
            }
            return mutableBitmap;
        }

    }
}
