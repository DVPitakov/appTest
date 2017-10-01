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
    public String author;
    public String date;

    public Commit(JSONObject jsonObject) throws JSONException {
        JSONObject jo = jsonObject.getJSONObject("commit");
        message = jo.getString("message");
        sha = jsonObject.getString("sha");
        JSONObject jo2 = jo.getJSONObject("author");
        author = jo2.getString("email");
        date = jo2.getString("date");

    }

    public Commit(Parcel parcel) {
        sha = parcel.readString();
        message = parcel.readString();
        author = parcel.readString();
        date = parcel.readString();

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
        parcel.writeString(author);
        parcel.writeString(date);

    }
}
