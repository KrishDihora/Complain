package com.complain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


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

       /* iv_quickcomplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new QuickcomplainFragment(),1);
            }
        });

        iv_complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new ComplainrequestFragment(),1);
            }
        });

        iv_complainstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new ComplainstatusFragment(),1);
            }
        });

        iv_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new FaqFragment(),1);
            }
        });

*/


        return  view;
    }

   /* public void loadFragment(Fragment fragment, int flag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (flag == 0) {
            ft.add(R.id.framelayout, fragment);
            *//*fm.popBackStack(R.id.home,FragmentManager.POP_BACK_STACK_INCLUSIVE);*//*
            ft.commit();
        } else {
            ft.replace(R.id.framelayout, fragment);
           *//* ft.addToBackStack(null);*//*
            ft.commit();
        }
    }


    public FragmentManager getSupportFragmentManager() {
        return supportFragmentManager;
    }

    public void setSupportFragmentManager(FragmentManager supportFragmentManager) {
        this.supportFragmentManager = supportFragmentManager;
    }*/
}