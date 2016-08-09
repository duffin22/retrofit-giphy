package generalassembly.yuliyakaleda.solution_code_thread_safe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity{
  private static final String TAG = MainActivity.class.getName();
  private static final int PICK_IMAGE_REQUEST = 1;
  private static final int PICK_IMAGE_REQUEST2 = 2;
  private static final int PICK_IMAGE_REQUEST3 = 3;
  private static final int PICK_IMAGE_REQUEST4 = 4;
  private ImageView mImageView;
  private Button mChooseButton, mChooseButton2, mChooseButton3, mChooseButton4;
  private ProgressBar mProgressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mChooseButton = (Button) findViewById(R.id.choose_button);
    mChooseButton2 = (Button) findViewById(R.id.choose_button2);
    mChooseButton3 = (Button) findViewById(R.id.choose_button3);
    mChooseButton4 = (Button) findViewById(R.id.choose_button4);
    mImageView = (ImageView) findViewById(R.id.image);
    mProgressBar = (ProgressBar)findViewById(R.id.progress);
    mProgressBar.setMax(100);

    mImageView.setImageResource(R.drawable.placeholder);
    mChooseButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        selectImage();
      }
    });

    mChooseButton2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        selectImage2();
      }
    });

    mChooseButton3.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        selectImage3();
      }
    });

    mChooseButton4.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        selectImage4();
      }
    });
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == PICK_IMAGE_REQUEST && resultCode == MainActivity.RESULT_OK && null != data) {
      Uri selectedImage = data.getData();

      new ImageProcessingAsyncTask(1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,selectedImage);
    } else if (requestCode == PICK_IMAGE_REQUEST2 && resultCode == MainActivity.RESULT_OK && null != data) {
      Uri selectedImage = data.getData();

      new ImageProcessingAsyncTask(2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,selectedImage);
    } else if (requestCode == PICK_IMAGE_REQUEST3 && resultCode == MainActivity.RESULT_OK && null != data) {
      Uri selectedImage = data.getData();

      new ImageProcessingAsyncTask(3).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,selectedImage);
    } else if (requestCode == PICK_IMAGE_REQUEST4 && resultCode == MainActivity.RESULT_OK && null != data) {
      Uri selectedImage = data.getData();

      new ImageProcessingAsyncTask(4).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,selectedImage);
    }
  }

  private void selectImage4() {
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST4);
  }

  private void selectImage3() {
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST3);
  }

  private void selectImage2() {
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST2);
  }

  // brings up the photo gallery/other resources to choose a picture
  private void selectImage() {
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
  }

  private class ImageProcessingAsyncTask extends AsyncTask<Uri,Integer,Bitmap> {
    int code;

    public ImageProcessingAsyncTask(int i) {
      code = i;
    }


    protected Bitmap doInBackground(Uri... params) {
      try {
        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(params[0]));
        //return invertImageColors(bitmap);
        if (code == 2) {
          return makeGreyscale(bitmap);
//          makeFrame(bitmap);
        } else if (code ==1) {
          return invertImageColors(bitmap);
        } else if (code ==3) {
          return makePixelated(bitmap);
        } else if (code == 4) {
          return  bitmap;
        }
      } catch (FileNotFoundException e) {
        Log.d(TAG, "Image uri is not received or recognized");
      }
      return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
      super.onProgressUpdate(values);
      mProgressBar.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
      mImageView.setImageBitmap(bitmap);
      mProgressBar.setVisibility(View.INVISIBLE);

//      final int left = mImageView.getLeft();
//      final int top = mImageView.getTop();
//
//      final int[] location = new int[2];
//      mImageView.getLocationOnScreen(location);


//      Log.i("MATT_TEST", "left/top: ("+left+","+top +")");

      mImageView.setOnTouchListener(new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){

          Bitmap bitty = ((BitmapDrawable)mImageView.getDrawable()).getBitmap();
          Bitmap mutableBitty = bitty.copy(bitty.getConfig(),true);

          int x = (int)event.getX();
          int y = (int)event.getY();

          int px = mutableBitty.getPixel(x,y);
          int red = Color.red(px);
          int green = Color.green(px);
          int blue = Color.blue(px);
          int avg = (red+blue+green)/3;
          int rev = Color.argb(255,avg,avg,avg);

          for (int i = x; i < x+50 && i <mutableBitty.getWidth(); i++) {
            for(int j = y; j < y+50 && i <mutableBitty.getHeight(); j++){
              mutableBitty.setPixel(i, j, rev);
            }
          }

          mImageView.setImageBitmap(mutableBitty);

          Log.i("MATT-TEST","RGB: ("+red+","+green+","+blue+")");

          return true;
        }
      });

    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      mProgressBar.setVisibility(View.VISIBLE);
      mProgressBar.setProgress(0);
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
        int progressVal = Math.round((long) (100*(i/(1.0*mutableBitmap.getWidth()))));
        mProgressBar.setProgress(progressVal);
      }
      return mutableBitmap;
    }

    private Bitmap makeGreyscale(Bitmap bitmap){
      //You must use this mutable Bitmap in order to modify the pixels
      Bitmap mutableBitmap = bitmap.copy(bitmap.getConfig(),true);

      //Loop through each pixel, and invert the colors
      for (int i = 0; i < mutableBitmap.getWidth(); i++) {
        for(int j = 0; j < mutableBitmap.getHeight(); j++){
          int px = mutableBitmap.getPixel(i,j);
          int red = Color.red(px);
          int green = Color.green(px);
          int blue = Color.blue(px);
          int avg = (red+blue+green)/3;
          int rev = Color.argb(255,avg,avg,avg);
          mutableBitmap.setPixel(i,j,rev);
        }
        int progressVal = Math.round((long) (100*(i/(1.0*mutableBitmap.getWidth()))));
        mProgressBar.setProgress(progressVal);
      }
      return mutableBitmap;
    }

    private Bitmap makePixelated(Bitmap bitmap){
      //You must use this mutable Bitmap in order to modify the pixels
      Bitmap mutableBitmap = bitmap.copy(bitmap.getConfig(),true);

      //Loop through each pixel, and invert the colors
      for (int i = 0; i < mutableBitmap.getWidth(); i+=30) {
        for(int j = 0; j < mutableBitmap.getHeight(); j+=30){
          int px = mutableBitmap.getPixel(i,j);
          int red = Color.red(px);
          int green = Color.green(px);
          int blue = Color.blue(px);
          int rev = Color.argb(255,red,green,blue);

          for (int k = i; k < mutableBitmap.getWidth() && k<i+30; k++) {
            for (int l = j; l < mutableBitmap.getHeight() && l<j+30; l++) {
              mutableBitmap.setPixel(k,l,rev);
            }
          }
        }
        int progressVal = Math.round((long) (100*(i/(1.0*mutableBitmap.getWidth()))));
        mProgressBar.setProgress(progressVal);
      }
      return mutableBitmap;
    }

//    private Bitmap makeFrame(Bitmap bitmap){
//      //You must use this mutable Bitmap in order to modify the pixels
//      Bitmap mutableBitmap = bitmap.copy(bitmap.getConfig(),true);
//
//
//    }

  }
}

