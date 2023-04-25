package com.complain;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.net.URI;

public class HelplineFragment extends Fragment {

    ConstraintLayout cl_num1;
    ConstraintLayout cl_num2;
    ConstraintLayout cl_num3;
    ConstraintLayout cl_num4;
    ConstraintLayout cl_num5;
    ConstraintLayout cl_num6;



    public HelplineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_helpline, container, false);

       cl_num1=view.findViewById(R.id.cl_num1);
        cl_num2=view.findViewById(R.id.cl_num2);
        cl_num3=view.findViewById(R.id.cl_num3);
        cl_num4=view.findViewById(R.id.cl_num4);
        cl_num5=view.findViewById(R.id.cl_num5);
        cl_num6=view.findViewById(R.id.cl_num6);


       cl_num1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent sendnumber=new Intent(Intent.ACTION_DIAL);
               sendnumber.setData(Uri.parse("number:8780111310"));
               startActivity(sendnumber);


               
           }
       });






       return view;
    }


}