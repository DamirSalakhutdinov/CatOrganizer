package com.example.salah.catorganizer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    Listener listener;
    public DownloadImageTask(final Listener listener) {
        this.listener = listener;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];

//        String dir_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/kitties";
        String dir_path = urls[2];
        String imName = "kitty" + urls[1] + ".png";
        Bitmap bmp = null;


        Log.d("My", dir_path);

        File dir = new File(dir_path + "/" + imName);
        if (dir.exists()) {
            bmp = BitmapFactory.decodeFile(dir_path + "/" + imName);
        }
        if (bmp == null) {
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = decodeSampledBitmapFromResource(in, 50, 50, urldisplay);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            dir = new File(dir_path);
            if (!dir.exists())
                if (!dir.mkdirs()) {
                    Log.d("My", "Problem creating Image folder");
                }
            File file = new File(dir, "kitty" + urls[1] + ".png");
            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                Log.d("My", "все плохо");
                e.printStackTrace();

            }
            if (fOut == null) {
                Log.d("My", "пиздец");
            }

            if (bmp != null) {

                bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                try {
                    fOut.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
