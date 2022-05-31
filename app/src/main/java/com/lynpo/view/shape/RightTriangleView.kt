package com.lynpo.view.shape

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.lynpo.R
import java.lang.Math.PI
import kotlin.math.cos


/**
 * [RightTriangleView]
 * 直角三角形
 **
 * Create by fujw on 2022/5/30, 星期一.
 */
class RightTriangleView constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {
    var path: Path = Path()
    var p: Paint = Paint()
    private var rectF: RectF = RectF()

    constructor(context: Context?): this(context, null)
    constructor(context: Context?, attrs: AttributeSet?): this(context, attrs, 0)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val arcWidth = context.resources.getDimensionPixelSize(R.dimen.dp_6).coerceAtMost(width)
        val pi = PI
        // 底边长：
        val lineLen = width
        // 高：
        val h = lineLen / 2
        // 三角形朝下：底边（在上）距离顶部距离
//        val topMargin = (lineLen - h) / 2
        // 三角形朝下：底边纵坐标，顶点坐标坐标
        val w = width
        // 三角形顶点纵坐标：
        val yTop = h.toFloat()
        // 圆弧半径：
        val r = arcWidth / 2 / cos(pi / 4)
        // 包含目标圆弧的圆，其下顶点，与三角形顶点距离：
//        val recLen = arcWidth / 2 * tan(pi / 4) + arcWidth * cos(pi / 4) - r
        val recLen = arcWidth - r
//        val h = height.toFloat()
//        val maxHeight = w * tan(Math.PI / 4).toFloat() / 2
//        h = h.coerceAtMost(maxHeight)
        path.moveTo(0f, 0f)
        path.lineTo(w.toFloat(), 0f)
        path.lineTo(w / 2f + arcWidth / 2f, yTop - (arcWidth / 2f))
//        path.lineTo(w / 2f + 2, h - 2)
//        path.arcTo(RectF(w / 2f - 20, h - 40, w / 2f + 20, h), 0f, 180f)
        rectF.set(w / 2f - r.toFloat(),
            yTop - recLen.toFloat() - 2 * r.toFloat(),
            w / 2f + r.toFloat(),
            yTop - recLen.toFloat())
        path.arcTo(
            rectF, 45f, 90f
        )
        path.close()
        p.color = Color.RED
        canvas.drawPath(path, p)
    }
}