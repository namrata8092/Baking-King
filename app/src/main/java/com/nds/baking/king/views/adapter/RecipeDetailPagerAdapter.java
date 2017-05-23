package com.nds.baking.king.views.adapter;

import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nds.baking.king.models.RecipeStepModel;
import com.nds.baking.king.views.Fragments.RecipeStepSlideFragment;

import java.util.List;

/**
 * Created by Namrata Shah on 5/21/2017.
 */

public class RecipeDetailPagerAdapter extends FragmentStatePagerAdapter {
private List<RecipeStepModel> mRecipeStepModelList;

    public RecipeDetailPagerAdapter(FragmentManager fm,List<RecipeStepModel> modelList) {
        super(fm);
        this.mRecipeStepModelList = modelList;
    }

    @Override
    public RecipeStepSlideFragment getItem(int position) {
        RecipeStepSlideFragment recipeStepSlideFragment = RecipeStepSlideFragment.newInstance(mRecipeStepModelList.get(position));
        return recipeStepSlideFragment;
    }

    @Override
    public int getCount() {
        if(mRecipeStepModelList !=null)
            return mRecipeStepModelList.size();
        return 0;
    }
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {

    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}
