package com.nds.baking.king.converters;

import com.nds.baking.king.models.RecipeIngredientModel;
import com.nds.baking.king.models.RecipeModel;
import com.nds.baking.king.models.RecipeResponseModel;
import com.nds.baking.king.models.RecipeStepModel;
import com.nds.baking.king.net.responses.RecipeResponse;
import com.nds.baking.king.net.tos.Ingredient;
import com.nds.baking.king.net.tos.Recipe;
import com.nds.baking.king.net.tos.Step;
import com.nds.baking.king.utils.JsonSerializationHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Namrata Shah on 5/9/2017.
 */

public class RecipeConverter {

    public static RecipeResponseModel convert(String jsonResponse) {
        RecipeResponse response = JsonSerializationHelper.deserializeObject(RecipeResponse.class, jsonResponse);
        RecipeResponseModel responseModel = new RecipeResponseModel(toRecipeListModel(response.getRecipeList()));
        return responseModel;
    }

    private static List<RecipeModel> toRecipeListModel(List<Recipe> recipeList) {
        if(recipeList == null){
            return null;
        }
        List<RecipeModel> recipeModel = new ArrayList<>();
        for(int index = 0; index < recipeList.size(); index++){
            Recipe recipe = recipeList.get(index);
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
        for(int index = 0; index < stepsModel.size(); index++){
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
