package com.nds.baking.king.views;

import android.support.test.runner.AndroidJUnit4;
import com.nds.baking.king.FragmentTestRule;
import com.nds.baking.king.R;
import com.nds.baking.king.views.Fragments.RecipeCollectionFragment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by Namrata Shah on 6/5/2017.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeCollectionFragmentTest {

    @Rule
    public FragmentTestRule<RecipeCollectionFragment> mFragmentTestRule = new FragmentTestRule<>(RecipeCollectionFragment.class);

    @Test
    public void display_recipe_collection_fragment() {
        mFragmentTestRule.launchActivity(null);
        onView(withId(R.id.recipeCards)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.recipeCards),withParent(withId(R.id.main_container)),isDisplayed()));
    }
}
