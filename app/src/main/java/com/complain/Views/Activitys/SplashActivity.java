package com.complain.Views.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.complain.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences userPreference;
    SharedPreferences.Editor userEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Local Database
        userPreference = getSharedPreferences("user",MODE_PRIVATE);
        userEditor = userPreference.edit();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                /*if (userPreference.getAll().size()>0){
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashActivity.this,SignInActivity.class));
                    finish();
                }*/

                FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

                if (firebaseUser!=null)
                {
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashActivity.this,SignInActivity.class));
                    finish();
                }

            }
        },2000);



    }
}