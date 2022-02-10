package io.github.muliphein.toolkits.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;

public class PictureReader {
    final String TAG = "PictureReader";
    public PictureReader(){}
    public Bitmap picReaderAsBitmap(File fileName){
        Bitmap result = null;
        try{
            FileInputStream fin = new FileInputStream(fileName);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            result = BitmapFactory.decodeStream(fin, null, options);
            int picWidth = options.outWidth;
            int picHeight = options.outHeight;
            options.inJustDecodeBounds = false;
            fin.close();
            fin = new FileInputStream(fileName);
            result = BitmapFactory.decodeStream(fin, null, options);
            fin.close();
        } catch (Exception e) {
            Log.e(TAG, "getThumb: File Open Error!");
        }
        return result;
    }
    public Bitmap picReaderAsBitmapWithBounding(File fileName, int maxWidth, int maxHeight){
        Bitmap result = null;
        try{
            FileInputStream fin = new FileInputStream(fileName);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            result = BitmapFactory.decodeStream(fin, null, options);
            int picWidth = options.outWidth;
            int picHeight = options.outHeight;
            int sampleRatio = Math.max(picWidth/maxWidth, picHeight/maxHeight);
            options.inJustDecodeBounds = false;
            options.inSampleSize = sampleRatio;
            fin.close();
            fin = new FileInputStream(fileName);
            result = BitmapFactory.decodeStream(fin, null, options);
            fin.close();
        } catch (Exception e) {
            Log.e(TAG, "getThumb: File Open Error!");
        }
        return result;
    }
}
