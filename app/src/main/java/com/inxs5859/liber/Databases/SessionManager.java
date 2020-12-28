package com.inxs5859.liber.Databases;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context; //to know which activity is calling this class

    private static final String IS_LOGIN = "isLoggedIn";

    //we create session for these variables
    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_USERNAME = "userName";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_DATE = "date";

    public SessionManager(Context _context) {

        context = _context;
        userSession = context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);//session id is userLoginSession
        editor = userSession.edit();//using editor, we add/delete things inside session
    }

    public void createLoginSession(String _fullName, String _userName, String _password, String _email, String _gender, String _date) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_FULLNAME, _fullName);
        editor.putString(KEY_USERNAME, _userName);
        editor.putString(KEY_PASSWORD, _password);
        editor.putString(KEY_EMAIL, _email);
        editor.putString(KEY_GENDER, _gender);
        editor.putString(KEY_DATE, _date);

        editor.commit();
    }

    public HashMap<String, String> getUserDetailsFromSession() {

        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_FULLNAME, userSession.getString(KEY_FULLNAME, null));
        userData.put(KEY_USERNAME, userSession.getString(KEY_USERNAME, null));
        userData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_GENDER, userSession.getString(KEY_GENDER, null));
        userData.put(KEY_DATE, userSession.getString(KEY_DATE, null));


        return userData;
    }

    public boolean checkLogin() {

        return userSession.getBoolean(IS_LOGIN, false);
    }

    public void logOutUser() {

        editor.clear();
        editor.commit();
    }


}
