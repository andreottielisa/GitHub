package com.api.github.presentation.fragment

import android.view.View
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToHolder
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.api.github.R
import com.api.github.presentation.adapter.ListRepoAdapter
import com.api.github.utils.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.any
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Isolated Test Fragment
 */
@RunWith(AndroidJUnit4::class)
class ListRepoFragmentTest {

    private lateinit var scenario: FragmentScenario<ListRepoFragment>

    @Before
    fun setupTest(){
        scenario = launchFragmentInContainer<ListRepoFragment>(themeResId = R.style.AppTheme)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testIsRecyclerViewVisibleInAppLaunch() {
        onView(withId(R.id.toolbar)).isDisplayed()
        onView(withId(R.id.recyclerView)).isDisplayed()
        onView(withId(R.id.progressBar)).isGone()
    }

    @Test
    fun testItemsIsDisplayed() {
        onView(withItemTextInContent("golang", R.id.recyclerView)).isDisplayed()
        onView(withItemTextInContent("go", R.id.recyclerView)).isDisplayed()
    }

    @Test(expected = Exception::class)
    fun testLoadIsDisplayedPositionOutOfRange() {
        onView(withId(R.id.recyclerView))
            .perform(
                scrollToHolder(ViewHolderLoadingMatcher())
                    .atPosition(10000)
            )
    }

    @Test
    fun testEndScrollLoad() {
        val scenario = launchFragmentInContainer<ListRepoFragment>(themeResId = R.style.AppTheme)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.recyclerView))
            .perform(scrollToHolder(ViewHolderLoadingMatcher()))
            .anything()

        onView(withItemTextInContent("consul", R.id.recyclerView)).isDisplayed()
    }

    @Test
    fun testContentDescriptionItem() {
        onView(withId(R.id.recyclerView))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(21))
            .check(ViewAssertions.matches(atPositionOnView(21,
                withContentDescription("Quantidade de forks: 4291"), R.id.item_num_forks)))

        onView(withId(R.id.recyclerView))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(5))
            .check(ViewAssertions.matches(atPositionOnView(5,
                withContentDescription("Quantidade de estrelas: 38367"), R.id.item_num_starts)))

        onView(withId(R.id.recyclerView))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(30))
            .check(ViewAssertions.matches(atPositionOnView(30,
                withContentDescription("Foto do usuário"), R.id.item_user_avatar)))

    }

    inner class ViewHolderLoadingMatcher : TypeSafeMatcher<RecyclerView.ViewHolder?> {
        private var itemMatcher: Matcher<View?> = any(View::class.java)

        constructor() {}
        constructor(itemMatcher: Matcher<View?>) {
            this.itemMatcher = itemMatcher
        }

        override fun matchesSafely(viewHolder: RecyclerView.ViewHolder?): Boolean {
            viewHolder ?: return false

            return (ListRepoAdapter.ViewHolderLoading::class.java.isAssignableFrom(viewHolder.javaClass)
                    && itemMatcher.matches(viewHolder.itemView))
        }

        override fun describeTo(description: Description?) {
            description?.appendText("is assignable from ViewHolderLoading")
        }
    }
}