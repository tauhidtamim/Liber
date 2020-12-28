package com.inxs5859.liber.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inxs5859.liber.HelperClasses.UserHelperClass;
import com.inxs5859.liber.R;

import java.util.Calendar;

public class SignUpSecond extends AppCompatActivity {

    RadioButton selectedGender;
    RadioGroup radioGroup;
    DatePicker datePicker;
    String fullName, userName, email, password, gender, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_second);


        //receive data from first sign up screen
        Intent intent = getIntent();
        fullName = intent.getStringExtra("fullName");
        userName = intent.getStringExtra("userName");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.age_picker);

    }


    public void signUp(View view){

        if(!validateAge() || !validateGender()){
            return;
        }

        selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
        gender = selectedGender.getText().toString();

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        date = day+"/"+month+"/"+year;

        //System.out.println("I AM HERE");

        storeNewUserData();

        Toast.makeText(this, "You Have Signed Up!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), WelcomeScreen.class);
        startActivity(intent);
        finish();

    }

    private void storeNewUserData(){

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Users");

        UserHelperClass newUser = new UserHelperClass(fullName,userName,password,email,gender,date);
        reference.child(userName).setValue(newUser);

    }


    private boolean validateGender() {

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validateAge() {

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userBirth = datePicker.getYear();
        int userAge = currentYear - userBirth;

        if (userAge < 13) {
            Toast.makeText(this, "You should be older than 13 years!", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;

    }
}