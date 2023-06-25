package com.example.ecart;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    GridView gridView;

    String[] values;
    String[] images;
    String[] b_data, b_data1, b_data2;
    Bitmap[] shops=new Bitmap[100];

    String[] shArr=new String[2];

    String s;

    int i=0;
    String sh=null;

    ImageButton onSearched;
    MenuItem manage_products, income;

    public String who;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {
            Intent intent=new Intent(getBaseContext(), ShoppingCart.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);

        onSearched= (ImageButton) findViewById(R.id.search_button);

        gridView=(GridView) findViewById(R.id.grid_view);

        ViewCompat.setNestedScrollingEnabled(gridView,true);

        SessionManagement sessionManagement=new SessionManagement(MainActivity.this);
        String userID=sessionManagement.getSession();

        manage_products=(MenuItem)findViewById(R.id.products);
        income=(MenuItem)findViewById(R.id.income);

        String operation2="check_user";

        EcartDB ecartDB2= (EcartDB) new EcartDB(this, new EcartDB.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                if (output.equals("A customer")){
                    method2("CUSTOMER");
                }else {
                    method2("SHOP");
                }
            }
        }).execute(operation2, userID);

        String operation="search_shop_name";

        EcartDB ecartDB= (EcartDB) new EcartDB(this, new EcartDB.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                if (output.equals("0 results")){
                    Toast.makeText(MainActivity.this, output, Toast.LENGTH_SHORT).show();
                }else{
                    values=output.split("   ");

                    b_data1=new String[values.length];
                    images=new String[values.length];

                    for (int i=0; i< values.length; i++){
                        Log.i("VALLLL", values[i]);

                        b_data=values[i].split("_");

                        b_data2=b_data[0].split(",");

                        Log.i("LEN", String.valueOf(b_data2.length));

                        for (int k=0; k<b_data2.length; k++){
                            if (s==null){
                                s= b_data2[0] + "\n";
                            }else {
                                s += b_data2[k] + "\n";
                            }
                        }

                        b_data1[i]=s;
                        s="";
                        images[i]=b_data[1];
                    }

                    for(int i=0; i<images.length; i++) {
                        byte[] image = Base64.decode(images[i], Base64.DEFAULT);
                        shops[i]=BitmapFactory.decodeByteArray(image, 0, image.length);
                        Log.i("UUU", String.valueOf(image));
                    }

                    MainAdapter adapter=new MainAdapter(MainActivity.this, b_data1, shops);
                    gridView.setAdapter(adapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            sh=adapter.getItem(i);
                            shArr=sh.split("\n");

                            Log.i("PRINTEDSH", shArr[0]);

                            method1(shArr[0]);
                        }
                    });
                }
            }
        }).execute(operation);

        onSearched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Hi!", Toast.LENGTH_SHORT).show();
            }
        });

        drawerToggle=new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.login:
                        Toast.makeText(MainActivity.this, "You already logged in!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout:
                        SessionManagement sessionManagement=new SessionManagement(MainActivity.this);
                        sessionManagement.removeSession();

                        moveToLogin();
                        break;
                    case R.id.products:
                        if (who.equals("CUSTOMER")){
                            Toast.makeText(MainActivity.this, "You are not allowed to access!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent=new Intent(getBaseContext(), ManageProducts.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.income:
                        if (who.equals("CUSTOMER")){
                            Toast.makeText(MainActivity.this, "You are not allowed to access!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent=new Intent(getBaseContext(), See_Income.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.cart:
                        if (who.equals("CUSTOMER")){
                            Toast.makeText(MainActivity.this, "You are not allowed to access!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent=new Intent(getBaseContext(), ExamineCart.class);
                            startActivity(intent);
                        }
                        break;
                }

                return false;
            }
        });

    }

    private void method2(String customer) {
        who=customer;
    }

    private void method1(String sh) {
//        Toast.makeText(this, sh, Toast.LENGTH_LONG).show();

        Intent i=new Intent(getApplicationContext(), ProductCategory.class);
        i.putExtra("key",sh);
        startActivity(i);
    }

    private void moveToLogin() {
        Intent i=new Intent(MainActivity.this, Login.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
        {
            finishAffinity();
            finish();
        }
    }
}