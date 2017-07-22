package com.nds.baking.king.providers;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.nds.baking.king.R;
import com.nds.baking.king.models.RecipeModel;
import com.nds.baking.king.models.RecipeResponseModel;
import com.nds.baking.king.services.BakingRemoteViewService;
import com.nds.baking.king.views.Activities.RecipeIngredientStepsActivity;
import com.orhanobut.logger.Logger;

import static com.nds.baking.king.services.BakingWidgetService.startBakingWidgetService;

/**
 * Created by Namrata Shah on 5/26/2017.
 */

public class BakingWidgetProvider extends AppWidgetProvider {

    private static final String TAG=BakingWidgetProvider.class.getSimpleName();
    private static RecipeResponseModel recipeResponseModel;
    private static final String RECIPE_BUNDLE="recipeBundle";
    private static final String RECIPE_INDEX_KEY="index";
    private static int RECIPE_INDEX = 0;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Logger.d(TAG,"onUpdate called "+RECIPE_INDEX);
        startBakingWidgetService(context);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void updateWidget(Context context, AppWidgetManager appWidgetManager,
                                    int appWidgetIds, RecipeResponseModel responseModel) {
        Logger.d(TAG,"updateWidget called "+RECIPE_INDEX);
        recipeResponseModel = responseModel;
        RemoteViews views = getRecipeGridView(context, appWidgetIds, recipeResponseModel);
        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    private static RemoteViews getRecipeGridView(Context context, int appWidgetIds,
                                                 RecipeResponseModel recipeResponseModel) {
        Logger.d(TAG,"getRecipeGridView");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        if(RECIPE_INDEX < 4 && RECIPE_INDEX >= 0){
            Logger.d(TAG,"condition 1");
            RECIPE_INDEX++;
        }else if(RECIPE_INDEX <= 1){
            Logger.d(TAG,"condition 2");
            RECIPE_INDEX--;
        }
        RecipeModel recipeModel = recipeResponseModel.getRecipes().get(RECIPE_INDEX);
        views.setTextViewText(R.id.recipeName,recipeModel.getRecipeName());
        views.setTextViewText(R.id.recipeServing, "servings : "+recipeModel.getServings());
        views.setImageViewResource(R.id.recipeIcon, R.drawable.ic_launcher);

        Intent intent = new Intent(context, BakingRemoteViewService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds);
        intent.putExtra(RECIPE_INDEX_KEY,RECIPE_INDEX);
        intent.putExtra(RECIPE_BUNDLE, (new Gson().toJson(recipeResponseModel)).toString());
        views.setRemoteAdapter( R.id.recipe_ingredients_list, intent);

        Intent appIntent = new Intent(context, RecipeIngredientStepsActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.recipe_ingredients_list, appPendingIntent);

        return views;
    }



    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
        startBakingWidgetService(context);
        Logger.d(TAG,"onAppWidgetOptionsChanged called");
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.d(TAG,"onReceive called "+RECIPE_INDEX);
        super.onReceive(context, intent);
    }
}
