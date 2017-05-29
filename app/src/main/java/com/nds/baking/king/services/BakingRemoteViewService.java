package com.nds.baking.king.services;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.nds.baking.king.widget.BakingWidgetRemoteViewsFactory;

/**
 * Created by Namrata Shah on 5/26/2017.
 */

public class BakingRemoteViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
