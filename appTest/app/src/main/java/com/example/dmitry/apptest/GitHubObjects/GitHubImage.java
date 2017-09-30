package com.example.dmitry.apptest.GitHubObjects;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;

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
