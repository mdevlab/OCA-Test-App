package io.mdevlab.ocatraining.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by husaynhakeem on 5/23/17.
 */

public class UtilConnection {

    private static UtilConnection instance;
    private ConnectivityManager connectivityManager;


    public static UtilConnection with(Context context) {
        if (instance == null) {
            instance = new UtilConnection(context);
        }
        return instance;
    }


    private UtilConnection(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }


    public boolean internetIsAvailable() {
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
