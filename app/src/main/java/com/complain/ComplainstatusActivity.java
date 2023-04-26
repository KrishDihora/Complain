package com.complain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ComplainstatusActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complainstatus);

        /*define id*/
        toolbar=findViewById(R.id.toolbar);
        back=findViewById(R.id.back);
        /*activate toolbar*/
        setSupportActionBar(toolbar);

        /*      set on click listener on back button*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home=new Intent(ComplainstatusActivity.this,HomeFragment.class);
                startActivity(home);
                finish();

            }
        });
    }

    /*backed pressed method*/
    @Override
    public void onBackPressed() {
      /*  Intent home=new Intent(ComplainstatusActivity.this,HomeFragment.class);
        startActivity(home);*/
        finish();

    }
}