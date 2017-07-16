package com.nds.baking.king.views;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.nds.baking.king.R;
import com.nds.baking.king.views.Activities.RecipeIngredientStepsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Namrata Shah on 6/5/2017.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeIngredientStepsActivityTest {
    @Rule
    public ActivityTestRule<RecipeIngredientStepsActivity> recipeIngredientStepsActivity = new ActivityTestRule<>(RecipeIngredientStepsActivity.class);

    @Test
    public void display_ingredients_title(){
        onView((withId(R.id.recipe_ingredients_title))).check(matches(isDisplayed()));
        onView((withId(R.id.recipe_ingredients_title))).check(matches(withText("Recipe Ingredients")));
    }

    @Test
    public void display_steps_title(){
        onView((withId(R.id.recipe_steps_title))).check(matches(isDisplayed()));
        onView((withId(R.id.recipe_steps_title))).check(matches(withText("Recipe Steps")));
    }
}
