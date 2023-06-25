package com.example.ecart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity{
    TextView tvRegister;
    Button login;
    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        tvRegister=(TextView) findViewById(R.id.register_link);

        login=(Button)findViewById(R.id.login_btn);
        username=(EditText)findViewById(R.id.etUsername);
        password=(EditText)findViewById(R.id.etPW);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=username.getText().toString();
                String pw=password.getText().toString();

                String operation="login";

                EcartDB ecartDB= (EcartDB) new EcartDB(getBaseContext(), new EcartDB.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        Toast.makeText(Login.this, output, Toast.LENGTH_SHORT).show();

                            if (output.equals("SUCCESS")){
                                User user=new User(id, pw);

                                SessionManagement sessionManagement=new SessionManagement(Login.this);
                                sessionManagement.saveSession(user);

                                moveToMainActivity();
                            }
                    }
                }).execute(operation, id, pw);

                clearFields();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WhoYouAreDialogBox dialogBox=WhoYouAreDialogBox.newInstace("You are a, ");
                dialogBox.show(getSupportFragmentManager(), "dialog");
            }
        });
    }

    private void clearFields() {
        username.setText("");
        password.setText("");
    }

    @Override
    protected void onStart() {
        super.onStart();

        SessionManagement sessionManagement=new SessionManagement(Login.this);
        String userID=sessionManagement.getSession();

        if (userID!=null){
            moveToMainActivity();
        }
    }

    private void moveToMainActivity() {
        Intent i=new Intent(Login.this, MainActivity.class);
        startActivity(i);
    }

    public void clickedOnCustomer(){
        Intent i=new Intent(this, CustomerRegistration.class);
        startActivity(i);
    }
    public void clickedOnBusinessman(){
        Intent i=new Intent(this, BusinessmanRegistration.class);
        startActivity(i);
    }
    public void onBackPressed() {
        finish();
    }
}
