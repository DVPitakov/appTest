package com.example.dmitry.apptest.GitHubObjects;

import android.os.Bundle;
import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dmitry on 30.09.17.
 */

public class Owner extends GitHubObject {
    public String login;
    public String avatarUrl;

    public Owner(JSONObject jsonObject) throws JSONException {
        login = jsonObject.getString("login");
        avatarUrl = jsonObject.getString("avatar_url");

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(login);
        parcel.writeString(avatarUrl);
    }
}
