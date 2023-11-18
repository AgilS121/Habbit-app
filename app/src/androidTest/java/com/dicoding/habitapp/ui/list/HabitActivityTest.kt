package com.dicoding.habitapp.ui.list

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dicoding.habitapp.R
import com.dicoding.habitapp.ui.add.AddHabitActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

//TODO 16 : Write UI test to validate when user tap Add Habit (+), the AddHabitActivity displayed
class HabitActivityTest {
    @get:Rule
    val activity = ActivityScenarioRule(HabitListActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @Test
    fun test_listHabits() {
        onView(withId(R.id.fab)).perform(click())
        intended(hasComponent(AddHabitActivity::class.java.name))
    }

    @After
    fun doneIntents() {
        Intents.release()
    }
}