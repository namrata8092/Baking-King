package com.nds.baking.king.views.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nds.baking.king.R;
import com.nds.baking.king.models.RecipeStepModel;

/**
 * Created by Namrata Shah on 5/21/2017.
 */

public class RecipeStepSlideFragment extends Fragment{
    private static final String TAG = RecipeStepSlideFragment.class.getSimpleName();
    private RecipeStepModel recipeStepModel;
    private static final String BUNDLE_SELECTED_STEP = "selectedStep";
    private String mVideoUrl;
    private String mRecipeDescription;
    private FragmentManager mFragmentManager;

    public static RecipeStepSlideFragment newInstance(RecipeStepModel stepModel) {
        RecipeStepSlideFragment fragment = new RecipeStepSlideFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_SELECTED_STEP, stepModel);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getFragmentManager();
        if(savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_SELECTED_STEP))
            recipeStepModel = savedInstanceState.getParcelable(BUNDLE_SELECTED_STEP);
        if (getArguments() != null) {
            recipeStepModel = getArguments().getParcelable(BUNDLE_SELECTED_STEP);
        }

        mVideoUrl = recipeStepModel.getVideoURL();
        mRecipeDescription = recipeStepModel.getShortDescription();

        RecipeVideoFragment recipeVideoFragment = RecipeVideoFragment.newInstance(mVideoUrl);
        mFragmentManager.beginTransaction().replace(R.id.recipe_video, recipeVideoFragment).commit();
        RecipeStepsDetailFragment recipeStepSlideFragment = RecipeStepsDetailFragment.newInstance(mRecipeDescription);
        mFragmentManager.beginTransaction().replace(R.id.recipe_steps_detail, recipeStepSlideFragment).commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_detail_slide_fragment, container, false);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BUNDLE_SELECTED_STEP, recipeStepModel);
        super.onSaveInstanceState(outState);
    }

}

