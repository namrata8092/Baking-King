package com.nds.baking.king.services;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.nds.baking.king.BakingApplication;
import com.nds.baking.king.BuildConfig;
import com.nds.baking.king.R;
import com.nds.baking.king.converters.RecipeConverter;
import com.nds.baking.king.models.RecipeResponseModel;
import com.nds.baking.king.net.requests.NetworkRequestManager;
import com.nds.baking.king.net.requests.NetworkRequester;
import com.nds.baking.king.providers.BakingWidgetProvider;
import com.nds.baking.king.utils.JsonSerializationHelper;
import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;

/**
 * Created by Namrata Shah on 5/26/2017.
 */

public class BakingWidgetService extends IntentService {
    private static final String TAG = BakingWidgetService.class.getSimpleName();
    private Context mContext;
    private static final String ACTION_UPDATE_WIDGET = "com.nds.baking.king.UPDATE_BAKING_RECIPE";

    public static void startBakingWidgetService(Context context) {
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
        if (intent != null && ACTION_UPDATE_WIDGET.equals(intent.getAction())) {
            mContext = getApplicationContext();
            try {
                BakingApplication mBakingApplication = (BakingApplication) mContext;
                if (mBakingApplication != null) {
                    NetworkRequestManager mNetworkRequestManager = mBakingApplication.getNetworkRequestManager();
                    if(BuildConfig.DEBUG){
                        int resourceID = getApplicationContext().getResources().getIdentifier(
                                "sample_repsonse", "raw", getPackageName());
                        String responseFromAssets = JsonSerializationHelper.readFakeResponseFromRaw( getApplicationContext(),resourceID);
                        RecipeResponseModel responseModel = RecipeConverter.convert(responseFromAssets);
                        showWidgetData(responseModel, mContext);
                    }else{
                        mNetworkRequestManager.getRecipeList(new WeakReference<NetworkRequester>(new NetworkRequester() {
                                    @Override
                                    public void onFailure(Throwable error) {
                                        Logger.d( "onFailure ++");
                                    }

                                    @Override
                                    public void onSuccess(Object respObj) {
                                        Logger.d( "onSuccess ++");
                                        RecipeResponseModel responseModel = (RecipeResponseModel) respObj;
                                        showWidgetData(responseModel, mContext);
                                    }
                                }), NetworkRequestManager.REQUEST_URL,
                                getResources().getString(R.string.recipe_request_tag));
                    }


                }
            } catch (Exception e) {
                Logger.e(TAG, "Exception ++ " + e.toString());
            }


        }
    }

    private void showWidgetData(RecipeResponseModel responseModel, Context mContext) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(mContext, BakingWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.recipe_grid_view);
        for (int widgetId : appWidgetIds) {
            Logger.d( " id " + widgetId);
            BakingWidgetProvider.updateWidget(mContext, appWidgetManager, widgetId, responseModel);
        }
    }
}
