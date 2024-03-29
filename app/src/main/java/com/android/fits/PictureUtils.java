package com.android.fits;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
/*
This code is copied from the Big Nerd Ranch 3rd edition textbook.
 */
public class PictureUtils {
    /**
     * Scales down the image file to a reasonable size for a bitmap.
     * @param path
     * @param destWidth
     * @param destHeight
     * @return
     */
    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight){
        /*
        The key parameter above is
        inSampleSize. This determines how
        big each “sample” should be for each
        pixel – a sample size of 1 has one final
        horizontal pixel for each horizontal pixel
        in the original file, and a sample size of
        2 has one horizontal pixel for every two
        horizontal pixels in the original file. So
        when inSampleSize is 2, the image
        has a quarter of the number of pixels of
        the original.

         */
        //Read in the dimensions of the image on disk.
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        // Figure out how much to scale down by
        int inSampleSize = 1;
        if (srcHeight > destHeight || srcWidth > destWidth){
            float heightScale = srcHeight/ destHeight;
            float widthScale = srcWidth / destWidth;

            inSampleSize = Math.round(heightScale > widthScale ? heightScale : widthScale);
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        // Read in and create final bitmap
        return BitmapFactory.decodeFile(path, options);

    }

    /**
     * This method checks to see how big the screen is and then scales the image
     * down to that size.
     * @param path
     * @param activity
     * @return
     */
    public static Bitmap getScaledBitmap(String path, Activity activity){
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);

        return getScaledBitmap(path, size.x, size.y);
    }
}
