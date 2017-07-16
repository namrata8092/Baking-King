package com.nds.baking.king.views.Activities;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.nds.baking.king.BuildConfig;
import com.nds.baking.king.R;

/**
 * Created by Namrata Shah on 7/4/2017.
 */
@VisibleForTesting
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            KeyguardManager.KeyguardLock keyguardLock = km.newKeyguardLock("TAG");
            keyguardLock.disableKeyguard();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(R.id.main_container);
        setContentView(frameLayout);
    }
}
