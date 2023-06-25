package com.example.ecart;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class ShoppingCart extends AppCompatActivity {
    GridView gridView;

    String[] pics;
    String[] names;
    Bitmap[] images=new Bitmap[100];

    String[] array, array2;

    Intent intent;

    String category, userID=null;

    String s8, s1="";

    String s="";

    public static String condition="UNCLICKED";

    Button total, address_btn, pay, remove;
    TextView displayTotal;

    EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart);

        address=(EditText)findViewById(R.id.et_deliver_to);

        remove=(Button)findViewById(R.id.remove_from_cart);

//        remove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//                overridePendingTransition(0, 0);
//                startActivity(getIntent());
//                overridePendingTransition(0, 0);
//
//                displayTotal.setText("");
//            }
//        });

        gridView=(GridView) findViewById(R.id.grid_view3);

        ViewCompat.setNestedScrollingEnabled(gridView,true);

        SessionManagement sessionManagement=new SessionManagement(ShoppingCart.this);
        userID=sessionManagement.getSession();

        String operation= "search_shop_name2";

    EcartDB ecartDB = (EcartDB) new EcartDB(this, new EcartDB.AsyncResponse() {
        @Override
        public void processFinish(String output) {
            if (output.equals("0 results")) {
                Toast.makeText(ShoppingCart.this, output, Toast.LENGTH_LONG).show();
            } else {
                names = output.split("_");

                array2 = new String[names.length];

                for (int i = 0; i < names.length; i++) {
                    array = names[i].split(",");

                    for (int k = 0; k < array.length; k++) {
                        if (k == 2) {
                            continue;
                        }
                        if (k == 6) {
                             s1 += array[k] + ",";

                            Log.i("LPIC", s1);
                            continue;
                        }
                        Log.i("SWAN", array[k]);
                        if (s==null){
                            continue;
                        }else {
                            s += array[k] + "\n";
                        }
                    }
                    array2[i] = s;
                    s = "";
                }
                pics=s1.split(",");
                    for(int i=0; i<pics.length; i++) {
                        byte[] image = Base64.decode(pics[i], Base64.DEFAULT);
                        images[i]= BitmapFactory.decodeByteArray(image, 0, image.length);
                        Log.i("TWOOO", String.valueOf(image));

                        Log.i("LENGTH", String.valueOf(pics.length));
                    }
                MainAdapterForCart adapter = new MainAdapterForCart(ShoppingCart.this, array2, images);
                gridView.setAdapter(adapter);
            }
        }
    }).execute(operation, userID);

        total=(Button) findViewById(R.id.payable);
        displayTotal=(TextView) findViewById(R.id.tv_payable);

        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String operation="display_total";

                EcartDB ecartDB= (EcartDB) new EcartDB(getBaseContext(), new EcartDB.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        if (output.equals("0 results")){
                            Toast.makeText(ShoppingCart.this, output, Toast.LENGTH_SHORT).show();
                        }else{
                            displayTotal.setText(output);
                        }
                    }
                }).execute(operation, userID);
            }
        });

        String operation2="display_address";

        EcartDB ecartDB1= (EcartDB) new EcartDB(getBaseContext(), new EcartDB.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                if (output.equals("0 results")){
                    Toast.makeText(ShoppingCart.this, output, Toast.LENGTH_SHORT).show();
                }else{
                    address.setText(output);
                }
            }
        }).execute(operation2, userID);

        address_btn=(Button) findViewById(R.id.edit_address_btn);
        address_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address.setFocusableInTouchMode(true);
            }
        });

        pay=(Button) findViewById(R.id.pay_btn);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String operation="paid";

                EcartDB ecartDB= (EcartDB) new EcartDB(getBaseContext(), new EcartDB.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        if (output.equals("0 results")){
                            Toast.makeText(ShoppingCart.this, output, Toast.LENGTH_SHORT).show();
                            Log.i("PAID", output);
                        }else{
                            Toast.makeText(ShoppingCart.this, output, Toast.LENGTH_SHORT).show();
                            Log.i("PAID1", output);
                        }
                    }
                }).execute(operation, userID);

                condition="CLICKED";
            }
        });
    }
    public void onRefreshClick(View v){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    private void moveToMainActivity() {
        Intent i=new Intent(ShoppingCart.this, MainActivity.class);
        startActivity(i);
    }
    @Override
    public void onBackPressed() {
        moveToMainActivity();
    }
}
