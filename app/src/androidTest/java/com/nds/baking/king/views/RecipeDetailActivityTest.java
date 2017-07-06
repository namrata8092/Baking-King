package com.nds.baking.king.views;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.nds.baking.king.R;
import com.nds.baking.king.views.Activities.RecipeDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isFocusable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Namrata Shah on 6/5/2017.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    @Rule
    ActivityTestRule<RecipeDetailActivity> recipeDetailActivity = new ActivityTestRule(RecipeDetailActivity.class);

    @Test
    public void display_recipe_slide_when_activity_created(){
        onView((withId(R.id.detailSlidePager))).check(matches(isDisplayed()));
    }

    @Test
    public void click_on_previous_icon_move_to_previous_recipe(){
        onView((withId(R.id.previousButton))).check(matches(isClickable()));
        onView((withId(R.id.previousButton))).check(matches(isFocusable()));
        onView((withId(R.id.previousButton))).perform(click());
    }

    @Test
    public void click_on_next_icon_move_to_next_recipe(){
        onView((withId(R.id.nextButton))).check(matches(isClickable()));
        onView((withId(R.id.nextButton))).check(matches(isFocusable()));
        onView((withId(R.id.previousButton))).perform(click());
    }
}
