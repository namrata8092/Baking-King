package com.nds.baking.king.views.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.nds.baking.king.BakingApplication;
import com.nds.baking.king.BuildConfig;
import com.nds.baking.king.R;
import com.nds.baking.king.converters.RecipeConverter;
import com.nds.baking.king.models.RecipeResponseModel;
import com.nds.baking.king.net.requests.NetworkRequestManager;
import com.nds.baking.king.net.requests.NetworkRequester;
import com.nds.baking.king.utils.JsonSerializationHelper;
import com.nds.baking.king.utils.NetworkUtil;
import com.nds.baking.king.views.Fragments.ErrorMessageFragment;
import com.nds.baking.king.views.Fragments.RecipeCollectionFragment;
import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;

public class BakingLauncherActivity extends AppCompatActivity {

    private static final String TAG = BakingLauncherActivity.class.getSimpleName();
    private static final String RECIPE_COLLECTION_BUNDLE_KEY = "recipes";
    private ProgressBar mProgressBar;
    private BakingApplication mBakingApplication;
    private NetworkRequestManager mNetworkRequestManager;
    private BakingLauncherActivity mBakingLauncherActivity;
    private FragmentManager mFragmentManager;
    private RecipeResponseModel mRecipeResponseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_baking_launcher);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);

        mBakingLauncherActivity = BakingLauncherActivity.this;
        mBakingApplication = (BakingApplication) mBakingLauncherActivity.getApplication();
        mNetworkRequestManager = mBakingApplication.getNetworkRequestManager();

        mFragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null && savedInstanceState.containsKey(RECIPE_COLLECTION_BUNDLE_KEY)) {
            mRecipeResponseModel = savedInstanceState.getParcelable(RECIPE_COLLECTION_BUNDLE_KEY);
            showRecipeCollectionFragment(mRecipeResponseModel, mFragmentManager);
        } else {
            loadAppropriateFragment(mNetworkRequestManager);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(RECIPE_COLLECTION_BUNDLE_KEY, mRecipeResponseModel);
        super.onSaveInstanceState(outState);
    }

    private void loadAppropriateFragment(NetworkRequestManager mNetworkRequestManager) {

        if (!NetworkUtil.isNetworkAvailable(getApplicationContext())) {
            ErrorMessageFragment errorMessageFragment =
                    ErrorMessageFragment.newInstance(getResources().getString(R.string.no_internet_error_msg));
            if (!isFinishing()) {
                mFragmentManager.beginTransaction().replace(R.id.main_container, errorMessageFragment).commit();
            }
        } else {
            showProgressBar();
            if (!isFinishing()) {
                if(BuildConfig.DEBUG){
                    int resourceID = getApplicationContext().getResources().getIdentifier(
                            "sample_repsonse", "raw", getPackageName());
                    String responseFromAssets = JsonSerializationHelper.readFakeResponseFromRaw( getApplicationContext(),resourceID);
                    mRecipeResponseModel = RecipeConverter.convert(responseFromAssets);
                    showRecipeCollectionFragment(mRecipeResponseModel, mFragmentManager);
                }else{
                    mNetworkRequestManager.getRecipeList(new WeakReference<NetworkRequester>(bakingNetworkRequster), NetworkRequestManager.REQUEST_URL,
                            getResources().getString(R.string.recipe_request_tag));
                }

            }
        }

    }

    private NetworkRequester bakingNetworkRequster = new NetworkRequester() {
        @Override
        public void onFailure(Throwable error) {
            Logger.e(TAG, "error " + error);
            hideProgressBar();
            ErrorMessageFragment errorMessageFragment =
                    ErrorMessageFragment.newInstance(getResources().getString(R.string.server_error_msg));
            mFragmentManager.beginTransaction().replace(R.id.main_container, errorMessageFragment, getResources().getString(R.string.error_fragment_tag)).commit();
        }

        @Override
        public void onSuccess(Object respObj) {
            RecipeResponseModel responseModel = (RecipeResponseModel) respObj;
            showRecipeCollectionFragment(responseModel, mFragmentManager);
        }
    };

    private void showRecipeCollectionFragment(RecipeResponseModel responseModel, FragmentManager mFragmentManager) {
        RecipeCollectionFragment recipeCollectionFragment = RecipeCollectionFragment.newInstance(responseModel);
        mFragmentManager.beginTransaction().replace(R.id.main_container, recipeCollectionFragment).commit();
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

}
