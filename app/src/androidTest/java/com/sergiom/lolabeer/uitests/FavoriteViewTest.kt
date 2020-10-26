package com.sergiom.lolabeer.uitests

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.sergiom.lolabeer.MainActivity
import com.sergiom.lolabeer.R
import com.sergiom.lolabeer.app.LolaBeerApp
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteViewTest {

    @get:Rule
    val mActivityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun setUp() {
        val intent = Intent(LolaBeerApp.getAppContext(), MainActivity::class.java)
        mActivityRule.launchActivity(intent)
    }

    @Test
    fun clickOnMenuFav() {
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.menu_fav)).perform(ViewActions.click())
    }

    @Test
    fun clickOnMenuFavGoesToFavourites() {
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.menu_fav)).perform(ViewActions.click())
        Thread.sleep(1000)
        Espresso.onView(withText(R.string.action_settings)).perform(ViewActions.click())
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.beers_by_style_recycler_view)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    /*
    * OTHER FUNCTIONS AND CLASSES NEEDED
    * */
    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }
}