package com.example.salah.catorganizer;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;

public class CustomAdapter  extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<CatInfo> objects;
    boolean[] loaded = new boolean[10];

    CustomAdapter(Context context, ArrayList<CatInfo> catInfos) {
        ctx = context;
        objects = catInfos;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < 10; i++) {
            loaded[i] = false;
        }
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        final CatInfo p = getCatInfo(position);

        ((TextView) view.findViewById(R.id.iName)).setText(p.name);
        ((ImageView) view.findViewById(R.id.iImage)).setImageResource(R.drawable.def_cat);

//        new DownloadImageTask(parent, position).execute(p.imageUri);
        if (!isCatLoaded(position)) {
            new DownloadImageTask(new DownloadImageTask.Listener() {
                @Override
                public void onImageDownloaded(Bitmap bitmap) {
                    ((ImageView) parent.getChildAt(position).findViewById(R.id.iImage))
                            .setImageBitmap(bitmap);
//                    Toast.makeText(ctx, "complete for " + p.name, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onImageDownloadError() {
//                    Toast.makeText(ctx, "failed for " + p.name, Toast.LENGTH_SHORT).show();
                }
            }).executeOnExecutor(THREAD_POOL_EXECUTOR, p.imageUri, String.valueOf(position));
//                    .execute(p.imageUri);
            setCatLoaded(position);
        }

        return view;
    }

    // товар по позиции
    CatInfo getCatInfo(int position) {
        return ((CatInfo) getItem(position));
    }

    boolean isCatLoaded(int position) {
        return loaded[position];
    }

    void setCatLoaded(int position) {
        loaded[position] = true;
    }
}
