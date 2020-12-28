package com.inxs5859.liber.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.inxs5859.liber.Databases.SessionManager;
import com.inxs5859.liber.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

/*

#for chip navigation bar

MIT License

Copyright (c) 2019 Ismael Di Vita

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

*/

public class UserDashboard extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        chipNavigationBar = findViewById(R.id.bottom_navigation_menu);
        chipNavigationBar.setItemSelected(R.id.mybooks,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MyBooksFragment()).commit();
        bottomMenu();
    }

    private void bottomMenu(){

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i){
                    case R.id.mybooks:
                        fragment = new MyBooksFragment();
                        break;
                    case R.id.search:
                        fragment = new SearchFragment();
                        break;
                    case R.id.stats:
                        fragment = new StatsFragment();
                        break;
                    case R.id.settings:
                        fragment = new SettingsFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });


    }



}