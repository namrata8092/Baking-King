package com.nds.baking.king.services;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nds.baking.king.BakingApplication;
import com.nds.baking.king.R;
import com.nds.baking.king.models.RecipeResponseModel;
import com.nds.baking.king.net.requests.NetworkRequestManager;
import com.nds.baking.king.net.requests.NetworkRequester;
import com.nds.baking.king.providers.BakingWidgetProvider;
import com.nds.baking.king.views.Activities.BakingLauncherActivity;

import java.lang.ref.WeakReference;

/**
 * Created by Namrata Shah on 5/26/2017.
 */

public class BakingWidgetService extends IntentService {

    private Context mContext;
    private static final String ACTION_UPDATE_WIDGET="com.nds.baking.king.UPDATE_BAKING_RECIPE";

    public static void startBakingWidgetService(Context context){
        Intent intent = new Intent(context, BakingWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);

    }
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BakingWidgetService() {
        super("BakingWidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null && ACTION_UPDATE_WIDGET.equals(intent.getAction())){
            mContext = getApplicationContext();
            try{
                BakingApplication mBakingApplication = (BakingApplication) mContext;
                if(mBakingApplication!=null){
                    NetworkRequestManager mNetworkRequestManager = mBakingApplication.getNetworkRequestManager();
                    mNetworkRequestManager.getRecipeList(new WeakReference<NetworkRequester>(new NetworkRequester() {
                                @Override
                                public void onFailure(Throwable error) {
                                    Log.d("Test","onFailure ++");
                                }

                                @Override
                                public void onSuccess(Object respObj) {
                                    Log.d("Test","onSuccess ++");
                                    RecipeResponseModel responseModel = (RecipeResponseModel) respObj;
                                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
                                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(mContext, BakingWidgetProvider.class));
                                    //Trigger data update to handle the GridView widgets and force a data refresh
                                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.recipe_grid_view);
                                    //Now update all widgets
                                    for(int widgetId : appWidgetIds){
                                        BakingWidgetProvider.updateWidget(mContext, appWidgetManager, widgetId,responseModel );
                                    }
                                }
                            }), NetworkRequestManager.REQUEST_URL,
                            getResources().getString(R.string.recipe_request_tag));
                }
            }catch (Exception e){
                Log.d("Test","Exception ++ "+e.toString());
            }


        }
    }
}
