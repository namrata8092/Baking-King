package com.nds.baking.king.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.nds.baking.king.Constants;

/**
 * Created by Namrata Shah on 6/26/2017.
 */

public final class DataUtil {

    private DataUtil(){};

    public static boolean isTwoPanel(Context context){
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return mSharedPreferences.getBoolean(Constants.TWO_PANEL_KEY, false);
    }
}
