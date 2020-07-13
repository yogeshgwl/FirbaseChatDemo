package com.aucto.core

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)

fun ImageView.loadImage(url: String) = Glide.with(this).load(url).into(this)

fun ImageView.loadImageRound(url: String) =
    Glide.with(this).load(url).apply(RequestOptions.circleCropTransform()).into(this)

fun View.setKeyboardShown(context: Context, shown: Boolean) {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (shown) {
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    } else {
        inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
    }
}

fun View.showSnackbar(message: String) {
    val snackbar = Snackbar.make(
        this,
        Html.fromHtml("<font color=\"#e9e9e9\">$message</font>"),
        Snackbar.LENGTH_SHORT
    )
    snackbar.show()
}
fun AppCompatActivity.setupToolbar(toolbar: Toolbar, darkIcons: Boolean = true,
                                         enableUpButton: Boolean = true,
                                         @StringRes resTitleId: Int? = null) {
    val title = if (resTitleId != null) getString(resTitleId) else ""
    setupToolbarWithTitle(toolbar, darkIcons, enableUpButton, title)
}

fun AppCompatActivity.setupToolbarWithTitle(toolbar: Toolbar, darkIcons: Boolean = true,
                                                  enableUpButton: Boolean = true, title: String? = null) {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(enableUpButton)
    val indicator = if (darkIcons) R.drawable.ic_baseline_black else R.drawable.ic_baseline_white
    supportActionBar?.setHomeAsUpIndicator(indicator)
    supportActionBar?.title = title ?: ""
}