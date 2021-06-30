package com.endrollmodel.notesample.util

import android.content.Context
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat


open class ThemeUtil{

    /**
     * 轉換為色碼
     *  @param color 色碼Int
     */
    fun colorToHex(context: Context, color: Int) : String{
        return "#" + Integer.toHexString(ContextCompat.getColor(context, color))
    }
    /**
     * 圓角
     * @param color 顏色 (需使用 color.parse 或是 Resources.getColor轉換)
     * @param topL 左上圓角角度
     * @param topR 右上圓角角度
     * @param bottomL 左下圓角角度
     * @param bottomR 右下圓角角度
     */
    fun gradientStyle(color: Int, topL: Int, topR: Int, bottomL: Int, bottomR: Int) : GradientDrawable{
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(color)
        gradientDrawable.cornerRadii = floatArrayOf(topL.toFloat(), topR.toFloat(), 0f, 0f, 0f, 0f, bottomL.toFloat(), bottomR.toFloat())
        return gradientDrawable
    }

    /**
     * 圓角
     * @param color 顏色
     * @param radius 四邊圓角角度
     */
    fun gradientStyle(color: Int, radius : Int) : GradientDrawable{
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(color)
        gradientDrawable.cornerRadius = radius.toFloat()
        return gradientDrawable
    }
}