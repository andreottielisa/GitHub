package com.api.github.presentation

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.api.github.R
import com.api.github.presentation.fragment.ListRepoFragment
import com.api.github.presentation.fragment.PullRequestsFragment
import com.api.github.utils.getFragment
import com.api.github.utils.isDisplayed
import com.api.github.utils.isGone
import com.api.github.utils.isVisible
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        activityRule.launchActivity(Intent())
    }

    @Test
    fun testLaunchHomeFragment() {
        val fragment = activityRule.activity.getFragment(R.id.contentFragment)
        Assert.assertEquals(ListRepoFragment::class.java, fragment?.javaClass)
    }

    @Test
    fun testContentFragmentDisplayed() {
        onView(
            withId(R.id.contentFragment)
        ).isDisplayed()
    }

    @Test
    fun testBackNavToListRepo(){
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        var fragment = activityRule.activity.getFragment(R.id.contentFragment)
        Assert.assertEquals(PullRequestsFragment::class.java, fragment?.javaClass)

        pressBack()

        fragment = activityRule.activity.getFragment(R.id.contentFragment)
        Assert.assertEquals(ListRepoFragment::class.java, fragment?.javaClass)
    }

    @Test
    fun testLoadDescription() {
        onView(withId(R.id.item_progressBar))
            .check(matches(withContentDescription("Carregando…")))
    }

    @Test
    fun testShowLoad() {
        activityRule.runOnUiThread {
            activityRule.activity.showLoad()
        }

        onView(
            withId(R.id.progressBar)
        ).isVisible()
    }

    @Test
    fun testHideLoad() {
        activityRule.runOnUiThread {
            activityRule.activity.hideLoad()
        }

        onView(withId(R.id.progressBar)).isGone()
    }

    @Test
    fun testPullRequestItemSelected(){
        onView(withId(R.id.recyclerView))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withText("go")).isDisplayed()
        onView(withContentDescription("Voltar")).perform(click());
    }

}