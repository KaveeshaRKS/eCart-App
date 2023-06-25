package com.example.ecart;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ManageProducts extends AppCompatActivity {
    ImageView cat_img, product_image;
    Button choose, choose1;
    Bitmap bitmap, bitmap1;
    EditText et_id, et_name, et_shop, et_pid, et_pname, et_examount, et_price, et_discount, et_shopid, description;
    Spinner category, unit;

    String userID="";
    String categories[];

    String[] kinds_of_units;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_products);

        cat_img=(ImageView) findViewById(R.id.cat_image);
        choose=(Button) findViewById(R.id.choose_btn);
        et_id=(EditText)findViewById(R.id.et_cat_id);
        et_name=(EditText)findViewById(R.id.et_cat_name);
        et_shop=(EditText)findViewById(R.id.et_shop_id);

        product_image=(ImageView)findViewById(R.id.product_image);
        et_pid=(EditText)findViewById(R.id.et_product_id);
        et_pname=(EditText)findViewById(R.id.et_product_name);
        category=(Spinner)findViewById(R.id.spinner_category);
        et_examount=(EditText)findViewById(R.id.et_amount);
        et_price=(EditText)findViewById(R.id.et_price);
        et_discount=(EditText)findViewById(R.id.et_discount);
        et_shopid=(EditText)findViewById(R.id.et_id_of_shop);
        choose1=(Button)findViewById(R.id.browse_btn);
        description=(EditText) findViewById(R.id.product_description);

        unit=(Spinner)findViewById(R.id.spinner_unit);

        kinds_of_units=getResources().getStringArray(R.array.units);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, kinds_of_units);

        unit.setAdapter(adapter);

        description.setMovementMethod(new ScrollingMovementMethod());

        ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode()== Activity.RESULT_OK){
                    Intent data=result.getData();
                    Uri uri=data.getData();
                    try {
                        bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        cat_img.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }
        });
        ActivityResultLauncher<Intent> activityResultLauncher1=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode()== Activity.RESULT_OK){
                    Intent data=result.getData();
                    Uri uri=data.getData();
                    try {
                        bitmap1= MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        product_image.setImageBitmap(bitmap1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        choose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher1.launch(intent);
            }
        });

        SessionManagement sessionManagement=new SessionManagement(ManageProducts.this);
        userID=sessionManagement.getSession();

        et_shop.setText(userID);
        et_shopid.setText(userID);

        String operation="load_categories";

        EcartDB ecartDB= (EcartDB) new EcartDB(this, new EcartDB.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                if (output.equals("0 results")){

                }else{
                    categories=output.split("   ");

                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, categories);

                    category.setAdapter(adapter);
                }
            }
        }).execute(operation, userID);
    }
    public void add_product_category(View view){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);

        String id=et_id.getText().toString();
        String name=(et_name.getText().toString()).trim();
        String shop=userID;

        ByteArrayOutputStream byteArrayOutputStream;
        byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytes=byteArrayOutputStream.toByteArray();
        String base64Image= Base64.encodeToString(bytes, Base64.DEFAULT);


        String operation="adding_category";

        EcartDB ecartDB= (EcartDB) new EcartDB(this, new EcartDB.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Toast.makeText(ManageProducts.this, output, Toast.LENGTH_SHORT).show();

                Log.i("VAR", output);
            }
        }).execute(operation, id, name, shop, base64Image);

        clearFields();
    }
    public void add_product(View view){
        String id=et_pid.getText().toString();
        String name=et_pname.getText().toString();
        String cat=(category.getSelectedItem().toString()).trim();
        String amt= et_examount.getText().toString();
        String price= et_price.getText().toString();
        String disc= et_discount.getText().toString();
        String IDOfShop=userID;
        String p_description=description.getText().toString();
        String u=unit.getSelectedItem().toString();

        ByteArrayOutputStream byteArrayOutputStream;
        byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytes=byteArrayOutputStream.toByteArray();
        String base64Image= Base64.encodeToString(bytes, Base64.DEFAULT);


        String operation="adding_product";

        EcartDB ecartDB= (EcartDB) new EcartDB(this, new EcartDB.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Toast.makeText(ManageProducts.this, output, Toast.LENGTH_SHORT).show();

                Log.i("VAR", output);
            }
        }).execute(operation, id, name, cat, amt, price, disc, IDOfShop, base64Image, p_description, u);

        clearFields();
    }
    public void update_product_category(View view){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);

        String id=et_id.getText().toString();
        String name=et_name.getText().toString();

        ByteArrayOutputStream byteArrayOutputStream;
        byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytes=byteArrayOutputStream.toByteArray();
        String base64Image= Base64.encodeToString(bytes, Base64.DEFAULT);


        String operation="updating_category";

        EcartDB ecartDB= (EcartDB) new EcartDB(this, new EcartDB.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Toast.makeText(ManageProducts.this, output, Toast.LENGTH_SHORT).show();
            }
        }).execute(operation, id, name, base64Image, userID);

        clearFields();
    }
    public void update_product(View view){
        String id=et_pid.getText().toString();
        String name=et_pname.getText().toString();
        String cat=category.getSelectedItem().toString();
        String existing_amount=et_examount.getText().toString();
        String price=et_price.getText().toString();
        String discount=et_discount.getText().toString();
        String p_description=description.getText().toString();
        String u=unit.getSelectedItem().toString();

        ByteArrayOutputStream byteArrayOutputStream;
        byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytes=byteArrayOutputStream.toByteArray();
        String base64Image= Base64.encodeToString(bytes, Base64.DEFAULT);


        String operation="updating_product";

        EcartDB ecartDB= (EcartDB) new EcartDB(this, new EcartDB.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Toast.makeText(ManageProducts.this, output, Toast.LENGTH_SHORT).show();

                Log.i("UPDATE1", output);
            }
        }).execute(operation, id, name, cat, existing_amount, price, discount, base64Image, p_description, u, userID);

        clearFields();
    }

    public void delete_product_category(View view){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);

        String id=et_id.getText().toString();
        String shop=et_shop.getText().toString();

        String operation="deleting_category";

        EcartDB ecartDB= (EcartDB) new EcartDB(this, new EcartDB.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Toast.makeText(ManageProducts.this, output, Toast.LENGTH_SHORT).show();

                Log.i("DELETE_CAT", output);
            }
        }).execute(operation, id, shop);

        clearFields();
    }
    public void delete_product(View view){
        String id=et_pid.getText().toString();
        String shop=et_shopid.getText().toString();
        String cat=category.getSelectedItem().toString();

        String operation="deleting_product";

        EcartDB ecartDB= (EcartDB) new EcartDB(this, new EcartDB.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Toast.makeText(ManageProducts.this, output, Toast.LENGTH_SHORT).show();
            }
        }).execute(operation, id, shop, cat);

        clearFields();
    }

    private void clearFields() {
        et_id.setText("");
        et_name.setText("");
        cat_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_image_24));

        et_pid.setText("");
        et_pname.setText("");
        et_examount.setText("");
        et_price.setText("");
        et_discount.setText("");
        product_image.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_image_24));
        description.setText("No description!");
    }
    private void moveToMainActivity() {
        Intent i=new Intent(ManageProducts.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        moveToMainActivity();
    }
}
