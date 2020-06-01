package com.example.android.lifequotes;

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.TranslateAnimation

object AnimationHelper {
    fun animate(holderItemView:View){

        // defining animation
        // val anim=AlphaAnimation(0.0f,1.0f)
        // anim.duration=1000

        val translate = TranslateAnimation(0.0f,0.0f,-100f,0.0f)
        translate.duration = 1000

        //assigning animation
        holderItemView.startAnimation(translate)
    }
}