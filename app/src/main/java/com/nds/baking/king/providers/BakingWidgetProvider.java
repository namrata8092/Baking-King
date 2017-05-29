package com.nds.baking.king.providers;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.nds.baking.king.R;
import com.nds.baking.king.models.RecipeModel;
import com.nds.baking.king.models.RecipeResponseModel;
import com.nds.baking.king.services.BakingRemoteViewService;
import com.nds.baking.king.services.BakingWidgetService;
import com.nds.baking.king.views.Activities.BakingLauncherActivity;
import com.nds.baking.king.views.Activities.RecipeIngredientStepsActivity;

/**
 * Created by Namrata Shah on 5/26/2017.
 */

public class BakingWidgetProvider extends AppWidgetProvider {

    private static final String TAG="Test";
    private static RecipeResponseModel recipeResponseModel;
    private static final String SELECTED_RECIPE_BUNDLE_KEY = "selectedRecipe";
    private static final String RECIPE_BUNDLE="recipeBundle";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d(TAG,"onUpdate called");
        BakingWidgetService.startBakingWidgetService(context);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetIds, RecipeResponseModel responseModel) {
        recipeResponseModel = responseModel;
        RemoteViews views;
        Bundle widgetDetails = appWidgetManager.getAppWidgetOptions(appWidgetIds);
        int widgetWidth = widgetDetails.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        if(widgetWidth < 300){
            views = getSingleRecipeView(context, 0);
        }else{
            views = getRecipeGridView(context, appWidgetIds, recipeResponseModel);
        }
        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    private static RemoteViews getRecipeGridView(Context context, int appWidgetIds, RecipeResponseModel recipeResponseModel) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_layout);

        Intent intent = new Intent(context, BakingRemoteViewService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds);
        intent.putExtra(RECIPE_BUNDLE, recipeResponseModel);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.recipe_grid_view, intent);

        Intent appIntent = new Intent(context, RecipeIngredientStepsActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.recipe_grid_view, appPendingIntent);
        // Handle empty gardens
        views.setEmptyView(R.id.recipe_grid_view, R.id.empty_view);

        return views;
    }

    private static RemoteViews getSingleRecipeView(Context context, int recipeID) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_initial_layout);
        Intent intent=null;
        if(recipeID < 0){
            intent = new Intent(context, BakingLauncherActivity.class);
        }else{
            intent = new Intent(context, RecipeIngredientStepsActivity.class);
            RecipeModel selectedRecipe = recipeResponseModel.getRecipes().get(recipeID);
            Bundle bundle = new Bundle();
            bundle.putParcelable(SELECTED_RECIPE_BUNDLE_KEY, selectedRecipe);
            intent.putExtra(RECIPE_BUNDLE, bundle);
            views.setImageViewResource(R.id.recipeIcon, R.drawable.ic_launcher);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.recipeIcon, pendingIntent);
        return views;
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        BakingWidgetService.startBakingWidgetService(context);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        Log.d(TAG,"onAppWidgetOptionsChanged called");
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d(TAG,"onDeleted called");
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(TAG,"onEnabled called");
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d(TAG,"onDisabled called");
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
        Log.d(TAG,"onRestored called");
    }

//    @Override
//    public void onReceive(Context context, Intent intent) {
//        try {
//            Bundle extras = intent.getExtras();
//            recipeResponseModel = extras.getParcelable(RECIPE_BUNDLE);
//
//            if (recipeResponseModel != null) {
//
//                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//                ComponentName thisAppWidget = new ComponentName(context.getPackageName(), BakingWidgetProvider.class.getName());
//                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
//
//                onUpdate(context, appWidgetManager, appWidgetIds);
//            }
//            super.onReceive(context, intent);
//
//        } catch (Exception e) {
//
//        }
//    }
}
