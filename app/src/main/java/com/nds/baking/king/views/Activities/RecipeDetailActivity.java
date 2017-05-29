package com.nds.baking.king.views.Activities;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.nds.baking.king.R;
import com.nds.baking.king.models.RecipeStepModel;
import com.nds.baking.king.views.adapter.RecipeDetailPagerAdapter;

import java.util.List;

/**
 * Created by Namrata Shah on 5/8/2017.
 */

public class RecipeDetailActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager mRecipeDetailViewPager;
    private static final String BUNDLE_STEPS ="stepsDescription";
    private static final String SELECTED_STEP_INDEX_KEY="selectedStepModel";
    private static final String STEPS_BUNDLE_KEY="selectedStepSBundle";
    private List<RecipeStepModel> mRecipeStepModelList;
    private int mSelectedStep = 0;
    private int mTotalSteps = 0;
    private RecipeDetailPagerAdapter detailpageAdapter;
    private ImageButton mPreviousButton;
    private ImageButton mNextButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        if(savedInstanceState!=null){
            return;
        }else if(getIntent().getBundleExtra(STEPS_BUNDLE_KEY)!=null){
            Bundle bundle = getIntent().getBundleExtra(STEPS_BUNDLE_KEY);
            mRecipeStepModelList = bundle.getParcelableArrayList(BUNDLE_STEPS);
            mSelectedStep = bundle.getInt(SELECTED_STEP_INDEX_KEY);
            mTotalSteps = mRecipeStepModelList.size();
        }
        mRecipeDetailViewPager = (ViewPager)findViewById(R.id.detailSlidePager);

        mPreviousButton = (ImageButton)findViewById(R.id.previousButton);
        mPreviousButton.setOnClickListener(this);

        mNextButton = (ImageButton)findViewById(R.id.nextButton);
        mNextButton.setOnClickListener(this);

        loadRecipeStepsSlides();

        setRecipeIndicator();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(detailpageAdapter!=null){
            loadRecipeStepsSlides();
            detailpageAdapter.notifyDataSetChanged();
            mRecipeDetailViewPager.setCurrentItem(mSelectedStep);
        }
    }

    private void loadRecipeStepsSlides() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        detailpageAdapter = new RecipeDetailPagerAdapter(fragmentManager, mRecipeStepModelList);
        mRecipeDetailViewPager.setAdapter(detailpageAdapter);
        mRecipeDetailViewPager.addOnPageChangeListener(this);
    }

    private void setRecipeIndicator() {
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
        mSelectedStep = position;
        setRecipeIndicator();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
