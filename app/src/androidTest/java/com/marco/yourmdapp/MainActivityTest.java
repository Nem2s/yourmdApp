package com.marco.yourmdapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule rule = new ActivityTestRule(MainActivity.class);


    @Test
    public void search_method() {
        Espresso.onView(ViewMatchers.withId(R.id.tiet_search)).perform(ViewActions.typeText("Hello"));
        Espresso.onView(ViewMatchers.withId(R.id.btn_search)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.error_message)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        Espresso.onView(ViewMatchers.withId(R.id.loading)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }


}
