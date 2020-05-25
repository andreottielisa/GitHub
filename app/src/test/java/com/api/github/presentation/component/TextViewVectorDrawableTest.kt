package com.api.github.presentation.component

import android.app.Activity
import com.api.github.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TextViewVectorDrawableTest : AutoCloseKoinTest() {

    private lateinit var activity: Activity

    @Before
    fun setupTest() {
        activity = Robolectric.buildActivity(Activity::class.java).get()
    }

    @Test
    fun `Assert drawableStartCompact`() {
        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.drawableStartCompact, "@drawable/ic_code_black_24dp")
            .addAttribute(R.attr.drawableStartTint, "@color/colorAccent")
            .build()

        val viewTest = TextViewVectorDrawable(activity, attrs, 0)

        Assert.assertNotEquals(0, viewTest.compoundPaddingStart)
    }

    @Test
    fun `Assert is not add drawableStartCompact`() {
        val viewTest = TextViewVectorDrawable(activity)

        Assert.assertEquals(0, viewTest.compoundPaddingStart)
    }
}