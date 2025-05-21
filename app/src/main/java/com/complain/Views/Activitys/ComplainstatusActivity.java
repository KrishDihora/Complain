package com.complain.Views.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.complain.R;
import com.complain.Adapters.RecyclerStatusAdapter;
import com.complain.Models.StatusModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ComplainstatusActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView back;
    RecyclerView rv_status;
    ArrayList<StatusModel> arr_statusdetail=new ArrayList<>();
    FirebaseDatabase db;
    DatabaseReference reference;
    SharedPreferences userPreference;

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

        //Local Database
        userPreference = getSharedPreferences("user",MODE_PRIVATE);

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

        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Complains").child(userPreference.getString("phoneNumber",null));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot != null) {

                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        String cname = childSnapshot.child("ctype").getValue(String.class);
                        String date = childSnapshot.child("date").getValue(String.class);
                        String status = childSnapshot.child("status").getValue(String.class);

                        StatusModel model = new StatusModel(cname, date, status);
                        arr_statusdetail.add(model);

                        RecyclerStatusAdapter adapter=new RecyclerStatusAdapter(ComplainstatusActivity.this,arr_statusdetail);
                        rv_status.setAdapter(adapter);
                    }

                } else {
                    Toast.makeText(ComplainstatusActivity.this, "No complaint found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("onCancelled", "Failed to fetch data");
                Toast.makeText(ComplainstatusActivity.this,"Failed to fetch data",Toast.LENGTH_SHORT).show();
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