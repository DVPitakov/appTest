package com.example.dmitry.diplomexplore;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Uri inputUri1;
    Uri inputUri2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent pickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickerIntent.setType("*/*");
        String[] mimetypes = {"image/*", "video/*"};
        pickerIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        startActivityForResult(pickerIntent, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode)
        {
            case 0:
            {
                if (resultCode == RESULT_OK) {
                    inputUri1 = data.getData();
                    Intent pickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    pickerIntent.setType("*/*");
                    String[] mimetypes = {"image/*", "video/*"};
                    pickerIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
                    startActivityForResult(pickerIntent, 1);

                }
                break;
            }
            case 1:
            {
                if (resultCode == RESULT_OK) {
                    inputUri2 = data.getData();
                    Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                    intent.putExtra(EditorActivity.INPUT_URI_1, inputUri1);
                    intent.putExtra(EditorActivity.INPUT_URI_2, inputUri2);
                    startActivityForResult(intent, 2);
                }
                break;
            }
        }
    }
}
