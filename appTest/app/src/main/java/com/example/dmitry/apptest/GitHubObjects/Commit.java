package com.example.dmitry.apptest.GitHubObjects;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Handler;

/**
 * Created by dmitry on 30.09.17.
 */

public class Commit extends GitHubObject{
    public String sha;
    public String message;

    public Commit(JSONObject jsonObject) throws JSONException {
        message = jsonObject.getString("message");
        sha = jsonObject.getString("sha");

    }

    public Commit(Parcel parcel) {
        sha = parcel.readString();
        message = parcel.readString();

    }

    public static final Parcelable.Creator<Commit> CREATOR = new Parcelable.Creator<Commit>() {
        public Commit createFromParcel(Parcel in) {
            return new Commit(in);
        }

        @Override
        public Commit[] newArray(int i) {
            return new Commit[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(sha);
        parcel.writeString(message);

    }
}
