package com.nds.baking.king.utils;

import android.text.TextUtils;

import com.nds.baking.king.models.RecipeModel;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Namrata Shah on 5/9/2017.
 */

public final class ValidationUtil {

    private ValidationUtil(){}

    public static boolean isValidString(String stringForCheck){
        return stringForCheck != null && !TextUtils.isEmpty(stringForCheck.trim());
    }

    public static boolean isValidRecipeList(List<RecipeModel> recipeList){
        return recipeList != null && recipeList.size() > 0 && !recipeList.isEmpty();
    }

    public static boolean isValidJSON(JSONObject jsonObject){
        return jsonObject != null && !TextUtils.isEmpty(jsonObject.toString());
    }
}
