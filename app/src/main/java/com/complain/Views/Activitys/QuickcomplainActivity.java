package com.complain.Views.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.complain.R;
import com.complain.Models.datamodel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QuickcomplainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView back;
    CardView btnsubmint;
    Spinner spinner_complaintype;
    String ctype,sname,snumber,saddress,sremark;
    EditText name,number,address,remark;
    FirebaseDatabase db;
    DatabaseReference reference;
    SharedPreferences userPreference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickcomplain);
        /*define id*/
        toolbar=findViewById(R.id.toolbar);
        back=findViewById(R.id.back);
        spinner_complaintype=findViewById(R.id.spinner_complaintype);

        name=findViewById(R.id.fullname);
        number=findViewById(R.id.mobileno);
        address=findViewById(R.id.address);
        remark=findViewById(R.id.remark);
        btnsubmint=findViewById(R.id.btnsunmit);

        /*activate toolbar*/
        setSupportActionBar(toolbar);

        //Local Database
        userPreference = getSharedPreferences("user",MODE_PRIVATE);

        btnsubmint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ctype=spinner_complaintype.getSelectedItem().toString();
                sname=name.getText().toString();
                snumber=number.getText().toString();
                saddress=address.getText().toString();
                sremark=remark.getText().toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy");

                if (ctype.isEmpty() || spinner_complaintype.getSelectedItemPosition()==0){
                    Toast.makeText(QuickcomplainActivity.this,"Please Select Complain",Toast.LENGTH_SHORT).show();
                } else if (sname.isEmpty()) {
                    Toast.makeText(QuickcomplainActivity.this,"Please Enter Your Name",Toast.LENGTH_SHORT).show();
                }else if (snumber.isEmpty()) {
                    Toast.makeText(QuickcomplainActivity.this,"Please Enter Your Phone Number",Toast.LENGTH_SHORT).show();
                }else if (saddress.isEmpty()) {
                    Toast.makeText(QuickcomplainActivity.this,"Please Enter Your Address",Toast.LENGTH_SHORT).show();
                }else {

                    datamodel datamodel = new datamodel(ctype,sname,snumber,saddress,sremark,dateFormat.format(new Date()),"Pending","1");

                    db=FirebaseDatabase.getInstance();
                    reference=db.getReference("Complains").child(userPreference.getString("phoneNumber",null)).child(String.valueOf(System.currentTimeMillis()));

                    reference.setValue(datamodel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            spinner_complaintype.setSelection(0);
                            name.setText("");
                            number.setText("");
                            address.setText("");
                            remark.setText("");
                            Toast.makeText(QuickcomplainActivity.this, "Your Complain Has been Submitted", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

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

        spinner_complainname.add("Select Complain Type");
        spinner_complainname.add("Shock On Pole");
        spinner_complainname.add("Cable Hanging Or Break");
        spinner_complainname.add("Cable Laying Open Or Open Joints");
        spinner_complainname.add("Feeder Panel OFF");
        spinner_complainname.add("Feeder Damaged");
        spinner_complainname.add("Shock On Feeder Panel");
        spinner_complainname.add("Other");

        ArrayAdapter<String> adapter=new ArrayAdapter<>(QuickcomplainActivity.this,R.layout.spinner_layout,spinner_complainname);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
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