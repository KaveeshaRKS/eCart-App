package com.example.ecart;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class Product extends AppCompatActivity {
    TextView desc, name_of_the_product, price_of_the_product, available_quantity, total_price, price_label;
    EditText required_quantity;

    Button price_check;

    Button cart;

    String shop, category, pic, image, price, p, pri;
    String amount="";
    String userID="";
    String cartID="";

    int product_price;

    double required_amount, total;

    public static int globalPrice=0;

    ImageSwitcher imageSwitcher;

    String[] result=new String[4];

    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product);

        desc = (TextView) findViewById(R.id.tv_description);
        desc.setMovementMethod(new ScrollingMovementMethod());

        name_of_the_product=(TextView)findViewById(R.id.tv_product_name);
        available_quantity=(TextView)findViewById(R.id.tv_avail_quantity1);

        price_of_the_product=(TextView)findViewById(R.id.tv_price);
        required_quantity=(EditText) findViewById(R.id.tv_required_quantity1);
        total_price=(TextView)findViewById(R.id.tv_total_price1);

        price_check=(Button)findViewById(R.id.see_price);

        price_label=(TextView)findViewById(R.id.tv_price_label2);

        cart=(Button)findViewById(R.id.add_to_cart);

        imageSwitcher = (ImageSwitcher) findViewById(R.id.image_switcher);

        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                myView.setScaleType(ImageView.ScaleType.FIT_XY);
                myView.setAdjustViewBounds(true);
                myView.setLayoutParams(new ImageSwitcher.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
                return myView;
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            shop = extras.getString("SHOPID");
            category = extras.getString("CAT");
            pic = extras.getString("PIC");

            Log.i("CATEEEE", category);
            Log.i("SHOPPPP", shop);
            Log.i("PICC", pic);

            SessionManagement sessionManagement=new SessionManagement(Product.this);
            userID=sessionManagement.getSession();
            cartID=userID;
        }
        String operation="search_single_product_image";

        EcartDB ecartDB2= (EcartDB) new EcartDB(this, new EcartDB.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                if (output.equals("0 results")){
                    Toast.makeText(Product.this, output, Toast.LENGTH_SHORT).show();
                }else{
                    image=output;

                    byte[] byte_image = Base64.decode(image, Base64.DEFAULT);
                    Bitmap image_bitmap= BitmapFactory.decodeByteArray(byte_image, 0, byte_image.length);

                    Log.i("UUU1", String.valueOf(image));

                    Drawable drawable =new BitmapDrawable(image_bitmap);
                    imageSwitcher.setImageDrawable(drawable);
                }}
        }).execute(operation, shop, category, pic);

        String operation1="load_product_details";

        EcartDB ecartDB= (EcartDB) new EcartDB(this, new EcartDB.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                if (output.equals("0 results")){

                }else{
                    result=output.split("   ");

                    globalPrice=see_price(result[1]);

                    name_of_the_product.setText(result[0]);
                    Log.i("PRODUCT", result[0]);
                    price_of_the_product.setText(result[1]);
                    Log.i("PRICE", result[1]);
                    desc.setText(result[2]);
                    Log.i("DESCC", result[2]);
                    available_quantity.setText(result[3]);
                    Log.i("QUANTITY", result[3]);
                    price_label.setText(result[4]);
                    Log.i("UNIT", result[4]);
                }
            }
        }).execute(operation1, shop, category, pic);

        price_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = required_quantity.getText().toString();
                required_amount = Double.parseDouble(amount);

                Log.i("REQUIRED", String.valueOf(required_amount));
                Log.i("REQ", amount);

                total=globalPrice * required_amount;
                Log.i("AMOUNT", String.valueOf(amount));

                p=Double.toString(total);

                Log.i("PRICEEE", String.valueOf(total));

                total_price.setText(p);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String operation2="add_to_cart";

                EcartDB ecartDB1=(EcartDB) new EcartDB(getBaseContext(), new EcartDB.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        Log.i("INCOME", output);
                        Toast.makeText(Product.this, output, Toast.LENGTH_SHORT).show();
                    }
                }).execute(operation2, cartID, pic, category, shop, amount, p);
            }
        });
    }

    public int see_price(String pri){
        try {
            price=pri;
            String s = price.replaceAll("\\s", "");

            Log.i("PRICE4", price);

            product_price = Integer.parseInt(s);

            Log.i("PRICE5", String.valueOf(product_price));

            return product_price;
        }catch (Exception e){
            Log.i("EXC", e.getMessage());
        }
        return 0;
    }
}
