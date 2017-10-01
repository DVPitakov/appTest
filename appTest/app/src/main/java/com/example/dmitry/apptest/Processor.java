package com.example.dmitry.apptest;

import android.support.annotation.Nullable;
import android.util.Log;

import com.example.dmitry.apptest.GitHubObjects.ServerResponse;
import com.example.dmitry.apptest.GitHubObjects.UserInfo;

import java.io.IOException;

/**
 * Created by dmitry on 28.09.17.
 */

public class Processor {
    private Processor() {}

    private static Processor instance;
    private static UserInfo userInfo;

    public static Processor getInstance() {
        if (instance == null) {
            instance = new Processor();
        }
        return instance;
    }

    @Nullable
    ServerResponse trySignIn() {
        ServerResponse serverResponse;
        UserData userData = Storage.getInstance(null).getUserData();
        if (userData.isValid()) {
            try {
                serverResponse = Rest.getInstance().signIn(userData);
                if(serverResponse != null && serverResponse.status == 200) {
                    return Rest.getInstance().signIn(userData);
                }
            } catch (IOException e) {
                Log.d("err", "invalid user data");

            }
        }
        return null;

    }

    ServerResponse getReposList(String uri) {
        UserData userData = Storage.getInstance(null).getUserData();
        if (userData.isValid()) {
            try {
                return Rest.getInstance().getUserRepos(userData, uri);
            } catch (IOException e) {
                Log.d("err", "invalid user data");

            }
        }
        return null;

    }

    ServerResponse getImage(String imageUri) {
        UserData userData = Storage.getInstance(null).getUserData();
        if (userData.isValid()) {
            try {
                return Rest.getInstance().loadImage(imageUri);
            } catch (IOException e) {
                Log.d("err", "invalid user data");

            }
        }
        return null;

    }

    ServerResponse getCommitsList(String uri) {
        UserData userData = Storage.getInstance(null).getUserData();
        if (userInfo == null) {
            trySignIn();
        }
        if (userData.isValid()) {
            try {
                return Rest.getInstance().getCommits(userData, uri);
            } catch (IOException e) {
                Log.d("err", "invalid user data");

            }
        }
        return null;

    }
}
