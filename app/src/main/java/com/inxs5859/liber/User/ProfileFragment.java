package com.inxs5859.liber.User;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.inxs5859.liber.Common.LoginSignUp.WelcomeScreen;
import com.inxs5859.liber.Databases.SessionManager;
import com.inxs5859.liber.R;

import java.time.Year;
import java.util.HashMap;


public class ProfileFragment extends Fragment {

    String fullName, userName, password, email, gender, date;
    TextView tvFull, tvUser, tvEmail, tvBirth, tvCopyright;
    Button logoutBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);


        //fetch user data from shared preference
        SessionManager sessionManager = new SessionManager(getActivity());
        HashMap<String,String> userDetails = sessionManager.getUserDetailsFromSession();
        fullName = userDetails.get(SessionManager.KEY_FULLNAME);
        userName = userDetails.get(SessionManager.KEY_USERNAME);
        password = userDetails.get(SessionManager.KEY_PASSWORD);
        email = userDetails.get(SessionManager.KEY_EMAIL);
        gender = userDetails.get(SessionManager.KEY_GENDER);
        date = userDetails.get(SessionManager.KEY_DATE);

        //hooks
        logoutBtn = root.findViewById(R.id.logout_btn);
        tvFull = root.findViewById(R.id.full_name);
        tvUser = root.findViewById(R.id.user_name);
        tvEmail = root.findViewById(R.id.email);
        tvBirth = root.findViewById(R.id.bday);
        tvCopyright = root.findViewById(R.id.copyright);

        //format strings to display
        String temp_full = "Full Name: " + fullName;
        String temp_user = "User Name: " + userName;
        String temp_email = "Email: " + email;
        String temp_birth = "Birthday: " + date;
        String copy = "©";
        copy = copy + String.valueOf(Year.now().getValue());

        //display fetched data
        tvFull.setText(temp_full);
        tvUser.setText(temp_user);
        tvEmail.setText(temp_email);
        tvBirth.setText(temp_birth);
        tvCopyright.setText(copy);


        //log out
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SessionManager sessionManager = new SessionManager(getActivity());
                sessionManager.logOutUser();

                //System.out.println("In Settings Fragment: ");
                //System.out.println(sessionManager.checkLogin());

                Intent intent = new Intent(getActivity(), WelcomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return root;
    }


}