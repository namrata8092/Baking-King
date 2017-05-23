package com.nds.baking.king.views.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.nds.baking.king.R;
import com.nds.baking.king.models.RecipeIngredientModel;
import com.nds.baking.king.models.RecipeModel;
import com.nds.baking.king.models.RecipeStepModel;
import com.nds.baking.king.views.Fragments.RecipeIngredientsFragment;
import com.nds.baking.king.views.Fragments.RecipeStepsDescriptionFragment;

import java.util.List;

/**
 * Created by Namrata Shah on 5/8/2017.
 */

public class RecipeIngredientStepsActivity extends AppCompatActivity {
    private static final String SELECTED_RECIPE_BUNDLE_KEY = "selectedRecipe";
    private static final String RECIPE_BUNDLE="recipeBundle";
    private RecipeModel mSelectedRecipe;
    private List<RecipeStepModel> recipeStepModelList;
    private List<RecipeIngredientModel> recipeIngredientModelList;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredients_steps);
        mFragmentManager = getSupportFragmentManager();
        if(savedInstanceState!=null){
            return;
        }else if(getIntent().getBundleExtra(RECIPE_BUNDLE)!=null){
            Bundle bundle = getIntent().getBundleExtra(RECIPE_BUNDLE);
            mSelectedRecipe = (RecipeModel)bundle.get(SELECTED_RECIPE_BUNDLE_KEY);

            recipeIngredientModelList = mSelectedRecipe.getRecipeIngredientModelList();
            RecipeIngredientsFragment ingredientFragment = RecipeIngredientsFragment.newInstance(recipeIngredientModelList);
            mFragmentManager.beginTransaction().add(R.id.recipe_ingredients, ingredientFragment).commit();

            recipeStepModelList = mSelectedRecipe.getRecipeStepModelList();
            RecipeStepsDescriptionFragment stepsDescriptionFragment = RecipeStepsDescriptionFragment.newInstance(recipeStepModelList);
            mFragmentManager.beginTransaction().add(R.id.recipe_steps_description, stepsDescriptionFragment).commit();
        }

    }
}
