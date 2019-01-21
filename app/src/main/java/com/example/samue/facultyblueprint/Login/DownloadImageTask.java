package com.example.samue.facultyblueprint.Login;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by michael on 20.01.19.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    Bitmap bitmap;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
        this.bitmap = null;
    }
    public DownloadImageTask(Bitmap bitmap){
        this.bitmap = bitmap;
        this.bmImage = null;
    }


    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
            Log.i("sTREEAM ",""+mIcon11.toString());
        } catch (Exception e) {
            Log.e("Error LOADING PIC >>>", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        if(null != bmImage)
            bmImage.setImageBitmap(result);
        if(null != bitmap)
            bitmap = result;
    }
}