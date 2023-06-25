package com.example.ecart;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BusinessmanRegistration extends AppCompatActivity {
    EditText id, shopName, shopAddress, shopDistrict, shopPhone, shopPassword;
    ImageView shop;
    Bitmap bitmap;
    Button browse;

    private static final int IMAGE_PICK_CODE=1000;
    private static final int PERMISSION_CODE=1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.businessman_registration);

        id = (EditText) findViewById(R.id.et_id);
        shopName = (EditText) findViewById(R.id.etBusinessName);
        shopAddress = (EditText) findViewById(R.id.etBusinessAddress);
        shopDistrict = (EditText) findViewById(R.id.etBusinessDistrict);
        shopPhone = (EditText) findViewById(R.id.etBusinessPhone);
        shopPassword = (EditText) findViewById(R.id.etBusinessPassword);
        shop = (ImageView) findViewById(R.id.cat_image);
        browse=(Button)findViewById(R.id.browse_btn);

        ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode()== Activity.RESULT_OK){
                    Intent data=result.getData();
                    Uri uri=data.getData();
                    try {
                        bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        shop.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }
        });
    }
    public void btnRegister2(View v){
        String username=id.getText().toString();
        String business=shopName.getText().toString();
        String addr=shopAddress.getText().toString();
        String dist=shopDistrict.getText().toString();
        String phone=shopPhone.getText().toString();
        String pw=shopPassword.getText().toString();

        ByteArrayOutputStream byteArrayOutputStream;
        byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytes=byteArrayOutputStream.toByteArray();
        String base64Image=Base64.encodeToString(bytes, Base64.DEFAULT);


        String operation="business_registration";

        EcartDB ecartDB= (EcartDB) new EcartDB(this, new EcartDB.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Toast.makeText(BusinessmanRegistration.this, output, Toast.LENGTH_SHORT).show();

                Log.i("ERRORR", output);
            }
        }).execute(operation, username, business, addr, dist, phone, pw, base64Image);

        clearFields();
    }

    private void clearFields() {
        id.setText("");
        shopName.setText("");
        shopAddress.setText("");
        shopDistrict.setText("");
        shopPhone.setText("");
        shopPassword.setText("");
        shop.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_storefront_24));
    }
    private void moveToLogin() {
        Intent i=new Intent(BusinessmanRegistration.this, Login.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveToLogin();
    }
}
