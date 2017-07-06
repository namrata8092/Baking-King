package com.nds.baking.king.net.tos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Namrata Shah on 5/8/2017.
 */
/*transfer object for recipe
* */
public class Recipe {
    @SerializedName("id")
    private int ID;
    @SerializedName("name")
    private String recipeName;
    @SerializedName("ingredients")
    private List<Ingredient> ingredients;
    @SerializedName("steps")
    private List<Step> steps;
    @SerializedName("servings")
    private int servings;
    @SerializedName("image")
    private String imageURL;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "ID=" + ID +
                ", recipeName='" + recipeName + '\'' +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                ", servings=" + servings +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (ID != recipe.ID) return false;
        if (servings != recipe.servings) return false;
        if (!recipeName.equals(recipe.recipeName)) return false;
        if (!ingredients.equals(recipe.ingredients)) return false;
        if (!steps.equals(recipe.steps)) return false;
        return imageURL.equals(recipe.imageURL);
    }

    @Override
    public int hashCode() {
        int result = ID;
        result = 31 * result + recipeName.hashCode();
        result = 31 * result + ingredients.hashCode();
        result = 31 * result + steps.hashCode();
        result = 31 * result + servings;
        result = 31 * result + imageURL.hashCode();
        return result;
    }
}
