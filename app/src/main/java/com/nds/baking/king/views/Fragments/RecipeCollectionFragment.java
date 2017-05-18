package com.nds.baking.king.views.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.nds.baking.king.R;
import com.nds.baking.king.models.RecipeModel;
import com.nds.baking.king.models.RecipeResponseModel;
import com.nds.baking.king.views.adapter.RecipeCollectionAdapter;

import java.util.List;

/**
 * Created by Namrata Shah on 5/8/2017.
 */

public class RecipeCollectionFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String RECIPE_COLLECTION_BUNDLE_KEY = "recipes";
    private RecipeResponseModel recipeResponseModel;
    private List<RecipeModel> recipes;
    private RecyclerView recipeCards;
    private RecipeCollectionAdapter recipeCollectionAdapter;
    private GridLayoutManager mGridLayoutManager;

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
        mGridLayoutManager = new GridLayoutManager(getContext(), getCellCount(getContext()));
        recipeCards.setLayoutManager(mGridLayoutManager);
        recipeCollectionAdapter = new RecipeCollectionAdapter(getContext(), recipes, this);
        recipeCards.setAdapter(recipeCollectionAdapter);

        return recipeCollectionView;
    }

    private int getCellCount(Context context) {
        int cellCount = 0;
        DisplayMetrics displayMetrics = (DisplayMetrics)context.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        if(width < 400){
            cellCount = 1;
        }else if(width <= 600){
            cellCount = 3;
        }else{
            cellCount = 5;
        }
        return cellCount;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null && savedInstanceState.containsKey(RECIPE_COLLECTION_BUNDLE_KEY)){
            recipeResponseModel = savedInstanceState.getParcelable(RECIPE_COLLECTION_BUNDLE_KEY);
        }else if(getArguments()!=null){
            recipeResponseModel = getArguments().getParcelable(RECIPE_COLLECTION_BUNDLE_KEY);
        }
        recipes = recipeResponseModel.getRecipes();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}