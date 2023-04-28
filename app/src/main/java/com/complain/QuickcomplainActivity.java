package com.complain;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class QuickcomplainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView back;
    CardView btnsubmint;
    Spinner spinner_complaintype;
    String ctype,sname,snumber,saddress,sremark;

    EditText name,number,address,remark;

    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickcomplain);
        /*define id*/
        toolbar=findViewById(R.id.toolbar);
        back=findViewById(R.id.back);
        spinner_complaintype=findViewById(R.id.spinner_complaintype1);

        name=findViewById(R.id.fullname);
        number=findViewById(R.id.mobileno);
        address=findViewById(R.id.address);
        remark=findViewById(R.id.remark);
        btnsubmint=findViewById(R.id.btnsunmit);
        db=FirebaseDatabase.getInstance("https://smart-bhavnagar-default-rtdb.asia-southeast1.firebasedatabase.app");

       // ctype=spinner_complaintype.getSelectedItem().toString();



        /*activate toolbar*/
        setSupportActionBar(toolbar);

        btnsubmint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sname=name.getText().toString();
                snumber=number.getText().toString();
                saddress=address.getText().toString();
                sremark=remark.getText().toString();

                datamodel datamodel = new datamodel(sname,snumber,saddress,sremark);

                reference=db.getReference("Complains");
                reference.child(snumber).setValue(datamodel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        name.setText("");
                        number.setText("");
                        address.setText("");
                        remark.setText("");
                        Toast.makeText(QuickcomplainActivity.this, "Your Complain Has bin Submitted", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

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
        spinner_complainname.add("Shock On Feeder Panel");
        spinner_complainname.add("Other");

        ArrayAdapter<String> adapter=new ArrayAdapter<>(QuickcomplainActivity.this,android.R.layout.simple_spinner_dropdown_item,spinner_complainname);
        spinner_complaintype.setAdapter(adapter);
    }

    /*backed pressed method*/
    @Override
    public void onBackPressed() {
      /*  Intent home=new Intent(QuickcomplainActivity.this,HomeFragment.class);
        startActivity(home);*/
        finish();

    }
}