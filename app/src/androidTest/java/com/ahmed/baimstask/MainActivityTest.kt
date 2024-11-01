package com.ahmed.baimstask

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.ahmed.baimstask.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun test_spinner_displaysCities() {
        onView(withId(R.id.citySpinner)).check(matches(isDisplayed()))
    }

    @Test
    fun test_retryButton_displaysError_whenNoData() {
        onView(withId(R.id.retry)).perform(click())
        onView(withId(R.id.no_data)).check(matches(isDisplayed()))
    }

    @Test
    fun test_weatherRecyclerView_isVisible_afterLoading() {
        onView(withId(R.id.weather_recycler_view)).check(matches(isDisplayed()))
    }
}
