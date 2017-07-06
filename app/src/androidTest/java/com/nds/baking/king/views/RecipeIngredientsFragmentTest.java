package com.nds.baking.king.views;

import android.support.test.runner.AndroidJUnit4;

import com.nds.baking.king.FragmentTestRule;
import com.nds.baking.king.R;
import com.nds.baking.king.views.Fragments.RecipeIngredientsFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Namrata Shah on 6/5/2017.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeIngredientsFragmentTest {

    @Rule
    public FragmentTestRule<RecipeIngredientsFragment> mFragmentTestRule = new FragmentTestRule<>(RecipeIngredientsFragment.class);

    @Test
    public void display_recipe_ingredient_fragment() {
        mFragmentTestRule.launchActivity(null);
        onView(withId(R.id.main_container)).check(matches(isDisplayed()));
        onView(withId(R.id.ingredientsList)).check(matches(isDisplayed()));
    }
}
