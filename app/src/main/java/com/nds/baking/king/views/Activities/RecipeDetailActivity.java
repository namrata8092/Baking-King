package com.nds.baking.king.views.Activities;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.nds.baking.king.R;
import com.nds.baking.king.models.RecipeStepModel;
import com.nds.baking.king.views.adapter.RecipeDetailPagerAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Namrata Shah on 5/8/2017.
 */

public class RecipeDetailActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager mRecipeDetailViewPager;
    private static final String BUNDLE_STEPS ="stepsDescription";
    private static final String SELECTED_STEP_INDEX_KEY="selectedStepModel";
    private static final String STEPS_BUNDLE_KEY="selectedStepSBundle";
    private static final String RECIPE_NAME ="recipeName";
    private List<RecipeStepModel> mRecipeStepModelList;
    private int mSelectedStep = 0;
    private int mTotalSteps = 0;
    private RecipeDetailPagerAdapter detailpageAdapter;
    private ImageButton mPreviousButton;
    private ImageButton mNextButton;
    private String mTitle;
    private static final String TAG="TEST"+RecipeDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        if(savedInstanceState!=null && savedInstanceState.containsKey(BUNDLE_STEPS)){
            mRecipeStepModelList = savedInstanceState.getParcelableArrayList(BUNDLE_STEPS);
            mSelectedStep = savedInstanceState.getInt(SELECTED_STEP_INDEX_KEY);
            mTitle = savedInstanceState.getString(RECIPE_NAME);
        }else if(getIntent().getBundleExtra(STEPS_BUNDLE_KEY)!=null){
            Bundle bundle = getIntent().getBundleExtra(STEPS_BUNDLE_KEY);
            mRecipeStepModelList = bundle.getParcelableArrayList(BUNDLE_STEPS);
            mSelectedStep = bundle.getInt(SELECTED_STEP_INDEX_KEY);
            mTitle = bundle.getString(RECIPE_NAME);
        }

        setTitle(mTitle);

        mTotalSteps = mRecipeStepModelList.size();
        mRecipeDetailViewPager = (ViewPager)findViewById(R.id.detailSlidePager);

        mPreviousButton = (ImageButton)findViewById(R.id.previousButton);
        mPreviousButton.setOnClickListener(this);

        mNextButton = (ImageButton)findViewById(R.id.nextButton);
        mNextButton.setOnClickListener(this);
        Logger.d(TAG,"onCreate");
        loadRecipeStepsSlides();

        setRecipeIndicator();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(BUNDLE_STEPS, (ArrayList<? extends Parcelable>) mRecipeStepModelList);
        outState.putInt(SELECTED_STEP_INDEX_KEY,mSelectedStep);
        outState.putString(RECIPE_NAME, mTitle);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d(TAG,"onResume");
        if(detailpageAdapter!=null){
            loadRecipeStepsSlides();
            detailpageAdapter.notifyDataSetChanged();
            mRecipeDetailViewPager.setCurrentItem(mSelectedStep);
        }
    }

    private void loadRecipeStepsSlides() {
        Logger.d(TAG,"loadRecipeStepsSlides");
        FragmentManager fragmentManager = getSupportFragmentManager();
        detailpageAdapter = new RecipeDetailPagerAdapter(fragmentManager, mRecipeStepModelList);
        mRecipeDetailViewPager.setAdapter(detailpageAdapter);
        mRecipeDetailViewPager.addOnPageChangeListener(this);
    }

    private void setRecipeIndicator() {
        Logger.d(TAG,"setRecipeIndicator");
        if(mSelectedStep == 0){
            setBackGround(mPreviousButton, getResources().getDrawable(R.drawable.previous_disable));
            setBackGround(mNextButton, getResources().getDrawable(R.drawable.next));
        }else if(mSelectedStep == mTotalSteps -1){
            setBackGround(mPreviousButton, getResources().getDrawable(R.drawable.previous));
            setBackGround(mNextButton, getResources().getDrawable(R.drawable.next_disable));
        }else{
            setBackGround(mPreviousButton, getResources().getDrawable(R.drawable.previous));
            setBackGround(mNextButton, getResources().getDrawable(R.drawable.next));
        }
    }

    private void setBackGround(ImageButton imageButton, Drawable backgroundImg) {
        imageButton.setImageResource(android.R.color.transparent);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            imageButton.setBackground(backgroundImg);
        }else{
            imageButton.setBackgroundDrawable(backgroundImg);
        }
    }

    @Override
    public void onClick(View v) {
        Logger.d(TAG,"onClick "+mSelectedStep);
        switch (v.getId()){
            case R.id.previousButton :
                if(mSelectedStep > 0){
                    mSelectedStep--;
                    setRecipeIndicator();
                    mRecipeDetailViewPager.setCurrentItem(mSelectedStep);
                }
                break;
            case R.id.nextButton :
                if(mSelectedStep < mTotalSteps-1){
                    mSelectedStep++;
                    setRecipeIndicator();
                    mRecipeDetailViewPager.setCurrentItem(mSelectedStep);
                }
                break;
                default:
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Logger.d(TAG,"onPageSelected "+position);
        mSelectedStep = position;
        setRecipeIndicator();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
