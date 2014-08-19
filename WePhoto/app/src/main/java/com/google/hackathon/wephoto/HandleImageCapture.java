package com.google.hackathon.wephoto;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.File;


public class HandleImageCapture extends Activity {

    private static final String TAG = "HandleImageCapture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_image_capture);
        updateView();
    }

    protected void updateView() {
        // Get the dimensions of the bitmap.
        BitmapFactory.Options options = new BitmapFactory.Options();
        // Scale factor will be rounded down to nearest power of two.
        options.inSampleSize = 2;
        options.inPurgeable = true;
        Bitmap bitmap = null;
        String snapPath = SelectMode.getTmpImageFile().getAbsolutePath();
        while (true) {
            try {
                Log.i(TAG, "Down-sampling image by " + options.inSampleSize + "x");
                bitmap = BitmapFactory.decodeFile(snapPath, options);
                break;
            } catch (OutOfMemoryError e) {
                options.inSampleSize *= 2;
            } catch (Exception e) {
                Log.i(TAG, "Loading image file error: " + e.getMessage());
            }
        }
        if (bitmap == null) {
            Log.w(TAG, "Failed to read image from storage");
            return;
        }
        Log.i(TAG, "Snapshot dimensions: " + bitmap.getWidth() + " x " + bitmap.getHeight());
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.handle_image_capture, menu);
        return true;
    }

    public void uploadPicture(View view) {
        Log.i(TAG, "uploadPicture");
        setResult(Activity.RESULT_OK);
        finish();
    }
}
