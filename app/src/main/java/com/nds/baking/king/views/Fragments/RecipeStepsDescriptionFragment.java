package com.nds.baking.king.views.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.nds.baking.king.R;
import com.nds.baking.king.models.RecipeStepModel;
import com.nds.baking.king.views.Activities.RecipeDetailActivity;
import com.nds.baking.king.views.adapter.RecipeStepsDescriptionAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Namrata Shah on 5/8/2017.
 */

public class RecipeStepsDescriptionFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String BUNDLE_STEPS ="stepsDescription";
    private static final String SELECTED_STEP_INDEX_KEY="selectedStepModel";
    private static final String STEPS_BUNDLE_KEY="selectedStepSBundle";
    private List<RecipeStepModel> mRecipeStepModelList;
    private RecyclerView mRecipeStepsList;
    private RecipeStepsDescriptionAdapter mStepsDescriptionAdapter;

    public static RecipeStepsDescriptionFragment newInstance(List<RecipeStepModel> recipeStepModelList){
        RecipeStepsDescriptionFragment fragment = new RecipeStepsDescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BUNDLE_STEPS, (ArrayList<? extends Parcelable>) recipeStepModelList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_steps_fragment, container, false);
        mRecipeStepsList = (RecyclerView) view.findViewById(R.id.recipe_steps_description);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecipeStepsList.setLayoutManager(linearLayoutManager);
        mStepsDescriptionAdapter = new RecipeStepsDescriptionAdapter(getContext(), mRecipeStepModelList, this);
        mRecipeStepsList.setAdapter(mStepsDescriptionAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mRecipeStepModelList = getArguments().getParcelableArrayList(BUNDLE_STEPS);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent destinationIntent = new Intent(getActivity(), RecipeDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BUNDLE_STEPS, (ArrayList<? extends Parcelable>) mRecipeStepModelList);
        bundle.putInt(SELECTED_STEP_INDEX_KEY, position);
        destinationIntent.putExtra(STEPS_BUNDLE_KEY, bundle);
        getActivity().startActivity(destinationIntent);
    }
}
