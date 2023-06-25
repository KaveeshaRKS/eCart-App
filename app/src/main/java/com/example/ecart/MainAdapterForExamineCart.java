package com.example.ecart;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainAdapterForExamineCart extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private String[] name;
    private String[] address;
    private String[] product;
    private String[] amount;
    private String[] price;
    private String[] date;

    public MainAdapterForExamineCart(Context context, String[] name, String[] address, String[] product, String[] amount, String[] price, String[] date) {
        this.context = context;
        this.name = name;
        this.address = address;
        this.product = product;
        this.amount = amount;
        this.price = price;
        this.date = date;
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
            view=inflater.inflate(R.layout.row_item_for_examine_cart, null);
        }
        TextView n=view.findViewById(R.id.tv_name);
        TextView addr=view.findViewById(R.id.tv_address);
        TextView p=view.findViewById(R.id.tv_product);
        TextView am=view.findViewById(R.id.tv_amount);
        TextView pri=view.findViewById(R.id.tv_price);
        TextView d=view.findViewById(R.id.tv_date);

        n.setText(name[i]);
        addr.setText(address[i]);
        p.setText(product[i]);
        am.setText(amount[i]);
        pri.setText(price[i]);
        d.setText(date[i]);

        return view;
    }
}
