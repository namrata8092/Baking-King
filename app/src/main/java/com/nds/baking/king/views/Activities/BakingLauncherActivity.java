package com.nds.baking.king.views.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.nds.baking.king.BakingApplication;
import com.nds.baking.king.R;
import com.nds.baking.king.models.RecipeResponseModel;
import com.nds.baking.king.net.requests.NetworkRequestManager;
import com.nds.baking.king.net.requests.NetworkRequester;
import com.nds.baking.king.utils.NetworkUtil;
import com.nds.baking.king.views.Fragments.ErrorMessageFragment;
import com.nds.baking.king.views.Fragments.RecipeCollectionFragment;

import java.lang.ref.WeakReference;

public class BakingLauncherActivity extends AppCompatActivity {
    private ProgressBar mProgressBar;
    private BakingApplication mBakingApplication;
    private NetworkRequestManager mNetworkRequestManager;
    private BakingLauncherActivity mBakingLauncherActivity;
    private FragmentManager mFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_baking_launcher);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);

        mBakingLauncherActivity = BakingLauncherActivity.this;
        mBakingApplication = (BakingApplication) mBakingLauncherActivity.getApplication();
        mNetworkRequestManager = mBakingApplication.getNetworkRequestManager();

        mFragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            return;
        } else {
            loadAppropriateFragment(mNetworkRequestManager);
        }

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
                mNetworkRequestManager.getRecipeList(new WeakReference<NetworkRequester>(bakingNetworkRequster), NetworkRequestManager.REQUEST_URL,
                        getResources().getString(R.string.recipe_request_tag));
            }
        }

    }

    private NetworkRequester bakingNetworkRequster = new NetworkRequester() {
        @Override
        public void onFailure(Throwable error) {
            Log.d("Test", "error " + error);
            hideProgressBar();
            ErrorMessageFragment errorMessageFragment =
                    ErrorMessageFragment.newInstance(getResources().getString(R.string.server_error_msg));
            mFragmentManager.beginTransaction().replace(R.id.main_container, errorMessageFragment).commit();
        }

        @Override
        public void onSuccess(Object respObj) {
            RecipeResponseModel responseModel = (RecipeResponseModel) respObj;
            RecipeCollectionFragment recipeCollectionFragment = RecipeCollectionFragment.newInstance(responseModel);
            mFragmentManager.beginTransaction().replace(R.id.main_container, recipeCollectionFragment).commit();
        }
    };

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

}
