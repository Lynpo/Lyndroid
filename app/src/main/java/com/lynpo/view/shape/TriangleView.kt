package com.lynpo.view.shape

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import com.lynpo.R
import java.lang.Math.PI
import kotlin.math.cos


/**
 * [TriangleView]
 * 等边直角三角形箭头，顶点为圆弧形，用作 Tip/Bubble 视图的指向箭头
 **
 * Create by fujw on 2022/5/30, 星期一.
 */
class TriangleView constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    private val colorRes: Int = R.color.colorAccent,
    private val direction: DuiConfigs.Direction = DuiConfigs.Direction.DOWN
) : View(mContext, attrs, defStyle) {
    val path: Path = Path()
    val p: Paint = Paint()
    private val rectF: RectF = RectF()

    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0, R.color.colorAccent, DuiConfigs.Direction.DOWN)

    constructor(context: Context, @ColorRes colorRes: Int): this(context, DuiConfigs.Direction.DOWN, colorRes)
    constructor(context: Context, direction: DuiConfigs.Direction, @ColorRes colorRes: Int): this(context, null, 0, colorRes, direction)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val pi = PI
        // 等边直角三角形
        // 底边长：
        val bottomLineLen = (if (direction.isHorizontal()) width else height).toFloat()
        // 高，底边一半长度：用于绘制顶点坐标
        val h = bottomLineLen / 2f
        // 顶部圆弧宽度，固定值 2dp，不超过底边长
        val arcWidth = context.resources.getDimensionPixelSize(R.dimen.dp_2).toFloat().coerceAtMost(bottomLineLen)
        // 顶部圆弧半径：
        val r = arcWidth / 2f / cos(pi / 4).toFloat()
        // 圆弧顶点，与三角形顶点距离
        val arcTriangleTopDistance = arcWidth - r

        when (direction) {
            DuiConfigs.Direction.DOWN -> {
                path.moveTo(0f, 0f)
                path.lineTo(bottomLineLen, 0f)
                path.lineTo(bottomLineLen / 2f + arcWidth / 2f, h - (arcWidth / 2f))
                rectF.set(bottomLineLen / 2f - r,
                    h - arcTriangleTopDistance - 2 * r,
                    bottomLineLen / 2f + r,
                    h - arcTriangleTopDistance)
                path.arcTo(
                    rectF, 45f, 90f
                )
            }
            DuiConfigs.Direction.LEFT -> {
                path.moveTo(h, 0f)
                path.lineTo(h, bottomLineLen)
                path.lineTo(arcWidth / 2f, bottomLineLen / 2f + arcWidth / 2f)
                rectF.set(arcTriangleTopDistance,
                    bottomLineLen / 2f - r,
                    arcTriangleTopDistance + 2 * r,
                    bottomLineLen / 2f + r)
                path.arcTo(
                    rectF, 135f, 90f
                )
            }
            DuiConfigs.Direction.UP -> {
                path.moveTo(bottomLineLen, h)
                path.lineTo(0f, h)
                path.lineTo(bottomLineLen / 2f - arcWidth / 2f, arcWidth / 2f)
                rectF.set(bottomLineLen / 2f - r,
                    arcTriangleTopDistance,
                    bottomLineLen / 2f + r,
                    arcTriangleTopDistance + 2 * r)
                path.arcTo(
                    rectF, 225f, 90f
                )
            }
            DuiConfigs.Direction.RIGHT -> {
                path.moveTo(0f, bottomLineLen)
                path.lineTo(0f, 0f)
                path.lineTo(h - (arcWidth / 2f), bottomLineLen / 2f - arcWidth / 2f)
                rectF.set(h - arcTriangleTopDistance - 2 * r,
                    bottomLineLen / 2f - r,
                    h - arcTriangleTopDistance,
                    bottomLineLen / 2f + r)
                path.arcTo(
                    rectF, 315f, 90f
                )
            }
        }
        path.close()
        p.color = mContext.getColor(colorRes)
        canvas.drawPath(path, p)
    }
}