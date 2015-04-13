package com.mobidosoft.storeapp.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by RP on 4/12/2015.
 */
public class ImageUtil {

    private static final int IO_BUFFER_SIZE =  4*1024;

    public static  Bitmap loadFromUrl(String url) {

        Bitmap bitmap = null;
        InputStream in = null;
        URL link = null;

        try {
            link = new URL(url);
        } catch (Exception e) {
            return  null;
        }

            try {
                in = link.openConnection().getInputStream();
                bitmap = BitmapFactory.decodeStream(in, null, null);
                in.close();
            } catch (IOException e) {
            }


        return bitmap;
    }


}
