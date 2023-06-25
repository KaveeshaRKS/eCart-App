package com.example.ecart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

public class ExamineCart extends AppCompatActivity {

    GridView gridView;

    String userID;

    String[] values, values2, names, addresses, products, amounts, prices, dates;

    boolean flag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examine_cart);

        gridView=(GridView) findViewById(R.id.grid_view);

        ViewCompat.setNestedScrollingEnabled(gridView,true);

        SessionManagement sessionManagement=new SessionManagement(ExamineCart.this);
        userID=sessionManagement.getSession();

        String s=ShoppingCart.condition;
        Log.i("FFLAG", s);

            String operation="check_cart";

            EcartDB ecartDB= (EcartDB) new EcartDB(this, new EcartDB.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    if (output.equals("0 results")){
                        Toast.makeText(ExamineCart.this, output, Toast.LENGTH_SHORT).show();
                    }else {
                        values=output.split("   ");

                        names=new String[values.length];
                        addresses=new String[values.length];
                        products=new String[values.length];
                        amounts=new String[values.length];
                        prices=new String[values.length];
                        dates=new String[values.length];
                        values2=new String[6];

                        for (int i=0; i< values.length; i++){
                            values2=values[i].split("_");
                            names[i]=values2[0];
                            addresses[i]=values2[1];
                            products[i]=values2[2];
                            amounts[i]=values2[3];
                            prices[i]=values2[4];
                            dates[i]=values2[5];
                        }
                        MainAdapterForExamineCart adapter=new MainAdapterForExamineCart(ExamineCart.this, names, addresses, products, amounts, prices, dates);
                        gridView.setAdapter(adapter);
                    }
                }
            }).execute(operation, userID);
    }
    private void moveToMainActivity() {
        Intent i=new Intent(ExamineCart.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        moveToMainActivity();
    }
}
