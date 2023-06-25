package com.example.ecart;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private String[] name;
    private Bitmap[] image;

    public MainAdapter(Context context, String[] name, Bitmap[] image) {
        this.context = context;
        this.name = name;
        this.image = image;
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public String getItem(int i) {
        return name[i];
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater==null){
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view==null){
            view=inflater.inflate(R.layout.row_item, null);
        }
        ImageView imageView=view.findViewById(R.id.image_view);
        TextView textView=view.findViewById(R.id.text_view);

        imageView.setImageBitmap(image[i]);
        textView.setText(name[i]);

        return view;
    }
}
