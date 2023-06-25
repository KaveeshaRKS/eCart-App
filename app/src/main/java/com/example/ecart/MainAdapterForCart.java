package com.example.ecart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainAdapterForCart extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private String[] name;
    private Bitmap[] image;
    String[] array;

    public MainAdapterForCart(Context context, String[] name, Bitmap[] image) {
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
            view=inflater.inflate(R.layout.row_item_for_cart, null);
        }
        ImageView imageView=view.findViewById(R.id.image_view);
        TextView textView=view.findViewById(R.id.text_view);

        Button remove=view.findViewById(R.id.remove_from_cart);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=getItem(i);

                Log.i("POP", s);

                SessionManagement sessionManagement=new SessionManagement(context);
                String userID=sessionManagement.getSession();

                Log.i("USSER", userID);

                array=s.split("\n");

                Log.i("WARR", array[0]);
                Log.i("WARR1", array[1]);
                Log.i("WARR2", array[2]);

                String operation="remove_from_cart";

                EcartDB ecartDB= (EcartDB) new EcartDB(context, new EcartDB.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        if (output.equals("0 results")){
                            Toast.makeText(context, output, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, output, Toast.LENGTH_SHORT).show();
                        }
                    }
                }).execute(operation, userID, array[0], array[1], array[2]);
            }
        });

        imageView.setImageBitmap(image[i]);
        textView.setText(name[i]);

        Log.i("BITMAP1", String.valueOf(image[i]));
        Log.i("BITMAP2", name[i]);

        return view;
    }
}
