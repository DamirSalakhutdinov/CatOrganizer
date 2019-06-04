package com.example.salah.catorganizer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.ArrayList;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ViewGroup parent = null;
    int position;
    ImageView imageView;

    public DownloadImageTask(ViewGroup parent, int position) {
        this.parent = parent;
        this.position = position;
    }

    public DownloadImageTask(ImageView imageView) {
        this.imageView = imageView;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap bmp = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();

//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = 32;
//
//            bmp = BitmapFactory.decodeStream(in, null, options);
            bmp = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bmp;
    }
    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            if (parent != null) {
                View ItemView = parent.getChildAt(position);
                ((ImageView) ItemView.findViewById(R.id.iImage)).setImageBitmap(result);
            } else {
                imageView.setImageBitmap(result);
            }
        }
    }
}
