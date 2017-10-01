package com.example.dmitry.apptest;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by dmitry on 28.09.17.
 */

public class Storage {
    private static Storage instance;
    private SharedPreferences preferences;

    private Storage(Context context) {
        this.preferences = context.getSharedPreferences("News", 0);

    }

    public static synchronized Storage getInstance(Context context) {
        if(instance == null) {
            instance  = new Storage(context.getApplicationContext());
        }
        return instance;

    }

    public void saveUserData(UserData userData) {
        if (userData == null) {
            this.preferences.edit().remove(UserData.userLogin).apply();
            this.preferences.edit().remove(UserData.userPassword).apply();
        }
        else {
            Log.d("1996", "123=" + userData.userLogin);
            Log.d("1996", "123=" + userData.userPassword);
            this.preferences.edit().putString(UserData.userLogin, userData.login).apply();
            this.preferences.edit().putString(UserData.userPassword, userData.password).apply();
        }
    }

    public UserData getUserData() {
        String userLogin = this.preferences.getString(UserData.userLogin, null);
        String userPassword = this.preferences.getString(UserData.userPassword, null);
        Log.d("1996", "456=" + userLogin);
        Log.d("1996", "456=" + userPassword);
        return new UserData(userLogin, userPassword);
    }

}
