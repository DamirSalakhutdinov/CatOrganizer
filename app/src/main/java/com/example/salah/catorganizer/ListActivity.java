package com.example.salah.catorganizer;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

public class ListActivity extends AppCompatActivity {
    String LIST_INSTANCE_STATE = "list state";
    String[] names = {
        "Барсик",
        "Кэсис",
        "Ферруцио",
        "Клементе",
        "Грампи",
        "Юппи",
        "Шелли",
        "Кактус",
        "Леопардо",
        "Жуля"
    };

    String[] avaUris = {
        "http://pngimg.com/uploads/cat/cat_PNG50546.png",
        "http://pngimg.com/uploads/cat/cat_PNG50537.png",
        "http://pngimg.com/uploads/cat/cat_PNG50525.png",
        "http://pngimg.com/uploads/cat/cat_PNG50511.png",
        "http://pngimg.com/uploads/cat/cat_PNG50498.png",
        "http://pngimg.com/uploads/cat/cat_PNG50480.png",
        "http://pngimg.com/uploads/cat/cat_PNG50433.png",
        "http://pngimg.com/uploads/cat/cat_PNG50425.png",
        "http://pngimg.com/uploads/cat/cat_PNG120.png",
        "http://pngimg.com/uploads/cat/cat_PNG104.png"
    };

    ArrayList<CatInfo> catInfos = new ArrayList<CatInfo>();
    CustomAdapter customAdapter;
    ListView lvMain;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        fillData();
        customAdapter = new CustomAdapter(this, catInfos, getFilesDir().getAbsolutePath());
        // находим список
        lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(customAdapter);
        lvMain.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);

                intent.putExtra("link", avaUris[position]);
                intent.putExtra("name", names[position]);
                intent.putExtra("pos", String.valueOf(position));
                startActivity(intent);
            }
        });
    }

    void fillData() {
        for (int i = 0; i <= 9; i++) {
            catInfos.add(new CatInfo(names[i],
                    avaUris[i]));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LIST_INSTANCE_STATE, lvMain.onSaveInstanceState());
    }
}
