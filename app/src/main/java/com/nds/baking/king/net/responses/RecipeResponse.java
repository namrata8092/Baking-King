package com.nds.baking.king.net.responses;

import com.google.gson.annotations.SerializedName;
import com.nds.baking.king.net.tos.Recipe;

import java.util.List;

/**
 * Created by Namrata Shah on 5/9/2017.
 */

public class RecipeResponse {

    @SerializedName("array")
    private List<Recipe> recipeList;

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecipeResponse that = (RecipeResponse) o;

        return recipeList.equals(that.recipeList);
    }

    @Override
    public int hashCode() {
        return recipeList.hashCode();
    }

    @Override
    public String toString() {
        return "RecipeResponse{" +
                "recipeList=" + recipeList +
                '}';
    }
}
