package com.example.ecart;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Products extends AppCompatActivity {
    GridView gridView;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    String[] pics;
    String[] names, values, values2;
    Bitmap[] images=new Bitmap[100];

    ImageButton onSearched;

    Intent intent;

    String shop, category=null;

    public String who;

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products);

        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);

        onSearched= (ImageButton) findViewById(R.id.search_button);

        gridView=(GridView) findViewById(R.id.grid_view3);

        SessionManagement sessionManagement=new SessionManagement(Products.this);
        String userID=sessionManagement.getSession();

        String operation2="check_user";

        EcartDB ecartDB2= (EcartDB) new EcartDB(this, new EcartDB.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                if (output.equals("A customer")){
                    method3("CUSTOMER");
                }else {
                    method3("SHOP");
                }
            }
        }).execute(operation2, userID);

        ViewCompat.setNestedScrollingEnabled(gridView,true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            shop = extras.getString("SHOPID");
            category=extras.getString("CATEGORY");

            Log.i("CATE", category);
            Log.i("SHOP", shop);

            method1(shop, category);
        }
        intent=new Intent(getApplicationContext(), Product.class);
        intent.putExtra("SHOPID",shop);
        intent.putExtra("CAT",category);
    }

    private void method1(String shopID, String cat) {
        String operation="search_product_name";

        String shopID2=shopID.trim();

//        Toast.makeText(this, shopID, Toast.LENGTH_LONG).show();
        Log.i("IID", shopID);
        Log.i("CAT", cat);
        Log.i("SHOPSID1", shopID2);
        Log.i("SHOPSID2", shopID);

        EcartDB ecartDB= (EcartDB) new EcartDB(this, new EcartDB.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                if (output.equals("0 results")){
                    Toast.makeText(Products.this, output, Toast.LENGTH_LONG).show();
                }else{
                    values=output.split("   ");

                    values2=new String[2];
                    names=new String[values.length];
                    pics=new String[values.length];

                    for (int i=0; i< values.length; i++){
                        values2=values[i].split(",");

                        names[i]=values2[0];
                        Log.i("PRODUCTS", names[i]);

                        pics[i]=values2[1];
                        Log.i("PIMAGES", pics[i]);
                    }
                    for(int i=0; i<pics.length; i++) {
                        byte[] image = Base64.decode(pics[i], Base64.DEFAULT);
                        images[i]= BitmapFactory.decodeByteArray(image, 0, image.length);
                        Log.i("UUU", String.valueOf(image));
                    }

                    MainAdapter adapter=new MainAdapter(Products.this, names, images);
                    gridView.setAdapter(adapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String pic=adapter.getItem(i);

                            method2(pic);
                        }
                    });
                }
            }
        }).execute(operation, shopID, cat);

        onSearched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Products.this, "Hi!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Products.this, "You already logged in!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout:
                        SessionManagement sessionManagement=new SessionManagement(Products.this);
                        sessionManagement.removeSession();

                        moveToLogin();
                        break;
                    case R.id.products:
                        if (who.equals("CUSTOMER")){
                            Toast.makeText(Products.this, "You are not allowed to access!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent=new Intent(getBaseContext(), ManageProducts.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.income:
                        if (who.equals("CUSTOMER")){
                            Toast.makeText(Products.this, "You are not allowed to access!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent=new Intent(getBaseContext(), See_Income.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.cart:
                        if (who.equals("CUSTOMER")){
                            Toast.makeText(Products.this, "You are not allowed to access!", Toast.LENGTH_SHORT).show();
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
    private void method3(String customer) {
        who=customer;
    }

    private void method2(String pic) {
        intent.putExtra("PIC", pic);
        startActivity(intent);
    }

    private void moveToLogin() {
        Intent i=new Intent(Products.this, Login.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
        {
            super.onBackPressed();
        }
    }
}
