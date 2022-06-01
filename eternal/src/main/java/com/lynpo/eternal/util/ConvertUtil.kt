package com.lynpo.eternal.util

import android.content.Context

object ConvertUtil {
    /**
     * dp 转 px
     *
     * @param context 上下文
     * @param dp      dp 值
     * @return px 值
     */
    fun dp2px(context: Context?, dp: Float): Int {
        val scale = context?.resources?.displayMetrics?.density?: 0f
        return (dp * scale + 0.5f).toInt()
    }

    fun sp2px(context: Context?, spValue: Float): Int {
        val fontScale = context?.resources?.displayMetrics?.scaledDensity?: 0f
        return (spValue * fontScale + 0.5f).toInt()
    }

    fun getWidth(context: Context): Int {
        val resources = context.resources
        val dm = resources.displayMetrics
        val density1 = dm.density
        return dm.widthPixels
    }

    fun getHeight(context: Context): Int {
        val resources = context.resources
        val dm = resources.displayMetrics
        val density1 = dm.density
        return dm.heightPixels
    }

    /**
     * wjy dp转换成px
     */
    fun dp2px(dp: Float, context: Context): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}