package com.example.dmitry.apptest.GitHubObjects;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by dmitry on 29.09.17.
 */

public class ReposList extends GitHubObject {
    public ArrayList<Repo> repos = new ArrayList<>();

    public ReposList(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            repos.add(new Repo(jsonArray.getJSONObject(i)));
        }

    }

    public ReposList(Parcel parcel) {
        int len = parcel.readInt();
        for(int i = 0; i < len; i++) {
            repos.add((Repo)(parcel.readParcelable(Repo.class.getClassLoader())));
        }

    }

    public static final Parcelable.Creator<ReposList> CREATOR = new Parcelable.Creator<ReposList>() {
        public ReposList createFromParcel(Parcel in) {
            return new ReposList(in);
        }

        @Override
        public ReposList[] newArray(int i) {
            return new ReposList[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        for (Repo repo:repos) {
            parcel.writeParcelable(repo, 0);
        }
    }
}
