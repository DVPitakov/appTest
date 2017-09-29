package com.example.dmitry.apptest;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by dmitry on 28.09.17.
 */

public class Rest {
    private final static String USER_KEY = "2b2cd8d0841d6e27b259";
    private final static String API_KEY = "7d5b74c0a1ef6f118764df99b3eb78a836f2a22a";
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

            Log.d("1996", "kuku epta2");
            String userDataAndPass = userData.login + ":" + userData.password;
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(userDataAndPass.getBytes());
            byte[] digest = md.digest();

            final String uri = Uri.parse(PROTOCOL + "://" + DOMAIN + "/user")
                    .buildUpon()
                    .build()
                    .toString();
            Log.d("1996", "kuku epta3");
            HttpURLConnection conn = (HttpURLConnection) new URL(uri).openConnection();

            Log.d("1996", "kuku epta4");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Basic "
                    + Base64.encodeToString(userDataAndPass.getBytes(), Base64.DEFAULT));
            Log.d("1996", "kuku epta5");
            conn.connect();
            Log.d("1996", "kuku epta6");

            int responseCode = conn.getResponseCode();
            Log.d("1996", "kuku epta7+++" + conn.getResponseCode());
            Log.d("1996", "kuku epta8");
            if (responseCode == 200) {
                is = conn.getInputStream();
                Log.d("1996", conn.getHeaderField("X-GitHub-SSO"));
                return inputStreamToString(is);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
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
