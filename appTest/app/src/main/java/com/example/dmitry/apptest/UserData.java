package com.example.dmitry.apptest;

/**
 * Created by dmitry on 28.09.17.
 */

public class UserData {
    public final static String userLogin = "userLogin";
    public final static String userPassword = "userPassword";
    public final static int LOGIN_ERROR = 1 << 0;
    public final static int PASSWORD_ERROR = 1 << 1;


    public String login;
    public String password;


    public boolean isValid() {
        return login != null
                && password != null
                && login.length() > 0
                && password.length() > 0;

    }

    public int ErrorType() {

        int result = 0;
        if (password != null && password.length() > 0) {
            result += LOGIN_ERROR;
        }
        if (login != null && login.length() > 0) {
            result += PASSWORD_ERROR;
        }
       return result;
    }

    public UserData() {}

    public UserData(String login, String password){
        this.login = login;
        this.password = password;

    }

}
