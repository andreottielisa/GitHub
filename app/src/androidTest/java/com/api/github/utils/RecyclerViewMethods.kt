/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.api.github.utils

import android.text.TextUtils
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.core.util.Preconditions
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher

internal fun atPositionOnView(
    position: Int, itemMatcher: Matcher<View?>,
    @NonNull @IdRes targetViewId: Int
): Matcher<View?>? {
    return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has view id $itemMatcher at position $position")
        }

        override fun matchesSafely(recyclerView: RecyclerView): Boolean {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
            viewHolder?.itemView ?: return false
            val targetView = viewHolder.itemView.findViewById<View>(targetViewId)
            return itemMatcher.matches(targetView)
        }
    }
}

internal fun withItemTextInContent(itemText: String, @IdRes idContent: Int): Matcher<View?>? {
    Preconditions.checkArgument(!TextUtils.isEmpty(itemText), "itemText cannot be null or empty")
    return object : TypeSafeMatcher<View?>() {
        override fun matchesSafely(item: View?): Boolean {
            return allOf(
                isDescendantOfA(withId(idContent)),
                withText(itemText)
            ).matches(item)
        }

        override fun describeTo(description: Description) {
            description.appendText("is isDescendantOfA RecyclerView with text $itemText")
        }
    }
}