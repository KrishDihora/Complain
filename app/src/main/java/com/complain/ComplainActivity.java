package com.complain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.net.SocketImpl;
import java.util.ArrayList;

public class ComplainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView back;
    Spinner spinner_complaintype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);

        /*define id*/
        toolbar=findViewById(R.id.toolbar);
        back=findViewById(R.id.back);
        spinner_complaintype=findViewById(R.id.spinner_complaintype);

        /*activate toolbar*/
        setSupportActionBar(toolbar);

        /*      set on click listener on back button*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent home=new Intent(ComplainActivity.this,HomeFragment.class);
               // startActivity(home);
                finish();

            }
        });


        /*drop down for complain type*/
        ArrayList<String> spinner_complainname=new ArrayList<>();

        spinner_complainname.add("select Complain Type");
        spinner_complainname.add("Light Blinking");
        spinner_complainname.add("LED OFF");
        spinner_complainname.add("Pole Damage");
        spinner_complainname.add("Bracket Damage");
        spinner_complainname.add("Junction Box Damage");
        spinner_complainname.add("New LED Light Installation");
        spinner_complainname.add("New Pole Installation");
        spinner_complainname.add("Pole Shifting Required");
        spinner_complainname.add("Other");

        ArrayAdapter<String> adapter=new ArrayAdapter<>(ComplainActivity.this,android.R.layout.simple_spinner_dropdown_item,spinner_complainname);
        spinner_complaintype.setAdapter(adapter);
    }

    /*backed pressed method*/
    @Override
    public void onBackPressed() {
        /*Intent home=new Intent(ComplainActivity.this,HomeFragment.class);
        startActivity(home);*/
        finish();

    }
}