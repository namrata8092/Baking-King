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
import com.nds.baking.king.views.Activities.BakingLauncherActivity;
import com.nds.baking.king.views.Activities.RecipeIngredientStepsActivity;
import com.orhanobut.logger.Logger;

import static com.nds.baking.king.services.BakingWidgetService.startBakingWidgetService;

/**
 * Created by Namrata Shah on 5/26/2017.
 */

public class BakingWidgetProvider extends AppWidgetProvider {

    private static final String TAG=BakingWidgetProvider.class.getSimpleName();
    private static RecipeResponseModel recipeResponseModel;
    private static final String SELECTED_RECIPE_BUNDLE_KEY = "selectedRecipe";
    private static final String RECIPE_BUNDLE="recipeBundle";
    private static final int WIDGET_MIN_WIDTH = 300;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Logger.d(TAG,"onUpdate called");
        startBakingWidgetService(context);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void updateWidget(Context context, AppWidgetManager appWidgetManager,
                                    int appWidgetIds, RecipeResponseModel responseModel) {
        recipeResponseModel = responseModel;
        RemoteViews views;
        Bundle widgetDetails = appWidgetManager.getAppWidgetOptions(appWidgetIds);
        int widgetWidth = widgetDetails.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        Logger.d(TAG,"width is "+widgetWidth+" appWidgetIds "+appWidgetIds);
//        if(widgetWidth < WIDGET_MIN_WIDTH){
//            views = getSingleRecipeView(context, 0, recipeResponseModel);
//        }else{
//            views = getRecipeGridView(context, appWidgetIds, recipeResponseModel);
//        }
        views = getRecipeGridView(context, appWidgetIds, recipeResponseModel);
        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    private static RemoteViews getRecipeGridView(Context context, int appWidgetIds,
                                                 RecipeResponseModel recipeResponseModel) {
        Logger.d(TAG,"getRecipeGridView");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.recipeName,recipeResponseModel.getRecipes().get(0).getRecipeName());
        views.setTextViewText(R.id.recipeServing, "servings : "+recipeResponseModel.getRecipes().get(0).getServings());
        views.setImageViewResource(R.id.recipeIcon, R.drawable.ic_launcher);

        Intent intent = new Intent(context, BakingRemoteViewService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds);
        intent.putExtra(RECIPE_BUNDLE, (new Gson().toJson(recipeResponseModel)).toString());
        views.setRemoteAdapter( R.id.recipe_ingredients_list, intent);

        Intent appIntent = new Intent(context, RecipeIngredientStepsActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.recipe_ingredients_list, appPendingIntent);

//        views.setEmptyView(R.id.recipe_grid_view, R.id.empty_view);

        return views;
    }

    private static RemoteViews getSingleRecipeView(Context context, int recipeID,
                                                   RecipeResponseModel responseModel) {
        Logger.d(TAG,"getSingleRecipeView");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_initial_layout);
        Intent intent=null;
        if(recipeID < 0){
            intent = new Intent(context, BakingLauncherActivity.class);
        }else{
            intent = new Intent(context, RecipeIngredientStepsActivity.class);
            RecipeModel selectedRecipe = responseModel.getRecipes().get(recipeID);
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
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
        startBakingWidgetService(context);
        Logger.d(TAG,"onAppWidgetOptionsChanged called");
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }
}
