package com.example.dmitry.apptest;

import android.net.Uri;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dmitry on 28.09.17.
 */

public class Rest {
    private final static String API_KEY = "2b0a2eeaceb58ff4429922cd163bf93de94729dd";
    private final static String DOMAIN = "api.github.com";
    private final static String PROTOCOL = "https";
    private static Rest instance;

    private Rest(){};

    public static Rest getInstance() {
        if (instance == null) {
            instance = new Rest();
        }
        return instance;
    }


    public ServerResponseOnSignIn signIn(UserData userData) throws IOException {
        InputStream is = null;
        try {
            final String uri = Uri.parse(PROTOCOL
                    + "://" + userData.login
                    + ":" + userData.password
                    + "@" + DOMAIN + "/user")
                    .buildUpon()
                    .build()
                    .toString();
            HttpURLConnection conn = (HttpURLConnection) new URL(uri).openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();

            int responseCode = conn.getResponseCode();
            is = conn.getInputStream();
            Log.d("debbb", conn.getHeaderField("X-GitHub-SSO"));
            if (responseCode == 200) {

                return inputStreamToString(is);
            }
        }
        finally {
            if(is != null) {
                is.close();
            }
        }
        return null;
    }

    private static ServerResponseOnSignIn inputStreamToString(final InputStream is) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line);
        }

        String res = stringBuilder.toString();
        ServerResponseOnSignIn serverResponseOnSignIn = new ServerResponseOnSignIn();
        try {
            //X-GitHub-SSO

            JSONObject jsonObject = new JSONObject(res);
            //cource.currency = jsonObject.getString("currency");
            //cource.value = Integer.toString(jsonObject.getInt("value"));
            //cource.status = Cource.OK;
        }
        catch (Exception e) {
            //cource.status = Cource.ERROR;
        }
        return serverResponseOnSignIn;
    }
}
