package com.nds.baking.king.views.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.nds.baking.king.R;

/**
 * Created by Namrata Shah on 7/4/2017.
 */
@VisibleForTesting
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(R.id.main_container);
        setContentView(frameLayout);
    }
}
