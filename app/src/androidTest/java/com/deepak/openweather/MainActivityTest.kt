package com.deepak.openweather

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import com.deepak.openweather.ui.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    lateinit var scenario: ActivityScenario<MainActivity>

    @get:Rule
    val rule = activityScenarioRule<MainActivity>()

    @Before
    fun setUp(){
        scenario = rule.scenario
    }

    @Test
    fun `activityLaunchViewsVisible`(){

        onView(withId(R.id.todaysImage)).check(matches(isDisplayed()))
        onView(withId(R.id.todaysTemperature)).check(matches(isDisplayed()))
        onView(withId(R.id.todaysMaxTemperature)).check(matches(isDisplayed()))
        onView(withId(R.id.todaysMinTemperature)).check(matches(isDisplayed()))
        onView(withId(R.id.todaysDescription)).check(matches(isDisplayed()))
        onView(withId(R.id.wind)).check(matches(isDisplayed()))
        onView(withId(R.id.place)).check(matches(isDisplayed()))

    }


    @After
    fun cleanUp(){
        scenario.close()

    }
}