package com.example.dmitry.apptest.GitHubObjects;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

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

    public Owner(Parcel parcel) {
        login = parcel.readString();
        avatarUrl = parcel.readString();

    }

    public static final Parcelable.Creator<Owner> CREATOR = new Parcelable.Creator<Owner>() {
        public Owner createFromParcel(Parcel in) {
            return new Owner(in);
        }

        @Override
        public Owner[] newArray(int i) {
            return new Owner[i];
        }
    };

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
