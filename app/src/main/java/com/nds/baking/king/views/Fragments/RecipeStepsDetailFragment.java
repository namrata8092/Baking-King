package com.nds.baking.king.views.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nds.baking.king.R;

/**
 * Created by Namrata Shah on 5/8/2017.
 */

public class RecipeStepsDetailFragment extends Fragment {
    private String mStepDetail;
    private static final String KEY_STEP_DETAIL = "stepDetail";

    public static RecipeStepsDetailFragment newInstance(String detail) {
        RecipeStepsDetailFragment fragment = new RecipeStepsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_STEP_DETAIL, detail);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_steps_row, container, false);
        TextView stepsDescriptionTV = (TextView)view.findViewById(R.id.recipeStep);
        stepsDescriptionTV.setText(mStepDetail);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null && savedInstanceState.containsKey(KEY_STEP_DETAIL))
            mStepDetail = savedInstanceState.getString(KEY_STEP_DETAIL);
        else if (getArguments() != null) {
            mStepDetail = getArguments().getString(KEY_STEP_DETAIL);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_STEP_DETAIL, mStepDetail);
        super.onSaveInstanceState(outState);
    }
}
