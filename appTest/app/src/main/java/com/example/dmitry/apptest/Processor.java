package com.example.dmitry.apptest;

import android.util.Log;

import java.io.IOException;

/**
 * Created by dmitry on 28.09.17.
 */

public class Processor {
    private Processor() {}

    private static Processor instance;

    public static Processor getInstance() {
        if (instance == null) {
            instance = new Processor();
        }
        return instance;
    }

    ServerResponseOnSignIn trySignIn() {
        ServerResponseOnSignIn serverResponseOnSignIn;
        UserData userData = Storage.getInstance(null).getUserData();
        if (userData.isValid()) {
            try {
                serverResponseOnSignIn = Rest.getInstance().signIn(userData);
                if(serverResponseOnSignIn.isOK()) {
                    Storage.getInstance(null).saveUserData(userData);
                }
            } catch (IOException e) {
                serverResponseOnSignIn = new ServerResponseOnSignIn();
                Log.d("err", "invalid user data");

            }
        }
        else {
            serverResponseOnSignIn = new ServerResponseOnSignIn();
        }
        return serverResponseOnSignIn;

    }
}
