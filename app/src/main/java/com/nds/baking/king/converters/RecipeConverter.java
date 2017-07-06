package com.nds.baking.king.converters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.nds.baking.king.models.RecipeIngredientModel;
import com.nds.baking.king.models.RecipeModel;
import com.nds.baking.king.models.RecipeResponseModel;
import com.nds.baking.king.models.RecipeStepModel;
import com.nds.baking.king.net.tos.Ingredient;
import com.nds.baking.king.net.tos.Recipe;
import com.nds.baking.king.net.tos.Step;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Namrata Shah on 5/9/2017.
 */
/*
* RecipeConverter converts JSON response to model.
* */
public class RecipeConverter {

private static final String TAG = "TEST"+RecipeConverter.class.getSimpleName();

    public static RecipeResponseModel convert(String jsonResponse) {
        Logger.d(TAG,"received valid response ");
        JsonParser jsonParser = new JsonParser();
        if(jsonResponse == null)
            return null;
        Logger.d(TAG,"response is "+jsonResponse);
        JsonElement jsonElement = jsonParser.parse(jsonResponse);
        JsonArray recipeList = jsonElement.getAsJsonArray();
        RecipeResponseModel responseModel = new RecipeResponseModel(toRecipeListModel(recipeList));
        return responseModel;
    }

    private static List<RecipeModel> toRecipeListModel(JsonArray recipeList) {
        if(recipeList == null){
            return null;
        }
        List<RecipeModel> recipeModel = new ArrayList<>();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        for(int i =0; i < recipeList.size(); i++){
            Logger.d(TAG,"iterate response ");
            Recipe recipe = gson.fromJson(recipeList.get(i), Recipe.class);
            RecipeModel model = new RecipeModel(Integer.toString(recipe.getID()),
                    recipe.getRecipeName(), toRecipeStepsListModel(recipe.getSteps()),
                    toRecipeIngredientListModel(recipe.getIngredients()));
            model.setRecipeImgURL(recipe.getImageURL());
            model.setServings(Integer.toString(recipe.getServings()));
            recipeModel.add(model);
        }
        return recipeModel;
    }

    private static List<RecipeIngredientModel> toRecipeIngredientListModel(List<Ingredient> ingredients) {
        if(ingredients == null)
            return null;
        List<RecipeIngredientModel> ingredientModels = new ArrayList<>();
        for(int index = 0; index < ingredients.size(); index++){
            Ingredient ingredient = ingredients.get(index);
            RecipeIngredientModel model = new RecipeIngredientModel(Integer.toString(ingredient.getQuantity()),
                    ingredient.getMeasure(), ingredient.getIngredientName());
            ingredientModels.add(model);
        }
        return ingredientModels;
    }

    private static List<RecipeStepModel> toRecipeStepsListModel(List<Step> steps) {
        if(steps == null)
            return null;
        List<RecipeStepModel> stepsModel = new ArrayList<>();
        for(int index = 0; index < steps.size(); index++){
            Step step = steps.get(index);
            RecipeStepModel model = new RecipeStepModel(Integer.toString(step.getStepID()),
                    step.getShortDescription(), step.getDescription());
            model.setThumbNailURL(step.getThumbNailURL());
            model.setVideoURL(step.getVideoURL());
            stepsModel.add(model);
        }
        return stepsModel;
    }
}
