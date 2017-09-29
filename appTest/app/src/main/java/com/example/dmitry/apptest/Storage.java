package com.example.dmitry.apptest;

import android.content.Context;
import android.content.SharedPreferences;

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
            this.preferences.edit().putString(UserData.userLogin, userData.login).apply();
            this.preferences.edit().putString(UserData.userPassword, userData.password).apply();
        }
    }

    public UserData getUserData() {
        String userLogin = this.preferences.getString(UserData.userLogin, null);
        String userPassword = this.preferences.getString(UserData.userLogin, null);
        return new UserData(userLogin, userPassword);
    }

}
