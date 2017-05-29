package com.nds.baking.king.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.nds.baking.king.R;
import com.nds.baking.king.models.RecipeModel;
import com.nds.baking.king.models.RecipeResponseModel;

import java.util.List;

/**
 * Created by Namrata Shah on 5/26/2017.
 */

public class BakingWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private RecipeResponseModel responseModel;
    private List<RecipeModel> recipeModelList;
    private Context mContext;
    private static final String RECIPE_BUNDLE="recipeBundle";

    public BakingWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        this.mContext = applicationContext;
        this.responseModel = intent.getParcelableExtra(RECIPE_BUNDLE);
        this.recipeModelList = responseModel.getRecipes();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(recipeModelList!=null)
            return recipeModelList.size();
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_grid_cell);
        views.setTextViewText(R.id.recipeName, "test1");
        views.setTextViewText(R.id.recipeServing, "test2");

//        views.setTextViewText(R.id.recipeName, recipeModelList.get(position).getRecipeName());
//        views.setTextViewText(R.id.recipeServing, recipeModelList.get(position).getRecipeName());
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_loading_layout);
        return views;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
