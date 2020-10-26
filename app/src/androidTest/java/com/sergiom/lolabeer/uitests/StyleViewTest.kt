package com.sergiom.lolabeer.uitests

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
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
class StyleViewTest {
    @get:Rule
    val mActivityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun setUp() {
        val intent = Intent(LolaBeerApp.getAppContext(), MainActivity::class.java)
        mActivityRule.launchActivity(intent)
    }

    @Test
    fun recyclerViewIsLoaded() {
        Thread.sleep(2000)
        Espresso.onView(withRecyclerView(R.id.style_recycler_view).atPosition(0)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Espresso.onView(withRecyclerView(R.id.style_recycler_view).atPosition(2))
            .check(ViewAssertions.matches(ViewMatchers.hasDescendant(withText("North American Origin Ales"))))
    }


    /*
    * OTHER FUNCTIONS AND CLASSES NEEDED
    * */
    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }
}