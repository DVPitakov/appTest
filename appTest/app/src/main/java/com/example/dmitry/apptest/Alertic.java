package com.example.dmitry.apptest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by dmitry on 01.10.17.
 */

public class Alertic {
    private Alertic (){}

    public static void show(Activity activity, String header, String body) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(header)
                .setMessage(body)
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
