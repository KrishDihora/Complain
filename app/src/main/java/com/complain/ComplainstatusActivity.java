package com.complain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class ComplainstatusActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView back;
    RecyclerView rv_status;

    ArrayList<StatusModel> arr_statusdetail=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complainstatus);

        /*define id*/
        toolbar=findViewById(R.id.toolbar);
        back=findViewById(R.id.back);
        rv_status=findViewById(R.id.rv_status);


        /*activate toolbar*/
        setSupportActionBar(toolbar);

        /*      set on click listener on back button*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent home=new Intent(ComplainstatusActivity.this,HomeFragment.class);
               // startActivity(home);
                finish();

            }
        });

        rv_status.setLayoutManager(new LinearLayoutManager(this));

        arr_statusdetail.add(new StatusModel("Streetlight Problem","27-4-2023","Pending"));
        arr_statusdetail.add(new StatusModel("Bulb Problem","27-4-2023","Under Process"));

        RecyclerStatusAdapter adapter=new RecyclerStatusAdapter(ComplainstatusActivity.this,arr_statusdetail);
        rv_status.setAdapter(adapter);




    }

    /*backed pressed method*/
    @Override
    public void onBackPressed() {
      /*  Intent home=new Intent(ComplainstatusActivity.this,HomeFragment.class);
        startActivity(home);*/
        finish();

    }
}