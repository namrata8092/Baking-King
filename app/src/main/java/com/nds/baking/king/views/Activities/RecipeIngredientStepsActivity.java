package com.nds.baking.king.views.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.nds.baking.king.Constants;
import com.nds.baking.king.R;
import com.nds.baking.king.models.RecipeModel;
import com.nds.baking.king.views.Fragments.RecipeIngredientsFragment;
import com.nds.baking.king.views.Fragments.RecipeStepSlideFragment;
import com.nds.baking.king.views.Fragments.RecipeStepsDescriptionFragment;
import com.orhanobut.logger.Logger;

/**
 * Created by Namrata Shah on 5/8/2017.
 */

public class RecipeIngredientStepsActivity extends AppCompatActivity {
    private static final String TAG = "Test"+RecipeIngredientStepsActivity.class.getSimpleName();
    private static final String SELECTED_RECIPE_BUNDLE_KEY = "selectedRecipe";
    private static final String RECIPE_BUNDLE="recipeBundle";
    private RecipeModel mSelectedRecipe;
    private FragmentManager mFragmentManager;
    private boolean mTwoPanel = false;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredients_steps);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mEditor = mSharedPreferences.edit();
        if(findViewById(R.id.recipe_video)!=null){
            mTwoPanel = true;
        }
        mEditor.putBoolean(Constants.TWO_PANEL_KEY, mTwoPanel).commit();
        mFragmentManager = getSupportFragmentManager();
        if(savedInstanceState!=null && savedInstanceState.containsKey(SELECTED_RECIPE_BUNDLE_KEY)){
            mSelectedRecipe = savedInstanceState.getParcelable(SELECTED_RECIPE_BUNDLE_KEY);
        }else if(getIntent().getBundleExtra(RECIPE_BUNDLE)!=null){
            Bundle bundle = getIntent().getBundleExtra(RECIPE_BUNDLE);
            mSelectedRecipe = (RecipeModel)bundle.get(SELECTED_RECIPE_BUNDLE_KEY);
        }
        if(mSelectedRecipe!=null){
            setIngredientsAndStepDescription(mSelectedRecipe, mFragmentManager);
            setTitle(mSelectedRecipe.getRecipeName());
        }
    }

    private void setIngredientsAndStepDescription(RecipeModel mSelectedRecipe, FragmentManager mFragmentManager) {
        RecipeIngredientsFragment ingredientFragment = RecipeIngredientsFragment.newInstance(mSelectedRecipe);
        mFragmentManager.beginTransaction().add(R.id.recipe_ingredients, ingredientFragment).commit();
        RecipeStepsDescriptionFragment stepsDescriptionFragment = RecipeStepsDescriptionFragment.newInstance(mSelectedRecipe);
        mFragmentManager.beginTransaction().add(R.id.recipe_steps_description, stepsDescriptionFragment).commit();
        if(mTwoPanel){
            Logger.d(TAG,"it is two panel so add video");
            RecipeStepSlideFragment stepSlideFragment = RecipeStepSlideFragment.newInstance(mSelectedRecipe.getRecipeStepModelList().get(0));
            mFragmentManager.beginTransaction().add(R.id.recipe_video, stepSlideFragment).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SELECTED_RECIPE_BUNDLE_KEY, mSelectedRecipe);
        super.onSaveInstanceState(outState);
    }
}
