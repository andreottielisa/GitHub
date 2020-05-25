package com.api.github.extensions

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.api.github.R

internal fun AppCompatActivity.setActionBar(
    topBar: Toolbar?, displayHomeAsUp: Boolean
) {
    setSupportActionBar(topBar)
    supportActionBar?.setDisplayHomeAsUpEnabled(displayHomeAsUp)
    supportActionBar?.setHomeActionContentDescription(R.string.back_description)
}

internal fun FragmentActivity.navigateTo(
    @IdRes containerIdRes: Int,
    fragment: Class<out Fragment>,
    tag: String? = null,
    addToBackStack: Boolean = true,
    args: Bundle? = null
) {
    supportFragmentManager.inTransaction(tag, addToBackStack) {
        replace(containerIdRes, fragment, args)
    }
}

internal fun FragmentManager.setupForAccessibility() {
    addOnBackStackChangedListener {
        val lastFragmentWithView = fragments.lastOrNull { it.view != null }
        for (fragment in fragments) {
            if (fragment == lastFragmentWithView) {
                fragment.view?.importantForAccessibility =
                    View.IMPORTANT_FOR_ACCESSIBILITY_YES
            } else {
                fragment.view?.importantForAccessibility =
                    View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
            }
        }
    }
}

internal fun FragmentManager.inTransaction(
    tag: String? = null,
    addToBackStack: Boolean = true,
    func: FragmentTransaction. () -> FragmentTransaction
) {
    with(beginTransaction()) {
        if (addToBackStack)
            addToBackStack(tag)
        func().commit()
    }
    setupForAccessibility()
}