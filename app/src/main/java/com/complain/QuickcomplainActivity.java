package com.complain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;

public class QuickcomplainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView back;
    Spinner spinner_complaintype;
    CardView btnsunmit;
    String sctype,sname,snumber,saddress,sremark;

    EditText fullname,monumber,address,remark;
    FirebaseDatabase db;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickcomplain);
        /*define id*/
        toolbar=findViewById(R.id.toolbar);
        back=findViewById(R.id.back);
        spinner_complaintype=findViewById(R.id.spinner_complaintype);
        fullname=findViewById(R.id.fullname);
        monumber=findViewById(R.id.mobileno);
        address=findViewById(R.id.address);
        remark=findViewById(R.id.remark);
        btnsunmit=findViewById(R.id.btnsunmit);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner_complaintype);

        /*activate toolbar*/
        setSupportActionBar(toolbar);

        sctype= mySpinner.getSelectedItem().toString();
        sname=fullname.getText().toString();
        snumber=monumber.getText().toString();
        saddress=address.getText().toString();
        sremark=remark.getText().toString();


        /*      set on click listener on back button*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent home=new Intent(QuickcomplainActivity.this,HomeFragment.class);
               // startActivity(home);
                finish();

            }
        });


        /*drop down for complain type*/
        ArrayList<String> spinner_complainname=new ArrayList<>();

        spinner_complainname.add("select Complain Type");
        spinner_complainname.add("Shock ON Pole");
        spinner_complainname.add("Cable Hanging Or Break");
        spinner_complainname.add("Cable Laying Open Or Open Joints");
        spinner_complainname.add("Feeder Panel OFF");
        spinner_complainname.add("Feeder Damaged");
        spinner_complainname.add("Shack On Feeder Panel");
        spinner_complainname.add("Other");

        ArrayAdapter<String> adapter=new ArrayAdapter<>(QuickcomplainActivity.this,android.R.layout.simple_spinner_dropdown_item,spinner_complainname);
        spinner_complaintype.setAdapter(adapter);

        btnsunmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ()
            }
        });
    }

    /*backed pressed method*/
    @Override
    public void onBackPressed() {
      /*  Intent home=new Intent(QuickcomplainActivity.this,HomeFragment.class);
        startActivity(home);*/
        finish();

    }
}