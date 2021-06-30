package com.endrollmodel.notesample.util

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics

object DisplayManager {
    fun getScreenPixel(context: Context): DisplayMetrics {
        val metrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(metrics)
        return metrics
    }
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun getStatusHeight(context: Context): Int {
        // 取得狀態列高度
        var statusBarHeight = -1
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            //根據資源ID獲取響應的尺寸值
            statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }
}