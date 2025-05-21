package com.complain.Views.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.complain.R;
import com.complain.Views.Fragments.ChatFragment;
import com.complain.Views.Fragments.HelplineFragment;
import com.complain.Views.Fragments.HomeFragment;
import com.complain.Views.Fragments.NoticeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    /*define variable*/

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView drawernavigation;
    BottomNavigationView bottomNavigation;
    TextView logout;
    FirebaseAuth auth;
    SharedPreferences userPreference;
    SharedPreferences.Editor userEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*define id*/

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerlayout);
        drawernavigation = findViewById(R.id.drawernavigation);
        bottomNavigation = findViewById(R.id.bottomnavigation);
        logout=findViewById(R.id.logout);

       /* Its needed to access toolbar or to work on it*/
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();

        //Local Database
        userPreference = getSharedPreferences("user",MODE_PRIVATE);
        userEditor = userPreference.edit();

        ActionBarDrawerToggle action = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(action);
        action.syncState();


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);

        drawernavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.home) {
                    loadFragment(new HomeFragment(), 1);
                    //bottomNavigation.setSelectedItemId(R.id.home);
                } /*else if (id == R.id.chat) {
                    loadFragment(new ChatFragment(), 1);
                    bottomNavigation.setSelectedItemId(R.id.chat);
                }*/ else if (id == R.id.helpline) {
                    loadFragment(new HelplineFragment(), 1);
                    //bottomNavigation.setSelectedItemId(R.id.helpline);
                } /*else if (id == R.id.notice) {
                    loadFragment(new NoticeFragment(), 1);
                    bottomNavigation.setSelectedItemId(R.id.notice);
                }*/ else if (id==R.id.complain) {
                    Intent complain=new Intent(MainActivity.this, ComplainActivity.class);
                    startActivity(complain);
                } else if (id==R.id.complainstatus) {
                    Intent complainstatus=new Intent(MainActivity.this, ComplainstatusActivity.class);
                    startActivity(complainstatus);
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                auth.signOut();
                userEditor.clear();
                userEditor.commit();
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
                finish();
            }
        });



        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.home) {
                    loadFragment(new HomeFragment(), 1);
                } /*else if (id == R.id.chat) {
                    loadFragment(new ChatFragment(), 1);
                }*/ else if (id == R.id.helpline) {
                    loadFragment(new HelplineFragment(), 1);
                }/* else if (id == R.id.notice) {
                    loadFragment(new NoticeFragment(), 1);
                }*/

                return true;
            }
        });
        bottomNavigation.setSelectedItemId(R.id.home);



    }



    public void loadFragment(Fragment fragment, int flag) {
        FragmentManager fm = getSupportFragmentManager();

        // Use a tag to identify the fragment in the back stack
        String fragmentTag = fragment.getClass().getSimpleName();
        //Log.d("TAGss", fragmentTag);
        Fragment existingFragment = fm.findFragmentByTag(fragmentTag);

        if (existingFragment == null || !existingFragment.isAdded())
        {
            FragmentTransaction ft = fm.beginTransaction();

            ft.replace( R.id.framelayout, fragment, fragmentTag);
            ft.addToBackStack(fragmentTag);
            ft.commit();
        }
        else
        {
            //---
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else if(fm.getBackStackEntryCount() > 1) {

            fm.popBackStackImmediate();

            // Get the currently displayed fragment and update the BottomNavigationView
            Fragment currentFragment = fm.findFragmentById(R.id.framelayout);
            //Log.d("TAG", "onBackPressed: "+ R.id.framelayout);

            if (currentFragment instanceof HomeFragment)
            {
                bottomNavigation.setSelectedItemId(R.id.home);
                //((HomeFragment) currentFragment).handleOnBackPressed();
            }
            else if (currentFragment instanceof HelplineFragment)
            {
                bottomNavigation.setSelectedItemId(R.id.helpline);
                //((NewsFragment) currentFragment).handleOnBackPressed();
            }
            else
            {
                //---
            }

        }
        else if (fm.getBackStackEntryCount() == 1)
        {
            finish();
        }
        else
        {
            finish();
            super.onBackPressed();
        }
    }

}