package com.example.dmitry.apptest.GitHubObjects;

/**
 * Created by dmitry on 29.09.17.
 */

public class ServerResponse {
    public final static String STATUS = "STATUS";
    public final static String PARCEABLE = "PARCEABLE";
    public int status;
    public GitHubObject gitHubObject;

    public ServerResponse(int status, GitHubObject gitHubObject) {
        this.status = status;
        this.gitHubObject = gitHubObject;
    }
}
