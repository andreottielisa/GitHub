package com.api.github.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.api.github.R
import com.api.github.domain.entities.RepoGitHubEntity
import com.api.github.utils.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.KoinComponent

@RunWith(AndroidJUnit4ClassRunner::class)
class PullRequestsFragmentTest : KoinComponent {

    private val mockedRepoEntity = RepoGitHubEntity(
        nameRepo = "go",
        descriptionRepo = "Open Source Continuous File Synchronization",
        username = "golang",
        avatar = "https://avatars0.githubusercontent.com/u/7628018?v=4",
        forksCount = "2677",
        stargazersCount = "31293"
    )

    private lateinit var scenario: FragmentScenario<PullRequestsFragment>

    @Before
    fun setupTest() {
        val fragmentArgs = Bundle().apply {
            putParcelable(REPO, mockedRepoEntity)
        }

        scenario = launchFragmentInContainer<PullRequestsFragment>(fragmentArgs, R.style.AppTheme)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testIsRecyclerViewVisibleInAppLaunch() {
        onView(withId(R.id.toolbar)).isDisplayed()
        onView(withId(R.id.recyclerView)).isDisplayed()
        onView(withId(R.id.alertView)).isGone()
    }

    @Test
    fun testFragmentNoArgs() {
        val scenario =
            launchFragmentInContainer<PullRequestsFragment>(themeResId = R.style.AppTheme)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.toolbar)).isDisplayed()
        onView(withId(R.id.recyclerView)).isGone()
        onView(withId(R.id.alertView)).isVisible()
        onView(withId(R.id.alertView)).hasDescendantWithText("Ocorreu um erro inesperado, tente novamente mais tarde.")
        onView(withId(R.id.alertView)).hasDescendantWithText("Atenção")
        onView(withText("Ok, Entendi!")).perform(ViewActions.click())
    }

    @Test
    fun testItemsIsDisplayed() {
        onView(withItemTextInContent("marksteve", R.id.recyclerView)).isDisplayed()
        onView(withItemTextInContent("fatihwk", R.id.recyclerView)).isDisplayed()
    }

    @Test
    fun testWithTextItem() {
        onView(withId(R.id.recyclerView))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(2))
            .check(matches(atPositionOnView(2, withText("Criado quarta-feira, 04 de setembro de 2019"), R.id.item_pull_date)))

        onView(withId(R.id.recyclerView))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(0))
            .check(matches(atPositionOnView(0, withText("Add audience parameter to token request for Auth0 support"), R.id.item_pull_name)))

    }
}