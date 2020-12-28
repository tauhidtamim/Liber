package com.inxs5859.liber.Common.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.inxs5859.liber.Databases.SessionManager;
import com.inxs5859.liber.R;
import com.inxs5859.liber.User.UserDashboard;

public class Login extends AppCompatActivity {


    TextInputEditText userName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

    }

    public void validateFields(View view) {


        //hooks
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);


        final String name = userName.getText().toString();
        final String pass = password.getText().toString();

        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild(name);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    userName.setError(null);

                    String fetchedPassword = snapshot.child(name).child("password").getValue(String.class);
                    if (fetchedPassword.equals(pass)) {
                        password.setError(null);

                        String fullName = snapshot.child(name).child("fullName").getValue(String.class);
                        String userName = snapshot.child(name).child("userName").getValue(String.class);
                        String password = snapshot.child(name).child("password").getValue(String.class);
                        String email = snapshot.child(name).child("email").getValue(String.class);
                        String gender = snapshot.child(name).child("gender").getValue(String.class);
                        String date = snapshot.child(name).child("date").getValue(String.class);

                        //Session

                        SessionManager sessionManager = new SessionManager(Login.this);
                        sessionManager.createLoginSession(fullName,userName,password,email,gender,date);

                        Intent intent = new Intent(Login.this, UserDashboard.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(Login.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "No User Profile!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }


}