package com.inxs5859.liber.Common.LoginSignUp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.provider.Settings;
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

        //check internet connection
        if(!isConnected(this)){
            showCustomDialog();
            return;
        }

        Intent intent = new Intent(getApplicationContext(),Login.class);

        Pair[] pairs  = new Pair[1];
        pairs[0] = new Pair<View,String>(findViewById(R.id.login_btn),"transition_login");

        //DOESN'T WORK BELOW LOLLIPOP
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(WelcomeScreen.this,pairs);
        startActivity(intent,options.toBundle());

    }

    public void callSignUpScreen(View view){

        //check internet connection
        if(!isConnected(this)){
            showCustomDialog();
            return;
        }

        Intent intent = new Intent(getApplicationContext(),SignUp.class);

        Pair[] pairs  = new Pair[1];
        pairs[0] = new Pair<View,String>(findViewById(R.id.signup_btn),"transition_signup");

        //DOESN'T WORK BELOW LOLLIPOP
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(WelcomeScreen.this,pairs);
        startActivity(intent,options.toBundle());

    }


    private boolean isConnected(WelcomeScreen welcomeScreen) {

        ConnectivityManager connectivityManager = (ConnectivityManager) welcomeScreen.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null) {
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());

            if(networkCapabilities != null){

                if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                    return true;
                }

            }
        }

        return false;
    }

    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please connect to the internet to proceed further!")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}