package com.example.dmitry.apptest;

/**
 * Created by dmitry on 28.09.17.
 */

public class ServerResponseOnSignIn {
    public final static String STATUS = "STATUS";
    public String status;
    public String key;
    public String currentUserUrl;
    public boolean validUserData = false;

    public ServerResponseOnSignIn() {};

    public boolean isOK() {
        return true;

    }


}
