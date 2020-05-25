package com.api.github.presentation.component

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.Gravity
import com.api.github.R

internal class TextViewVectorDrawable @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        var drawable: Drawable? = null
        val defaultColor = ContextCompat.getColor(context, android.R.color.background_dark)

        attrs?.let {
            val array = context.obtainStyledAttributes(it, R.styleable.TextViewVectorDrawable)
            val drawableRes =
                array.getResourceId(R.styleable.TextViewVectorDrawable_drawableStartCompact, -1)
            val color = array.getColor(
                R.styleable.TextViewVectorDrawable_drawableStartTint,
                defaultColor
            )

            array.recycle()

            if (drawableRes != -1) {
                drawable = AppCompatResources.getDrawable(context, drawableRes)
                drawable?.apply { DrawableCompat.setTint(this, color) }
            }

            gravity = Gravity.CENTER_VERTICAL
            setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
        }

    }

}