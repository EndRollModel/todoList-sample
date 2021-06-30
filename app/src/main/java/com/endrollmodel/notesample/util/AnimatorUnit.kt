package com.endrollmodel.notesample.util

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.view.View

object AnimatorUnit {
    fun setChangerColorAni(
        currentColor: Int,
        newColor: Int,
        view: View?,
        duration: Long
    ): ObjectAnimator {
        val animator = ObjectAnimator.ofObject(
            view,
            "backgroundColor",
            ArgbEvaluator(),
            currentColor,
            newColor
        )
        animator.duration = duration
        return animator
    }
}