package com.api.github.presentation.component

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import com.api.github.R
import kotlinx.android.synthetic.main.layout_alert_view.view.*

internal class AlertView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.layout_alert_view, this)
    }

    fun setIcon(@DrawableRes icon: Int){
        iconAlertView?.setImageResource(icon)
    }

    fun setDescription(@StringRes description: Int) {
        descriptionAttention?.setText(description)
    }

    fun setActionButton(action: () -> Unit) {
        buttonAttention?.setOnClickListener {
            action()
        }
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (visibility == View.VISIBLE){
            ViewCompat.setAccessibilityLiveRegion(this, ViewCompat.ACCESSIBILITY_LIVE_REGION_POLITE)
        }
    }
}