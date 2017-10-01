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

    public final static int CONNECTION_ERROR_1 = -1;
    public final static int CONNECTION_ERROR_2 = -2;
    public final static int CONNECTION_ERROR_3 = -3;
    public final static int CONNECTION_ERROR_4 = -4;


    private static Processor instance;

    public static Processor getInstance() {
        if (instance == null) {
            instance = new Processor();
        }
        return instance;
    }

    @Nullable
    ServerResponse trySignIn(UserData userData) {
        try {
            return Rest.getInstance().signIn(userData);
        } catch (IOException e) {
            return new ServerResponse(CONNECTION_ERROR_1, null);

        }

    }

    ServerResponse getReposList(UserData userData, String uri) {
        try {
            return Rest.getInstance().getUserRepos(userData, uri);
        } catch (IOException e) {
            return new ServerResponse(CONNECTION_ERROR_2, null);
        }

    }

    ServerResponse getImage(String imageUri) {
        try {
            return Rest.getInstance().loadImage(imageUri);
        } catch (IOException e) {
            return new ServerResponse(CONNECTION_ERROR_3, null);
        }



    }

    ServerResponse getCommitsList(UserData userData, String uri) {
        try {
            return Rest.getInstance().getCommits(userData, uri);
        } catch (IOException e) {
            return new ServerResponse(CONNECTION_ERROR_4, null);
        }

    }
}
