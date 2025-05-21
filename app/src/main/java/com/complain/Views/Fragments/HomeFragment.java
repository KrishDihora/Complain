package com.complain.Views.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.complain.Views.Activitys.QuickcomplainActivity;
import com.complain.R;
import com.complain.Views.Activitys.ComplainActivity;
import com.complain.Views.Activitys.ComplainstatusActivity;
import com.complain.Views.Activitys.FaqActivity;


public class HomeFragment extends Fragment {

    ImageView iv_quickcomplain;
    ImageView iv_complain;
    ImageView iv_complainstatus;
    ImageView iv_faq;
    private FragmentManager supportFragmentManager;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        iv_quickcomplain=view.findViewById(R.id.iv_quickcomplain);
        iv_complain=view.findViewById(R.id.iv_complain);
        iv_complainstatus=view.findViewById(R.id.iv_complainstatus);
        iv_faq=view.findViewById(R.id.iv_faq);

        iv_quickcomplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quickcomplain=new Intent(getContext(), QuickcomplainActivity.class);
                startActivity(quickcomplain);

            }
        });

        iv_complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent complain=new Intent(getContext(), ComplainActivity.class);
                startActivity(complain);

            }
        });

        iv_complainstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent complainstatus=new Intent(getContext(), ComplainstatusActivity.class);
                startActivity(complainstatus);

            }
        });

        iv_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent faq=new Intent(getContext(), FaqActivity.class);
                startActivity(faq);

            }
        });





        return  view;
    }


}