package com.sergiom.lolabeer.uitests

import android.content.Intent
import android.view.View
import androidx.cardview.widget.CardView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Checks
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withTagValue
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.sergiom.lolabeer.MainActivity
import com.sergiom.lolabeer.R
import com.sergiom.lolabeer.app.LolaBeerApp
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BeerDetailTest {

    @get:Rule
    val mActivityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun setUp() {
        val intent = Intent(LolaBeerApp.getAppContext(), MainActivity::class.java)
        mActivityRule.launchActivity(intent)
    }

    @Test
    fun detailViewIsLoaded() {
        goToBeerDetailView()
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.detail_close_button)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.favourite)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.beer_detail_image)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.beer_detail_name)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.beer_description)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun clickOnImageShowsDialog() {
        goToBeerDetailView()
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.beer_detail_image)).perform(click())
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.beer_image_zoom)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun clickOnFavChangeDrawable() {
        goToBeerDetailView()
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.favourite)).perform(click())
        Thread.sleep(1000)
        Espresso.onView(withTagValue(Matchers.`is`("favourite"))).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.favourite)).perform(click())
        Thread.sleep(1000)
        Espresso.onView(withTagValue(Matchers.`is`("notFavourite"))).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    @Test
    fun checkFavouriteChangeColorInList() {
        goToBeerDetailView()
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.favourite)).perform(click())
        Thread.sleep(1000)
        Espresso.pressBack()
        Thread.sleep(3000)
        Espresso.onView(withRecyclerView(R.id.beers_by_style_recycler_view).atPosition(0)).check(matches(withCardViewColor(-6035539)))
        //The color is -1 for white and -6035539 for color accent
    }

    /*
    * OTHER FUNCTIONS AND CLASSES NEEDED
    * */
    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }

    private fun goToBeerDetailView() {
        Thread.sleep(2000)
        Espresso.onView(withRecyclerView(R.id.style_recycler_view).atPosition(0))
            .perform(ViewActions.click())
        Thread.sleep(3000)
        Espresso.onView(withRecyclerView(R.id.beers_by_style_recycler_view).atPosition(0))
            .perform(ViewActions.click())
    }

    private fun withCardViewColor(color: Int): Matcher<View?>? {
        Checks.checkNotNull(color)
        return object : BoundedMatcher<View?, CardView>(CardView::class.java) {
            override fun matchesSafely(warning: CardView): Boolean {
                val back = warning.cardBackgroundColor.defaultColor
                return color == back
            }

            override fun describeTo(description: Description) {
                description.appendText("with text color: ")
            }
        }
    }
}