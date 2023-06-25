package com.example.ecart;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CustomerRegistration extends AppCompatActivity {
EditText nic, name, address, phone, email, password;
long recordValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_registration);

        nic=(EditText) findViewById(R.id.et_nic);
        name=(EditText) findViewById(R.id.etName);
        address=(EditText) findViewById(R.id.etAddress);
        phone=(EditText) findViewById(R.id.etPhone);
        email=(EditText) findViewById(R.id.etEmail);
        password=(EditText) findViewById(R.id.etPassword);
    }
    public void btnRegister1(View v)
    {
        String customerNic = nic.getText().toString();
        String customerName = name.getText().toString();
        String customerAddress = address.getText().toString();
        String customerPhone = phone.getText().toString();
        String customerEmail=email.getText().toString();
        String customerPassword=password.getText().toString();

        String operation="customer_registration";

        EcartDB ecartDB= (EcartDB) new EcartDB(this, new EcartDB.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Toast.makeText(CustomerRegistration.this, output, Toast.LENGTH_SHORT).show();
            }
        }).execute(operation, customerNic, customerName, customerAddress, customerPhone, customerEmail, customerPassword);

        clearFields();
    }
    public void clearFields(){
        nic.setText("");
        name.setText("");
        address.setText("");
        phone.setText("");
        email.setText("");
        password.setText("");
    }
    private void moveToLogin() {
        Intent i=new Intent(CustomerRegistration.this, Login.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveToLogin();
    }
}
