package com.nds.baking.king.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Namrata Shah on 5/9/2017.
 */

public class RecipeIngredientModel implements Parcelable{

    private final String ingredientQuantity;
    private final String ingredientMeasure;
    private final String ingredientName;

    public RecipeIngredientModel(String qnty, String measure, String name){
        this.ingredientQuantity = qnty;
        this.ingredientMeasure = measure;
        this.ingredientName = name;
    }

    public String getIngredientQuantity() {
        return ingredientQuantity;
    }

    public String getIngredientMeasure() {
        return ingredientMeasure;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    protected RecipeIngredientModel(Parcel in) {
        ingredientQuantity = in.readString();
        ingredientMeasure = in.readString();
        ingredientName = in.readString();
    }

    public static final Creator<RecipeIngredientModel> CREATOR = new Creator<RecipeIngredientModel>() {
        @Override
        public RecipeIngredientModel createFromParcel(Parcel in) {
            return new RecipeIngredientModel(in);
        }

        @Override
        public RecipeIngredientModel[] newArray(int size) {
            return new RecipeIngredientModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ingredientQuantity);
        dest.writeString(ingredientMeasure);
        dest.writeString(ingredientName);
    }
}
