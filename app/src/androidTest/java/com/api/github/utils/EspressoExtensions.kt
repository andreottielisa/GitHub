package com.api.github.utils

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers

internal fun AppCompatActivity.getFragment(@IdRes idRes: Int) =
    supportFragmentManager.findFragmentById(idRes)

internal fun ViewInteraction.hasDescendantWithText(text: String) =
    check(matches(hasDescendant(withText(text))))

internal fun ViewInteraction.anything() =
    check(matches(CoreMatchers.anything()))

internal fun ViewInteraction.isDisplayed() =
    check(matches(ViewMatchers.isDisplayed()))

internal fun ViewInteraction.isGone() =
    getViewAssertion(Visibility.GONE)

internal fun ViewInteraction.isVisible() =
    getViewAssertion(Visibility.VISIBLE)

private fun getViewAssertion(visibility: Visibility): ViewAssertion? =
    matches(withEffectiveVisibility(visibility))