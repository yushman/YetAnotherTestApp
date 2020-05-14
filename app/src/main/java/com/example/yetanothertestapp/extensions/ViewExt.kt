package com.example.yetanothertestapp.extensions

import android.view.View

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun View.setVisible(isVisible: Boolean){
    if (isVisible) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}