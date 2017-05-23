package com.nds.baking.king.views.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.nds.baking.king.R;
import com.nds.baking.king.models.RecipeModel;
import com.nds.baking.king.models.RecipeResponseModel;
import com.nds.baking.king.views.Activities.RecipeIngredientStepsActivity;
import com.nds.baking.king.views.adapter.RecipeCollectionAdapter;

import java.util.List;

/**
 * Created by Namrata Shah on 5/8/2017.
 */

public class RecipeCollectionFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String RECIPE_COLLECTION_BUNDLE_KEY = "recipes";
    private static final String SELECTED_RECIPE_BUNDLE_KEY = "selectedRecipe";
    private static final String SELECTED_RECIPE_INDEX_KEY = "selectedRecipeIndex";
    private static final String RECIPE_BUNDLE="recipeBundle";
    private RecipeResponseModel recipeResponseModel;
    private List<RecipeModel> recipes;
    private RecyclerView recipeCards;
    private RecipeCollectionAdapter recipeCollectionAdapter;
    private GridLayoutManager mGridLayoutManager;
    private int mSelectedRecipeIndex = 0;

    public static RecipeCollectionFragment newInstance(RecipeResponseModel responseModel){
        RecipeCollectionFragment fragment = new RecipeCollectionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE_COLLECTION_BUNDLE_KEY, responseModel);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View recipeCollectionView = inflater.inflate(R.layout.recipe_collection_fragment, container, false);
        recipeCards = (RecyclerView)recipeCollectionView.findViewById(R.id.recipeCards);
        mGridLayoutManager = new GridLayoutManager(getContext(), getResources().getInteger(R.integer.no_of_grid_cells));
        recipeCards.setLayoutManager(mGridLayoutManager);
        recipeCollectionAdapter = new RecipeCollectionAdapter(getContext(), recipes, this);
        recipeCards.setAdapter(recipeCollectionAdapter);

        return recipeCollectionView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null && savedInstanceState.containsKey(SELECTED_RECIPE_INDEX_KEY)){
            mSelectedRecipeIndex = savedInstanceState.getParcelable(SELECTED_RECIPE_INDEX_KEY);
        }
        if(getArguments()!=null){
            recipeResponseModel = getArguments().getParcelable(RECIPE_COLLECTION_BUNDLE_KEY);
        }
        recipes = recipeResponseModel.getRecipes();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_RECIPE_INDEX_KEY, mSelectedRecipeIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mSelectedRecipeIndex = position;
        RecipeModel selectedRecipe = recipeResponseModel.getRecipes().get(position);
        Intent destinationIntent = new Intent(getActivity(), RecipeIngredientStepsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(SELECTED_RECIPE_BUNDLE_KEY, selectedRecipe);
        destinationIntent.putExtra(RECIPE_BUNDLE, bundle);
        getActivity().startActivity(destinationIntent);
    }
}