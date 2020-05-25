package com.api.github.extensions

import android.app.Activity
import android.view.View
import android.widget.Button
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController

@RunWith(RobolectricTestRunner::class)
internal class ViewExtensionsTest : AutoCloseKoinTest() {

    private lateinit var activityController: ActivityController<Activity>

    @Before
    fun setupTest() {
        activityController = Robolectric.buildActivity(Activity::class.java)
    }

    @Test
    fun `Assert show(false)`() {
        //Given
        val activity = activityController.get()
        val viewTest = Button(activity)
        activity.setContentView(viewTest)
        viewTest.visibility = View.VISIBLE

        //When
        viewTest.show(false)

        //Then
        assertEquals(View.GONE, viewTest.visibility)
    }

    @Test
    fun `Assert show(true)`() {
        //Given
        val activity = activityController.get()
        val viewTest = Button(activity)
        activity.setContentView(viewTest)
        viewTest.visibility = View.GONE

        //When
        viewTest.show(true)

        //Then
        assertEquals(View.VISIBLE, viewTest.visibility)
    }
}