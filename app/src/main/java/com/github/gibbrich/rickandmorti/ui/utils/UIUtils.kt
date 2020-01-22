package com.github.gibbrich.rickandmorti.ui.utils

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.github.gibbrich.rickandmorti.R
import com.google.android.material.snackbar.Snackbar

fun Context.getColorCompat(@ColorRes id: Int) = ContextCompat.getColor(this, id)

fun showError(root: View, @StringRes message: Int) {
    Snackbar
        .make(root, message, Snackbar.LENGTH_LONG)
        .decorate(R.color.snack_background_color)
        .show()
}

fun Snackbar.decorate(
    @ColorRes backgroundId: Int,
    @ColorRes textColorId: Int = android.R.color.white,
    @ColorRes actionColor: Int = android.R.color.white
): Snackbar {
    val layout: Snackbar.SnackbarLayout = view as Snackbar.SnackbarLayout
    layout.setBackgroundColor(context.getColorCompat(backgroundId))

    val textView = layout.findViewById<TextView>(R.id.snackbar_text)

    textView.setTextColor(context.getColorCompat(textColorId))

    textView.typeface = android.graphics.Typeface.SANS_SERIF
    textView.maxLines = Int.MAX_VALUE
    textView.ellipsize = null

    val action = layout.findViewById<TextView>(R.id.snackbar_action)
    action.setTextColor(context.getColorCompat(actionColor))
    action.typeface = android.graphics.Typeface.SANS_SERIF
    return this
}

fun createCircularProgressDrawable(context: Context) = CircularProgressDrawable(context).apply {
    strokeWidth = 5f
    centerRadius = 30f
    start()
}