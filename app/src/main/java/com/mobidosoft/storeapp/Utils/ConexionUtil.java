package com.mobidosoft.storeapp.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by RP on 3/30/2015.
 */
public class ConexionUtil {


    public static boolean isOnline(Context context ) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}
