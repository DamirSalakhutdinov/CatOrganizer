package com.example.salah.catorganizer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//    ViewGroup parent = null;
//    int position;
//    ImageView imageView;
    Listener listener;
    public DownloadImageTask(final Listener listener) {
//        this.parent = parent;
//        this.position = position;
        this.listener = listener;
    }

//    public DownloadImageTask(ImageView imageView) {
//        this.imageView = imageView;
//    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap bmp = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();

//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = 32;
////
//            bmp = BitmapFactory.decodeStream(in, null, options);
//            bmp = decodeSampledBitmapFromResource(in , 50, 50);
//            bmp = BitmapFactory.decodeStream(in);
            bmp = decodeSampledBitmapFromResource(in, 50, 50, urldisplay);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bmp;
    }
    protected void onPostExecute(Bitmap downloadedBitmap) {
        if (null != downloadedBitmap) {
            listener.onImageDownloaded(downloadedBitmap);
        } else {
            listener.onImageDownloadError();
        }
    }

    public Bitmap decodeSampledBitmapFromResource(InputStream in,
                                                         int reqWidth, int reqHeight, String urldisplay) throws MalformedURLException {

        // Читаем с inJustDecodeBounds=true для определения размеров
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        InputStream inReset = null;
        try {
            inReset = new java.net.URL(urldisplay).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Вычисляем inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Читаем с использованием inSampleSize коэффициента
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(inReset, null, options);
    }

    public int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Реальные размеры изображения
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Вычисляем наибольший inSampleSize, который будет кратным двум
            // и оставит полученные размеры больше, чем требуемые
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static interface Listener {
        void onImageDownloaded(final Bitmap bitmap);
        void onImageDownloadError();
    }

}
