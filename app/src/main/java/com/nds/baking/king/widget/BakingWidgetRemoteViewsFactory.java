package com.nds.baking.king.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nds.baking.king.R;
import com.nds.baking.king.models.RecipeIngredientModel;
import com.nds.baking.king.models.RecipeModel;
import com.nds.baking.king.models.RecipeResponseModel;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Namrata Shah on 5/26/2017.
 */

public class BakingWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = "TEST"+BakingWidgetRemoteViewsFactory.class.getSimpleName();
    private RecipeResponseModel responseModel;
    private List<RecipeModel> recipeModelList;
    private List<RecipeIngredientModel> ingredientModelList;
    private Context mContext;
    private static final String RECIPE_BUNDLE="recipeBundle";
    private static final String SELECTED_RECIPE_BUNDLE_KEY = "selectedRecipe";
    private static final String RECIPE_INDEX_KEY="index";
    private int INDEX = 0;

    public BakingWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        Logger.d( TAG,"BakingWidgetRemoteViewsFactory constrctor");
        this.mContext = applicationContext;
        String recipes = intent.getStringExtra(RECIPE_BUNDLE);
        INDEX = intent.getIntExtra(RECIPE_INDEX_KEY,0);
        Logger.d(TAG,"received recipes "+recipes);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        this.responseModel = gson.fromJson(recipes, RecipeResponseModel.class);
        this.recipeModelList = responseModel.getRecipes();
        this.ingredientModelList = recipeModelList.get(INDEX).getRecipeIngredientModelList();
    }

    @Override
    public void onCreate() {
        Logger.d( TAG,"BakingWidgetRemoteViewsFactory onCreate");

    }

    @Override
    public void onDataSetChanged() {
        Logger.d( TAG,"BakingWidgetRemoteViewsFactory onDataSetChanged");
    }


    @Override
    public void onDestroy() {
        Logger.d( TAG,"BakingWidgetRemoteViewsFactory onDestroy");
    }

    @Override
    public int getCount() {
        Logger.d( TAG,"BakingWidgetRemoteViewsFactory getCount");
        if(ingredientModelList!=null)
            return ingredientModelList.size();
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Logger.d( TAG,"BakingWidgetRemoteViewsFactory getViewAt "+INDEX);
        RecipeModel selectedRecipe = responseModel.getRecipes().get(INDEX);
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_row);
        RecipeIngredientModel recipeIngredientModel = selectedRecipe.getRecipeIngredientModelList().get(position);

        views.setTextViewText(R.id.ingredient_name,recipeIngredientModel.getIngredientName());
        views.setTextViewText(R.id.ingredient_quantity,recipeIngredientModel.getIngredientQuantity());
        views.setTextViewText(R.id.ingredient_unit,recipeIngredientModel.getIngredientMeasure());

        Bundle bundle = new Bundle();
        bundle.putParcelable(SELECTED_RECIPE_BUNDLE_KEY, selectedRecipe);
        Intent recipeDetailIntent = new Intent();
        recipeDetailIntent.putExtra(RECIPE_BUNDLE, bundle);
        views.setOnClickFillInIntent(R.id.widget_list_row_parent,recipeDetailIntent);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        Logger.d( TAG,"BakingWidgetRemoteViewsFactory getLoadingView");
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_loading_layout);
        return views;
    }

    @Override
    public int getViewTypeCount() {
        Logger.d(TAG,"BakingWidgetRemoteViewsFactory getViewTypeCount");
        return 1;
    }

    @Override
    public long getItemId(int position) {
        Logger.d( TAG,"BakingWidgetRemoteViewsFactory getItemId");
        return position;
    }

    @Override
    public boolean hasStableIds() {
        Logger.d( TAG,"BakingWidgetRemoteViewsFactory hasStableIds");
        return true;
    }
}
