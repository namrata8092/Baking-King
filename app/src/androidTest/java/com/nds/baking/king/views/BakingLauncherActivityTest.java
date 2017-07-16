package com.nds.baking.king.views;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.nds.baking.king.R;
import com.nds.baking.king.views.Activities.BakingLauncherActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Created by Namrata Shah on 6/30/2017.
 */
@RunWith(AndroidJUnit4.class)
public class BakingLauncherActivityTest {

    @Rule
    public ActivityTestRule<BakingLauncherActivity> bakingLauncherActivityActivityTestRule =
            new ActivityTestRule<>(BakingLauncherActivity.class);

    @Test
    public void show_container_for_fragment() {
        onView(allOf(withId(R.id.main_container))).check(matches(isDisplayed()));
    }

}
