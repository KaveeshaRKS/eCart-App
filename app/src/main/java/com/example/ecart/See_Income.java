package com.example.ecart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class See_Income extends AppCompatActivity {

    Spinner months;

    EditText year;

    Button income;

    String y,m, userID;

    TextView display_income;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_income);

        display_income=(TextView)findViewById(R.id.tv_see_income);

        months=(Spinner) findViewById(R.id.s_month);

        String[] monthsInNumbers={"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, monthsInNumbers);

        months.setAdapter(adapter);

        year=(EditText) findViewById(R.id.et_year);

        SessionManagement sessionManagement=new SessionManagement(See_Income.this);
        userID=sessionManagement.getSession();

        income=(Button) findViewById(R.id.btn_see_income);

        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                y=year.getText().toString();
                m=months.getSelectedItem().toString();

                String operation="see_income";

                EcartDB ecartDB=(EcartDB) new EcartDB(getBaseContext(), new EcartDB.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        display_income.setText(output);
                        Log.i("IINCOME", output);
                    }
                }).execute(operation, y, m, userID);
            }
        });
    }
    private void moveToMainActivity() {
        Intent i=new Intent(See_Income.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveToMainActivity();
    }
}
