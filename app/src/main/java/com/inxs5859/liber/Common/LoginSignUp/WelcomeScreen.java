package com.inxs5859.liber.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import com.inxs5859.liber.R;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome_screen);
    }

    public void callLoginScreen(View view){

        Intent intent = new Intent(getApplicationContext(),Login.class);

        Pair[] pairs  = new Pair[1];
        pairs[0] = new Pair<View,String>(findViewById(R.id.login_btn),"transition_login");

        //DOESN'T WORK BELOW LOLLIPOP
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(WelcomeScreen.this,pairs);
        startActivity(intent,options.toBundle());

    }
}