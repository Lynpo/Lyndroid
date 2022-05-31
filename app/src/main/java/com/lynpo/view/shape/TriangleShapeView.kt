package com.lynpo.view.shape

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.lynpo.R
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

/**
 * TriangleShapeView
 * *
 * Create by fujw on 2019-05-31.
 */
class TriangleShapeView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {
    lateinit var path: Path
    lateinit var p: Paint
    private fun init() {
        path = Path()
        p = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val arcWidth = context.resources.getDimensionPixelSize(R.dimen.dp_8).coerceAtMost(width)
        val pi = Math.PI
        // 边长：
        val lineLen = width
        // 高：
        val h = lineLen * sin(Math.PI / 3)
        // 三角形朝下：底边（在上）距离顶部距离
        val topMargin = (lineLen - h) / 2
        // 三角形朝下：底边纵坐标，顶点坐标坐标
        val w = width
        // 三角形定点纵坐标：
//        val yTop = height - topMargin.toFloat()
        val yTop = h.toFloat()
        // 圆弧半径：
        val r = arcWidth / 2 / cos(Math.PI / 6)
        // 包括包含目标圆弧的原型的矩形（正方形）的定点距离三角形点点距离：
//        val recLen = arcWidth / 2 * tan(pi / 6) + arcWidth * cos(pi / 6) - r
        val recLen = arcWidth / 2 * tan(pi / 6) + arcWidth / 2 * tan(pi / 3) - r
//        val h = height.toFloat()
//        val maxHeight = w * tan(Math.PI / 4).toFloat() / 2
//        h = h.coerceAtMost(maxHeight)
        path.moveTo(0f, 0f)
        path.lineTo(w.toFloat(), 0f)
        path.lineTo(w / 2f, yTop)
//        path.lineTo(w / 2f + 2, h - 2)
//        path.arcTo(RectF(w / 2f - 20, h - 40, w / 2f + 20, h), 0f, 180f)
        path.arcTo(
            RectF(
                w / 2f - r.toFloat(),
                yTop - recLen.toFloat() - 2 * r.toFloat(),
                w / 2f + r.toFloat(),
                yTop - recLen.toFloat()
            ), 30f, 120f
        )
        path.close()
        p.color = Color.RED
        canvas.drawPath(path, p)
    }

    init {
        init()
    }
}