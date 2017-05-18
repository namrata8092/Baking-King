package com.nds.baking.king.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Namrata Shah on 5/10/2017.
 */

public final class NetworkUtil {
    private NetworkUtil(){};

    /**
     * Check whether device has valid internet connection or not.
     * @param mContext
     * @return true if internet connection is available else returns false.
     */
    public static boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return isWifiConnected(networkInfo) || isMobileConnected(networkInfo);
    }

    /**
     * Check whether device has valid wifi connectivity on device.
     * @param networkInfo
     * @return true if wifi connection is available else returns false.
     */
    public static boolean isWifiConnected(NetworkInfo networkInfo) {
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI
                && (networkInfo.isConnected() || networkInfo.isConnectedOrConnecting())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check whether device has valid mobile data connectivity on device.
     * @param networkInfo
     * @return true if mobile data is available else returns false.
     */
    public static boolean isMobileConnected(NetworkInfo networkInfo) {
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE
                && (networkInfo.isConnected() || networkInfo.isConnectedOrConnecting())) {
            return true;
        } else {
            return false;
        }
    }

}
