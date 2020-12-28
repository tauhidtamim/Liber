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

import java.util.HashMap;


public class SettingsFragment extends Fragment {

    String fullName, userName, password, email, gender, date;
    TextView textView;
    Button logoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_settings, container, false);

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
        textView = root.findViewById(R.id.settings_text);
        logoutBtn = root.findViewById(R.id.logout_btn);

        //display fetched data
        textView.setText(userName);

        //log out
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SessionManager sessionManager = new SessionManager(getActivity());
                sessionManager.logOutUser();

                System.out.println("In Settings Fragment: ");
                System.out.println(sessionManager.checkLogin());

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