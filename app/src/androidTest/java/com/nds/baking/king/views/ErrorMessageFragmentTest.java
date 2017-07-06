package com.nds.baking.king.views;

import android.support.test.runner.AndroidJUnit4;

import com.nds.baking.king.FragmentTestRule;
import com.nds.baking.king.R;
import com.nds.baking.king.views.Fragments.ErrorMessageFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Namrata Shah on 6/5/2017.
 */
@RunWith(AndroidJUnit4.class)
public class ErrorMessageFragmentTest {

    @Rule
    public FragmentTestRule<ErrorMessageFragment> mFragmentTestRule = new FragmentTestRule<>(ErrorMessageFragment.class);

    @Test
    public void display_error_fragment() {
        mFragmentTestRule.launchActivity(null);
        onView(withId(R.id.main_container)).check(matches(isDisplayed()));
        onView(withId(R.id.errorMessageTV)).check(matches(withText("this is error msg")));
        onView(withId(R.id.okButton)).check(matches(isDisplayed())).perform(click());
    }
}
