package com.example.dmitry.apptest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;

import com.example.dmitry.apptest.GitHubObjects.CommitsList;
import com.example.dmitry.apptest.GitHubObjects.GitHubImage;
import com.example.dmitry.apptest.GitHubObjects.ReposList;
import com.example.dmitry.apptest.GitHubObjects.ServerResponse;
import com.example.dmitry.apptest.GitHubObjects.UserInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
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


    public ServerResponse loadImage(String imageUri) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(imageUri).openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setRequestProperty("Accept", "application/json");
        if (conn.getResponseCode() == 200) {
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            GitHubImage gitHubImage = new GitHubImage(imageUri, BitmapFactory.decodeStream(bis));
            return new ServerResponse(conn.getResponseCode(), gitHubImage);

        }
        return null;

    }

    public ServerResponse getUserRepos(UserData userData, UserInfo userInfo) throws IOException {
        InputStream is = null;
        try {
            HttpURLConnection conn = sendRequest(userData, userInfo.reposUrl);
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                is = conn.getInputStream();
                return new ServerResponse(responseCode, inputJsonStreamToRepos(is));
            }
        } finally {
            if(is != null) {
                is.close();
            }
        }
        return null;
    }


    public ServerResponse signIn(UserData userData) throws IOException {
        InputStream is = null;
        try {
            HttpURLConnection conn = sendRequest(userData, PROTOCOL + "://" + DOMAIN + "/user");
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                is = conn.getInputStream();
                return new ServerResponse(responseCode, inputJsonStreamToUserInfo(is));
            }
        } finally {
            if(is != null) {
                is.close();
            }
        }
        return null;

    }

    private HttpURLConnection sendRequest(UserData userData, String path) throws IOException {
        String userDataAndPass = userData.login + ":" + userData.password;
        final String uri = Uri.parse(path)
                .buildUpon()
                .build()
                .toString();
        HttpURLConnection conn = (HttpURLConnection) new URL(uri).openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", "Basic "
                + Base64.encodeToString(userDataAndPass.getBytes(), Base64.DEFAULT));
        conn.connect();
        return conn;
    }

    @Nullable
    private static UserInfo inputJsonStreamToUserInfo(final InputStream is) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line);
        }
        String res = stringBuilder.toString();
        UserInfo userInfo = null;
        try {
            userInfo = new UserInfo(new JSONObject(res));
        }
        catch (Exception e) {}
        return userInfo;

    }

    @Nullable
    private static ReposList inputJsonStreamToRepos(final InputStream is) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line);
        }
        String res = stringBuilder.toString();
        ReposList reposList = null;
        try {
            reposList = new ReposList(new JSONArray(res));

        }
        catch (Exception e) {}
        return reposList;

    }

    @Nullable
    private static CommitsList inputJsonStreamToCommits(final InputStream is) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line);
        }
        String res = stringBuilder.toString();
        CommitsList commitsList = null;
        try {
            commitsList = new CommitsList(new JSONArray(res));

        }
        catch (Exception e) {}
        return commitsList;

    }
}

