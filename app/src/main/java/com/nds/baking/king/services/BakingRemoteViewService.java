package com.nds.baking.king.services;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.nds.baking.king.widget.BakingWidgetRemoteViewsFactory;
import com.orhanobut.logger.Logger;

/**
 * Created by Namrata Shah on 5/26/2017.
 */

public class BakingRemoteViewService extends RemoteViewsService  {
    private static final String TAG = BakingRemoteViewService.class.getSimpleName();
    @Override
        public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Logger.d("BakingRemoteViewService get remote view");
        return new BakingWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
