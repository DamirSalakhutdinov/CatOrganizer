package com.example.salah.catorganizer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;

public class ProfileActivity extends AppCompatActivity {
    ImageView Photo;
    TextView Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Photo = (ImageView) findViewById(R.id.pImage);
        Name = (TextView) findViewById(R.id.pName);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String link = intent.getStringExtra("link");
        Name.setText(intent.getStringExtra("name"));
//        new DownloadImageTask(Photo).execute(intent.getStringExtra("link"));
        new DownloadImageTask(new DownloadImageTask.Listener() {
            @Override
            public void onImageDownloaded(Bitmap bitmap) {
                ((ImageView) Photo.findViewById(R.id.pImage))
                        .setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(), "complete for" + name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onImageDownloadError() {
                Toast.makeText(getApplicationContext(), "failed for" + name, Toast.LENGTH_SHORT).show();
            }
        }).executeOnExecutor(THREAD_POOL_EXECUTOR, link);
    }
}
