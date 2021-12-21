package com.example.bestmovie2.view

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.showSnackBar(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    lenght: Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar.make(this, text, lenght).setAction(actionText, action).show()
}