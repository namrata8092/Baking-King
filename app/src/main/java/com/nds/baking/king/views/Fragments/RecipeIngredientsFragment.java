package com.nds.baking.king.views.Fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nds.baking.king.R;
import com.nds.baking.king.models.RecipeIngredientModel;
import com.nds.baking.king.models.RecipeModel;
import com.nds.baking.king.views.adapter.RecipeIngredientAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Namrata Shah on 5/8/2017.
 */

public class RecipeIngredientsFragment extends Fragment {
    private static String BUNDLE_RECIPE_INGREDIENT = "ingredientBundle";
    private List<RecipeIngredientModel> mRecipeIngredientModelList;
    private RecipeIngredientAdapter ingredientAdapter;

    public static RecipeIngredientsFragment newInstance(RecipeModel mSelectedRecipe) {
        RecipeIngredientsFragment fragment = new RecipeIngredientsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BUNDLE_RECIPE_INGREDIENT, (ArrayList<? extends Parcelable>) mSelectedRecipe.getRecipeIngredientModelList());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_ingredient_fragment, container, false);
        LinearLayout ingredientListContainer = (LinearLayout) view.findViewById(R.id.ingredientsList);
        ingredientAdapter = new RecipeIngredientAdapter(getContext(), R.layout.recipe_ingredients_row,
                mRecipeIngredientModelList);
        setRecipeIngredients(ingredientListContainer, ingredientAdapter, mRecipeIngredientModelList);
        return view;
    }

    private void setRecipeIngredients(LinearLayout listContainer, RecipeIngredientAdapter ingredientAdapter,
                                      List<RecipeIngredientModel> mRecipeIngredientModelList) {
        if (mRecipeIngredientModelList != null && mRecipeIngredientModelList.size() > 0) {
            for (int index = 0; index < mRecipeIngredientModelList.size(); index++) {
                View v = ingredientAdapter.getView(index, null, null);
                listContainer.addView(v, new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mRecipeIngredientModelList = getArguments().getParcelableArrayList(BUNDLE_RECIPE_INGREDIENT);
    }
}
