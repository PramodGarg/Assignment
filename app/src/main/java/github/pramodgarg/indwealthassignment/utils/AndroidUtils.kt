package github.pramodgarg.indwealthassignment.utils

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


fun View.gone() {
    this.visibility = View.GONE
}

fun View.isGone()  = this.visibility == View.GONE

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToParent: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToParent)
}

