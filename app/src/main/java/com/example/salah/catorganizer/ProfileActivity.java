package com.example.salah.catorganizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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

        Name.setText(intent.getStringExtra("name"));
        new DownloadImageTask(Photo).execute(intent.getStringExtra("link"));
    }
}
