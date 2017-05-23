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

public class RecipeStepSlideFragment extends Fragment {
    private RecipeStepModel recipeStepModel;
    private static final String BUNDLE_SELECTED_STEP = "selectedStep";
    private FragmentManager mFragmetnManager;

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
        if (getArguments() != null) {
            recipeStepModel = getArguments().getParcelable(BUNDLE_SELECTED_STEP);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_detail_slide_fragment, container, false);
        mFragmetnManager = getActivity().getSupportFragmentManager();

        RecipeVideoFragment videoFragment = RecipeVideoFragment.newInstance(recipeStepModel.getVideoURL());
        mFragmetnManager.beginTransaction().add(R.id.recipe_video, videoFragment).commit();

        RecipeStepsDetailFragment detailFragment = RecipeStepsDetailFragment.newInstance(recipeStepModel.getDescription());
        mFragmetnManager.beginTransaction().add(R.id.recipe_steps_detail, detailFragment).commit();
        return view;
    }
}

