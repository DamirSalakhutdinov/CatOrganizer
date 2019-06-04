package com.example.salah.catorganizer;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter  extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<CatInfo> objects;

    CustomAdapter(Context context, ArrayList<CatInfo> catInfos) {
        ctx = context;
        objects = catInfos;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        CatInfo p = getCatInfo(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка

//        ImageView imv = ((ImageView) view.findViewById(R.id.iImage));

        ((TextView) view.findViewById(R.id.iName)).setText(p.name);
        ((ImageView) view.findViewById(R.id.iImage)).setImageResource(R.drawable.def_cat);
        new DownloadImageTask(parent, position).execute(p.imageUri);

        return view;
    }

    // товар по позиции
    CatInfo getCatInfo(int position) {
        return ((CatInfo) getItem(position));
    }
}
