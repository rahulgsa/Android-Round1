package com.ttnd.androidround1.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by ttnd on 19/1/16.
 */
public class NetworkConnection {

    public static boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
