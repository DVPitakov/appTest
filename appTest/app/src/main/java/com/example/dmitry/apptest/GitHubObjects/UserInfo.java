package com.example.dmitry.apptest.GitHubObjects;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dmitry on 29.09.17.
 */

public class UserInfo  extends GitHubObject {
    public String reposUrl;

    public UserInfo(JSONObject jsonObject) throws JSONException {
        reposUrl = jsonObject.getString("repos_url");

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(reposUrl);

    }
}
