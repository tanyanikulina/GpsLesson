package com.example.gpslesson.extensions

import android.animation.ValueAnimator
import android.view.View

fun View.animateScale(fromValue: Float = 1f, toValue:Float = 1.5f, repeatCount: Int = ValueAnimator.INFINITE) {
    val anim = ValueAnimator.ofFloat(fromValue, toValue)
    anim.duration = 1000
    anim.addUpdateListener { animation ->
        scaleX = animation.animatedValue as Float
        scaleY = animation.animatedValue as Float
    }
    anim.repeatCount = repeatCount
    anim.repeatMode = ValueAnimator.REVERSE
    anim.start()
}