package com.nds.baking.king.views.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nds.baking.king.R;
import com.nds.baking.king.models.RecipeIngredientModel;

import java.util.List;

/**
 * Created by Namrata Shah on 5/21/2017.
 */

public class RecipeIngredientAdapter extends ArrayAdapter {
    private List<RecipeIngredientModel> mRecipeIngredientModelList;

    public RecipeIngredientAdapter(@NonNull Context context, int resource, @NonNull List<RecipeIngredientModel> ingredientList) {
        super(context, resource, ingredientList);
        this.mRecipeIngredientModelList = ingredientList;
    }

    @Override
    public int getCount() {
        if (mRecipeIngredientModelList != null)
            return mRecipeIngredientModelList.size();
        return 0;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return mRecipeIngredientModelList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.recipe_ingredients_row, parent, false);
        }
        RecipeIngredientModel ingredientModel = mRecipeIngredientModelList.get(position);
        if(ingredientModel != null){
            TextView ingredientNameTV = (TextView) convertView.findViewById(R.id.ingredient_name);
            TextView ingredientQuantityTV = (TextView) convertView.findViewById(R.id.ingredient_quantity);
            TextView ingredientUnitTV = (TextView) convertView.findViewById(R.id.ingredient_unit);

            ingredientNameTV.setText(ingredientModel.getIngredientName());
            ingredientQuantityTV.setText(ingredientModel.getIngredientQuantity());
            ingredientUnitTV.setText(ingredientModel.getIngredientMeasure());
        }
        return convertView;
    }
}
