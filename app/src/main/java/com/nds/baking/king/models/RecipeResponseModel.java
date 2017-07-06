package com.nds.baking.king.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.nds.baking.king.utils.ValidationUtil;

import java.util.List;

/**
 * Created by Namrata Shah on 5/9/2017.
 */
/*This is model class for recipe response
* */
public class RecipeResponseModel implements Parcelable{

    private final List<RecipeModel> recipes;
    private boolean isRecipeAvailable;

    public RecipeResponseModel(List<RecipeModel> recipes){
        this.recipes = recipes;
    }

    public List<RecipeModel> getRecipes() {
        return recipes;
    }

    public boolean isRecipeAvailable() {
        return ValidationUtil.isValidRecipeList(this.recipes);
    }

    protected RecipeResponseModel(Parcel in) {
        recipes = in.createTypedArrayList(RecipeModel.CREATOR);
        isRecipeAvailable = in.readByte() != 0;
    }

    public static final Creator<RecipeResponseModel> CREATOR = new Creator<RecipeResponseModel>() {
        @Override
        public RecipeResponseModel createFromParcel(Parcel in) {
            return new RecipeResponseModel(in);
        }

        @Override
        public RecipeResponseModel[] newArray(int size) {
            return new RecipeResponseModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(recipes);
        dest.writeByte((byte) (isRecipeAvailable ? 1 : 0));
    }
}
