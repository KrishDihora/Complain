package com.complain.Views.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.complain.R;

public class FaqActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        /*define id*/
        toolbar=findViewById(R.id.toolbar);
        back=findViewById(R.id.back);
        /*activate toolbar*/
        setSupportActionBar(toolbar);

        /*      set on click listener on back button*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent home=new Intent(FaqActivity.this,HomeFragment.class);
                //startActivity(home);
                finish();

            }
        });
    }

    /*backed pressed method*/
    @Override
    public void onBackPressed() {
       /* Intent home=new Intent(FaqActivity.this,HomeFragment.class);
        startActivity(home);*/
        finish();

    }
}