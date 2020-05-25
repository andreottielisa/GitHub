package com.api.github.presentation.component

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.api.github.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AlertViewTest : AutoCloseKoinTest() {

    private lateinit var activity: Activity
    private lateinit var viewTest: AlertView

    @Before
    fun setupTest() {
        activity = Robolectric.buildActivity(Activity::class.java).get()
        viewTest = AlertView(activity)
    }

    @Test
    fun `Assert setIcon`() {
        viewTest.setIcon(R.drawable.ic_error_red_24dp)
        val view = viewTest.findViewById<ImageView>(R.id.iconAlertView)
        assertNotEquals(0, view.background)
    }

    @Test
    fun `Assert setDescription`() {
        viewTest.setDescription(R.string.avatar_description)
        val view = viewTest.findViewById<TextView>(R.id.descriptionAttention)
        assertEquals(activity.getString(R.string.avatar_description), view.text.toString())
    }

    @Test
    fun `Assert setActionButton`() {
        viewTest.setActionButton {
            assert(true)
        }
        val view = viewTest.findViewById<View>(R.id.buttonAttention)

        assertTrue(view.hasOnClickListeners())
        assertTrue(view.performClick())
    }

    @Test
    fun `Assert setVisibility`() {
        viewTest.visibility = View.GONE
        assertEquals(View.GONE, viewTest.visibility)

        viewTest.visibility = View.VISIBLE
        assertEquals(View.VISIBLE, viewTest.visibility)
    }
}