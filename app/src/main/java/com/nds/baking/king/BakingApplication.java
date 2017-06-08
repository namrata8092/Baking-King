package com.nds.baking.king;

import android.app.Application;

import com.nds.baking.king.net.requests.NetworkRequestManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by Namrata Shah on 5/9/2017.
 */

public class BakingApplication extends Application {

    private NetworkRequestManager networkRequestManager;

    @Override
    public void onCreate() {
        super.onCreate();
        networkRequestManager = NetworkRequestManager.getInstance(BakingApplication.this);
        networkRequestManager.initializeNetworkManager();
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    public synchronized NetworkRequestManager getNetworkRequestManager() {
        return networkRequestManager;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (networkRequestManager != null)
            networkRequestManager.terminateNetworkManager();
    }
}
