package com.example.dmitry.apptest.GitHubObjects;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dmitry on 30.09.17.
 */

public class GitHubImage extends GitHubObject{
    public String uri;
    public Bitmap bitmap;
    public GitHubImage(String uri, Bitmap bitmap) {
        this.uri = uri;
        this.bitmap = bitmap;

    }

    public GitHubImage(Parcel parcel) {
        uri = parcel.readString();
        bitmap = parcel.readParcelable(Bitmap.class.getClassLoader());

    }

    public static final Parcelable.Creator<GitHubImage> CREATOR = new Parcelable.Creator<GitHubImage>() {
        public GitHubImage createFromParcel(Parcel in) {
            return new GitHubImage(in);
        }

        @Override
        public GitHubImage[] newArray(int i) {
            return new GitHubImage[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uri);
        parcel.writeParcelable(bitmap, 0);
    }
}
