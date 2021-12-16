package com.example.prodemo.base

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.prodemo.R
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun View.makeSnack(message: String): Snackbar {
    hideKeyboard()
    val snack = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    snack.view.setBackgroundColor(ContextCompat.getColor(this.context, R.color.white))
    val textView = snack.view.findViewById(R.id.snackbar_text) as TextView
    textView.maxLines = 5
    textView.setTextColor(
        ContextCompat.getColor(
            this.context,
            R.color.design_default_color_primary_dark
        )
    )
    snack.show()
    return snack
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(rootView.windowToken, 0)
}

fun ImageView.loadImageCircle(url: String?) {
    val requestOptions = RequestOptions().transform(CenterCrop(), CircleCrop())
    Glide.with(context).load(url ?: "").apply(requestOptions).into(this)
}
fun Disposable.autoDispose(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}