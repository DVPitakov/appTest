package com.example.dmitry.diplomexplore;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dmitry on 02.10.17.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{


    Context context;
    SurfaceHolder surfaceHolder;
    ContentResolver cR;

    Activity activity;

    Uri inputUri1;
    Uri inputUri2;

    public MySurfaceView(Context context, Activity activity, Uri inputUri1, Uri inputUri2) {
        super(context);
        getHolder().addCallback(this);
        this.cR = context.getContentResolver();
        this.context = context;
        this.inputUri1 = inputUri1;
        this.inputUri2 = inputUri2;
        this.activity = activity;

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        int tw = this.getWidth();
        int th = this.getHeight();
        draw();

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    public void draw() {

            Surface surface = surfaceHolder.getSurface();
            Canvas canvas = surface.lockCanvas(null);
            Paint paint = new Paint();

            canvas.drawColor(Color.WHITE);

        try {
            Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(
                    activity.getContentResolver(), inputUri1);

            Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(
                activity.getContentResolver(), inputUri2);

            int iw1 = bitmap1.getWidth();
            int ih1 = bitmap1.getHeight();

            int iw2 = bitmap2.getWidth();
            int ih2 = bitmap2.getHeight();

            canvas.drawBitmap(bitmap1, 0, 0, paint);
            canvas.drawBitmap(bitmap2, 0, ih1, paint);

            int [] bitmapArr1 = new int[bitmap1.getWidth() * bitmap1.getHeight()];
            int [] bitmapArr2 = new int[bitmap2.getWidth() * bitmap2.getHeight()];
            int [] bitmapArr = new int[bitmap1.getWidth() * bitmap1.getHeight()];
            bitmap1.getPixels(bitmapArr1, 0, 0, 0, 0, bitmap1.getWidth(), bitmap1.getHeight());
            bitmap1.getPixels(bitmapArr2, 0, 0, 0, 0, bitmap2.getWidth(), bitmap2.getHeight());

            for (int i = 0; i < bitmap1.getWidth() * bitmap1.getHeight(); i++) {
                bitmapArr[i] = (int)Math.sqrt(bitmapArr1[i] * bitmapArr1[i]
                        - bitmapArr2[i] * bitmapArr2[i]);
            }
            //Bitmap bitmap = Bitmap.createBitmap(bitmapArr
            //        , bitmap1.getWidth()
            //        , bitmap1.getHeight()
            //        , Bitmap.Config.ALPHA_8);
           // canvas.drawBitmap(bitmap, 0, ih1 + ih2, paint);

        } catch (IOException e) {
            e.printStackTrace();
        }

        surface.unlockCanvasAndPost(canvas);

    }

}
