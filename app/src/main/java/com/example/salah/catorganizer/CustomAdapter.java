package com.example.salah.catorganizer;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;

public class CustomAdapter  extends BaseAdapter {
    private Context ctx;
    private LayoutInflater lInflater;
    private ArrayList<CatInfo> objects;
    private boolean[] loaded = new boolean[10];
    private String path;

    CustomAdapter(Context context, ArrayList<CatInfo> catInfos, String path) {
        ctx = context;
        objects = catInfos;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < 10; i++) {
            loaded[i] = false;
        }
        this.path = path;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        final CatInfo p = getCatInfo(position);

        ((TextView) view.findViewById(R.id.iName)).setText(p.name);
        ((ImageView) view.findViewById(R.id.iImage)).setImageResource(R.drawable.def_cat);

        if (!isCatLoaded(position)) {
            new DownloadImageTask(new DownloadImageTask.Listener() {
                @Override
                public void onImageDownloaded(Bitmap bitmap) {
                    if (parent != null) {
                        ((ImageView) parent.getChildAt(position).findViewById(R.id.iImage))
                                .setImageBitmap(bitmap);
                    }
                }

                @Override
                public void onImageDownloadError() {
                }
            }).executeOnExecutor(THREAD_POOL_EXECUTOR, p.imageUri, String.valueOf(position), path);
            setCatLoaded(position);
        }

        return view;
    }

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
