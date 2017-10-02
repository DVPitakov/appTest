package com.example.dmitry.apptest.GitHubObjects;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by dmitry on 30.09.17.
 */

public class CommitsList extends GitHubObject {

    public ArrayList<Commit> commits = new ArrayList<>();

    public CommitsList(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            commits.add(new Commit(jsonArray.getJSONObject(i)));
        }
    }

    public CommitsList(Parcel parcel) {
        int len = parcel.readInt();
        for(int i = 0; i < len; i++) {
            commits.add((Commit)(parcel.readParcelable(Commit.class.getClassLoader())));
        }

    }

    public static final Parcelable.Creator<CommitsList> CREATOR = new Parcelable.Creator<CommitsList>() {
        public CommitsList createFromParcel(Parcel in) {
            return new CommitsList(in);
        }

        @Override
        public CommitsList[] newArray(int i) {
            return new CommitsList[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(commits.size());
        for (Commit commit:commits) {
            parcel.writeParcelable(commit, 0);
        }
    }
}
