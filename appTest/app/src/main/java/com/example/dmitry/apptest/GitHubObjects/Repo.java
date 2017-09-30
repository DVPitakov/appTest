package com.example.dmitry.apptest.GitHubObjects;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dmitry on 29.09.17.
 */

public class Repo extends GitHubObject {
    public String name;
    public String description;

    public int forks;
    public int watchers;

    public Owner owner;

    Repo(JSONObject jsonObject) throws JSONException {
        name = jsonObject.getString("name");
        //description = jsonObject.getString("description");
        forks = jsonObject.getInt("forks");
        watchers = jsonObject.getInt("watchers");
        owner = new Owner(jsonObject.getJSONObject("owner"));

    }

     private Repo(Parcel parcel) {
         name = parcel.readString();
         description = parcel.readString();
         forks = parcel.readInt();
         watchers = parcel.readInt();
         owner = (Owner)parcel.readParcelable(Owner.class.getClassLoader());

    }

    public static final Parcelable.Creator<Repo> CREATOR = new Parcelable.Creator<Repo>() {
        public Repo createFromParcel(Parcel in) {
            return new Repo(in);
        }

        @Override
        public Repo[] newArray(int i) {
            return new Repo[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeInt(forks);
        parcel.writeInt(watchers);
        parcel.writeParcelable(owner, 0);

    }
}
