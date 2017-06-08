package com.nds.baking.king.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nds.baking.king.R;
import com.nds.baking.king.models.RecipeModel;
import com.nds.baking.king.models.RecipeResponseModel;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Namrata Shah on 5/26/2017.
 */

public class BakingWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = BakingWidgetRemoteViewsFactory.class.getSimpleName();
    private RecipeResponseModel responseModel;
    private List<RecipeModel> recipeModelList;
    private Context mContext;
    private static final String RECIPE_BUNDLE="recipeBundle";
    private static final String SELECTED_RECIPE_BUNDLE_KEY = "selectedRecipe";

    public BakingWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        Logger.d( "BakingWidgetRemoteViewsFactory constrctor");
        this.mContext = applicationContext;
        String recipes = intent.getStringExtra(RECIPE_BUNDLE);
        Logger.d("received recipes "+recipes);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        this.responseModel = gson.fromJson(recipes, RecipeResponseModel.class);
        this.recipeModelList = responseModel.getRecipes();
    }

    @Override
    public void onCreate() {
        Logger.d( "BakingWidgetRemoteViewsFactory onCreate");

    }

    @Override
    public void onDataSetChanged() {
        Logger.d( "BakingWidgetRemoteViewsFactory onDataSetChanged");
    }


    @Override
    public void onDestroy() {
        Logger.d( "BakingWidgetRemoteViewsFactory onDestroy");
    }

    @Override
    public int getCount() {
        Logger.d( "BakingWidgetRemoteViewsFactory getCount");
        if(recipeModelList!=null)
            return recipeModelList.size();
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Logger.d( "BakingWidgetRemoteViewsFactory getViewAt");
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_grid_cell);
        views.setTextViewText(R.id.recipeName, recipeModelList.get(position).getRecipeName());
        views.setTextViewText(R.id.recipeServing, recipeModelList.get(position).getServings());
        RecipeModel selectedRecipe = responseModel.getRecipes().get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(SELECTED_RECIPE_BUNDLE_KEY, selectedRecipe);
        Intent recipeDetailIntent = new Intent();
        recipeDetailIntent.putExtra(RECIPE_BUNDLE, bundle);
        views.setOnClickFillInIntent(R.id.recipeIcon,recipeDetailIntent);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        Logger.d( "BakingWidgetRemoteViewsFactory getLoadingView");
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_loading_layout);
        return views;
    }

    @Override
    public int getViewTypeCount() {
        Logger.d( "BakingWidgetRemoteViewsFactory getViewTypeCount");
        return 1;
    }

    @Override
    public long getItemId(int position) {
        Logger.d( "BakingWidgetRemoteViewsFactory getItemId");
        return position;
    }

    @Override
    public boolean hasStableIds() {
        Logger.d( "BakingWidgetRemoteViewsFactory hasStableIds");
        return true;
    }
}
