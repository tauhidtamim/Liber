package com.inxs5859.liber.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.inxs5859.liber.Common.LoginSignUp.Login;
import com.inxs5859.liber.Common.LoginSignUp.WelcomeScreen;
import com.inxs5859.liber.R;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIMER = 2000;

    //variables
    ImageView backgroundImage;

    //animations
    Animation sideAnim;

    //shared preference

    SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        //hooks
        backgroundImage = findViewById(R.id.background_image);
        sideAnim = AnimationUtils.loadAnimation(this,R.anim.side_anim);

        //set animations to elements
        backgroundImage.setAnimation(sideAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
                boolean isFirstTime = onBoardingScreen.getBoolean("firstTime",true);

                if(isFirstTime) {

                    SharedPreferences.Editor editor = onBoardingScreen.edit();
                    editor.putBoolean("firstTime", false);
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), OnBoarding.class);//Onboarding e hobe, test er jonno login deya
                    startActivity(intent);
                    finish();

                }else{

                    Intent intent = new Intent(getApplicationContext(), WelcomeScreen.class);//welcome screen hobe
                    startActivity(intent);
                    finish();
                }
            }


        }, SPLASH_TIMER);

    }
}