package com.nds.baking.king.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.nds.baking.king.utils.ValidationUtil;

import java.util.List;

/**
 * Created by Namrata Shah on 5/9/2017.
 */
/*This is model class for recipe
* */
public class RecipeModel implements Parcelable{

    private final String recipeID;
    private final String recipeName;
    private final List<RecipeStepModel> recipeStepModelList;
    private final List<RecipeIngredientModel> recipeIngredientModelList;
    private String servings;
    private String recipeImgURL;
    private boolean isImgURL;

    public RecipeModel(String id, String name,
                       List<RecipeStepModel> steps,
                       List<RecipeIngredientModel> ingredients){
        this.recipeID = id;
        this.recipeName = name;
        this.recipeStepModelList = steps;
        this.recipeIngredientModelList = ingredients;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public List<RecipeStepModel> getRecipeStepModelList() {
        return recipeStepModelList;
    }

    public List<RecipeIngredientModel> getRecipeIngredientModelList() {
        return recipeIngredientModelList;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getRecipeImgURL() {
        return recipeImgURL;
    }

    public void setRecipeImgURL(String recipeImgURL) {
        this.recipeImgURL = recipeImgURL;
    }

    public boolean isImgURL() {
        return ValidationUtil.isValidString(this.recipeImgURL);
    }

    protected RecipeModel(Parcel in) {
        recipeID = in.readString();
        recipeName = in.readString();
        recipeStepModelList = in.createTypedArrayList(RecipeStepModel.CREATOR);
        recipeIngredientModelList = in.createTypedArrayList(RecipeIngredientModel.CREATOR);
        servings = in.readString();
        recipeImgURL = in.readString();
        isImgURL = in.readByte() != 0;
    }

    public static final Creator<RecipeModel> CREATOR = new Creator<RecipeModel>() {
        @Override
        public RecipeModel createFromParcel(Parcel in) {
            return new RecipeModel(in);
        }

        @Override
        public RecipeModel[] newArray(int size) {
            return new RecipeModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recipeID);
        dest.writeString(recipeName);
        dest.writeTypedList(recipeStepModelList);
        dest.writeTypedList(recipeIngredientModelList);
        dest.writeString(servings);
        dest.writeString(recipeImgURL);
        dest.writeByte((byte) (isImgURL ? 1 : 0));
    }
}
