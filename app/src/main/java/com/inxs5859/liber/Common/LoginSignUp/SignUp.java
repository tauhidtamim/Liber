package com.inxs5859.liber.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.inxs5859.liber.R;

public class SignUp extends AppCompatActivity {


    ImageView backBtn;
    Button next;
    TextView titleText;

    TextInputEditText fullName, userName, email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        backBtn = findViewById(R.id.signup_back_btn);
        next = findViewById(R.id.signup_next_btn);
        titleText = findViewById(R.id.signup_title_text);

        fullName = findViewById(R.id.full_name_text_box);
        userName = findViewById(R.id.user_name_text_box);
        email = findViewById(R.id.email_text_box);
        password = findViewById(R.id.password_text_box);
    }

    public void callNextSignUpScreen(View view) {


        if(!validateEmail() || !validateFullName() || !validatePass() || !validateUserName()){
            return;
        }

        Intent intent = new Intent(getApplicationContext(), SignUpSecond.class);

        //send data
        intent.putExtra("fullName",fullName.getText().toString());
        intent.putExtra("userName",userName.getText().toString());
        intent.putExtra("email",email.getText().toString());
        intent.putExtra("password",password.getText().toString());

        //transitions
        Pair[] pairs = new Pair[3];
        pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair<View, String>(titleText, "transition_signup_title");
        pairs[2] = new Pair<View, String>(next, "transition_next_btn");


        //call next activity
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
        startActivity(intent, options.toBundle());

    }

    private boolean validateFullName(){

        String s = fullName.getText().toString().trim();

        if(s.isEmpty()){
            fullName.setError("Field Can't Be Empty");
            return false;
        }else{
            fullName.setError(null);
            return true;
        }
    }

    private boolean validateUserName(){

        String s = userName.getText().toString().trim();
        String whitespaces = "\\A\\w{1,20}\\z";

        if(s.isEmpty()) {
            userName.setError("Field can't be empty!");
            return false;
        }else if(s.length()>20){
            userName.setError("Username is too long!");
            return false;
        }else if(!s.matches(whitespaces)){
            userName.setError("No spaces allowed!");
            return false;
        }else{
            userName.setError(null);
            return true;
        }
    }

    private boolean validateEmail(){

        String s = email.getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(s.isEmpty()) {
            email.setError("Field can't be empty!");
            return false;
        }else if(!s.matches(checkEmail)){
            email.setError("Invalid email!");
            return false;
        }else{
            email.setError(null);
            return true;
        }
    }

    private boolean validatePass(){

        String s = password.getText().toString().trim();

        if(s.isEmpty()) {
            password.setError("Field can't be empty!");
            return false;
        }else if(s.length()<5){
            password.setError("Should contain at least 5 characters!");
            return false;
        }else{
            password.setError(null);
            return true;
        }
    }




}