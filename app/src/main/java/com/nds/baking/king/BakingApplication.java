package com.nds.baking.king;

import android.app.Application;

import com.nds.baking.king.net.requests.NetworkRequestManager;

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
    }

    public synchronized NetworkRequestManager getNetworkRequestManager(){
        return networkRequestManager;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if(networkRequestManager !=  null)
            networkRequestManager.terminateNetworkManager();
    }
}
