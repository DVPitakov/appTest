package com.example.dmitry.apptest;

/**
 * Created by dmitry on 28.09.17.
 */

public class UserData {
    public final static String userLogin = "userLogin";
    public final static String userPassword = "userPassword";

    public String login;
    public String password;


    public boolean isValid() {
        return login != null
                && password != null
                && login.length() > 0
                && password.length() > 0;

    }

    public UserData() {}

    public UserData(String login, String password){
        this.login = login;
        this.password = password;

    }

}
