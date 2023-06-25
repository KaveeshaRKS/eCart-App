package com.example.ecart;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.StringTokenizer;

public class ProductCategory extends AppCompatActivity {
    GridView gridView;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    String[] pics;
    String[] values, values2, names;
    Bitmap[] images;

    ImageButton onSearched;

    String shop=null;

    public String who;

    Intent i;

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
        setContentView(R.layout.product_category);

        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);

        onSearched= (ImageButton) findViewById(R.id.search_button);

        SessionManagement sessionManagement=new SessionManagement(ProductCategory.this);
        String userID=sessionManagement.getSession();

        String operation2="check_user";

        EcartDB ecartDB2= (EcartDB) new EcartDB(this, new EcartDB.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                if (output.equals("A customer")){
                    method4("CUSTOMER");
                }else {
                    method4("SHOP");
                }
            }
        }).execute(operation2, userID);

        gridView=(GridView) findViewById(R.id.grid_view2);

        ViewCompat.setNestedScrollingEnabled(gridView,true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            shop = extras.getString("key");

            method2(shop);
        }
    }

    private void method2(String shop) {
        String operation3="search_shop_id";

        Log.i("SSH", shop);

//        Toast.makeText(this, shop, Toast.LENGTH_LONG).show();

        EcartDB ecartDB3= (EcartDB) new EcartDB(getBaseContext(), new EcartDB.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                    method1(output);

                    i=new Intent(getApplicationContext(), Products.class);
                    i.putExtra("SHOPID",output);

                    Log.i("SHOPID", output);

            }
        }).execute(operation3, shop);
    }

    private void method1(String shopID) {
        String operation1="search_category_name";

//        Toast.makeText(this, shopID, Toast.LENGTH_LONG).show();
        Log.i("IIDD", shopID);

        EcartDB ecartDB1= (EcartDB) new EcartDB(this, new EcartDB.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                if (output.equals("0 results")){
                    Toast.makeText(ProductCategory.this, output, Toast.LENGTH_LONG).show();
                }else{
                    values=output.split("   ");

                    values2=new String[2];
                    names=new String[values.length];
                    images=new Bitmap[values.length];
                    pics=new String[values.length];

                    for (int i=0; i< values.length; i++){
                        values2=values[i].split(",");

                        names[i]=values2[0];
                        pics[i]=values2[1];
                    }
                    for(int i=0; i<pics.length; i++) {
                        byte[] image = Base64.decode(pics[i], Base64.DEFAULT);
                        images[i]= BitmapFactory.decodeByteArray(image, 0, image.length);
                        Log.i("UUU", String.valueOf(image));
                    }

                    MainAdapter adapter=new MainAdapter(ProductCategory.this, names, images);
                    gridView.setAdapter(adapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String category=adapter.getItem(i);

                            method3(category);

                            Log.i("CATTE", category);
                        }
                    });
                }
            }
        }).execute(operation1, shopID);

        onSearched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProductCategory.this, "Hi!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ProductCategory.this, "You already logged in!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout:
                        SessionManagement sessionManagement=new SessionManagement(ProductCategory.this);
                        sessionManagement.removeSession();

                        moveToLogin();
                        break;
                    case R.id.products:
                        if (who.equals("CUSTOMER")){
                            Toast.makeText(ProductCategory.this, "You are not allowed to access!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent=new Intent(getBaseContext(), ManageProducts.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.income:
                        if (who.equals("CUSTOMER")){
                            Toast.makeText(ProductCategory.this, "You are not allowed to access!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent=new Intent(getBaseContext(), See_Income.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.cart:
                        if (who.equals("CUSTOMER")){
                            Toast.makeText(ProductCategory.this, "You are not allowed to access!", Toast.LENGTH_SHORT).show();
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
    private void method4(String customer) {
        who=customer;
    }

    private void method3(String category) {
//        Toast.makeText(this, category, Toast.LENGTH_LONG).show();

        i.putExtra("CATEGORY",category);
        startActivity(i);
    }

    private void moveToLogin() {
        Intent i=new Intent(ProductCategory.this, Login.class);
        startActivity(i);
    }
    private void moveToMainActivity() {
        Intent i=new Intent(ProductCategory.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
        {
            moveToMainActivity();
        }
    }
}
