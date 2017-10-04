package com.example.dmitry.diplomexplore;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.io.IOException;

public class EditorActivity extends AppCompatActivity {

    public static final String INPUT_URI_1 = "INPUT_URI_1";
    public static final String INPUT_URI_2 = "INPUT_URI_2";

    private Uri inputUri1;
    private Uri inputUri2;

    private MySurfaceView mySurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Intent intent = getIntent();
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
        inputUri1 = intent.getParcelableExtra(INPUT_URI_1);
        inputUri2 = intent.getParcelableExtra(INPUT_URI_2);
        mySurfaceView = new MySurfaceView(linearLayout.getContext(), this, inputUri1, inputUri2);
        mySurfaceView.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));

        linearLayout.addView(mySurfaceView);

    }
}
