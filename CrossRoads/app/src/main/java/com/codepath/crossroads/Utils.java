package com.codepath.crossroads;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.codepath.crossroads.models.ParseItem;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by ar on 10/18/14.
 */
public class Utils {

    public static Bitmap byteArrToBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    public static byte[] bitmapToByteArr(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static Bitmap getStoredImage(String path) {
        Log.i("", "Path: " + path);
        int targetW = 200;
        int targetH = 200;

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        return bitmap;
    }

    public static void loadAnyItemPicLocal(final List<ParseItem> items, final int idx, final ImageView iv) {
        if (idx == items.size()) {
            loadAnyItemPicRemote(items, 0, iv);
            return;
        }

        ParseItem item = items.get(idx);
        item.fetchFromLocalDatastoreInBackground(new GetCallback<ParseItem>() {
            @Override
            public void done(ParseItem parseObject, ParseException e) {
                if (e != null) {
                    loadAnyItemPicRemote(items, idx + 1, iv);
                    return;
                }

                ParseFile f = parseObject.getPhoto();
                if (f == null) {
                    loadAnyItemPicRemote(items, idx + 1, iv);
                    return;
                }

                try {
                    iv.setImageBitmap(Utils.byteArrToBitmap(f.getData()));
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void loadAnyItemPicRemote(final List<ParseItem> items, final int idx, final ImageView iv) {
        if (idx == items.size()) {
            return;
        }

        ParseItem item = items.get(idx);
        item.fetchInBackground(new GetCallback<ParseItem>() {
            @Override
            public void done(ParseItem parseObject, ParseException e) {
                if (e != null) {
                    loadAnyItemPicRemote(items, idx + 1, iv);
                    return;
                }

                ParseFile f = parseObject.getPhoto();
                if (f == null) {
                    loadAnyItemPicRemote(items, idx + 1, iv);
                    return;
                }

                try {
                    iv.setImageBitmap(Utils.byteArrToBitmap(f.getData()));
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
