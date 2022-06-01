package com.lynpo.view.shape

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.lynpo.R
import com.lynpo.eternal.LynConstants
import com.lynpo.eternal.extend.*


/**
 * [BubbleLayout]
 * 气泡控件
 **
 * Create by fujw on 2022/5/30, 星期一.
 */
class BubbleLayout private constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    private val direction: DuiConfigs.Direction = DuiConfigs.Direction.DOWN
) : ConstraintLayout(mContext, attrs, defStyle) {

    private constructor(context: Context) : this(context, null)
    private constructor(context: Context, direction: DuiConfigs.Direction) : this(context, null, direction)
    private constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    private constructor(context: Context, attrs: AttributeSet?, direction: DuiConfigs.Direction) : this(context, attrs, 0, direction) {
//        inflate(context, R.layout.drugs_comm_layout_bubble, this)
    }

    fun setBubbleView(bubbleView: View, paddingHorizontal: Int = (8), paddingVertical: Int = (6)): BubbleLayout {
//        content_container.addView(bubbleView)
        return this
    }

    fun setBubbleDirection(direction: DuiConfigs.Direction = DuiConfigs.Direction.DOWN): BubbleLayout {
//        val triangleView = TriangleView(mContext, direction)
//        val lp = if (direction.isHorizontal())
//            LayoutParams(dp2Pixel(8), dp2Pixel(4))
//        else LayoutParams(dp2Pixel(4), dp2Pixel(8))
//        bubble_layout.addView(triangleView)
        when (direction) {
            DuiConfigs.Direction.DOWN -> {

            }
            DuiConfigs.Direction.UP -> {}
            DuiConfigs.Direction.LEFT -> {}
            DuiConfigs.Direction.RIGHT -> {}
        }
        return this
    }

    companion object {
        fun create(context: Context): Builder = Builder(context)
        fun create(context: Context, direction: DuiConfigs.Direction): Builder = Builder(context, direction)
    }

    class Builder(private val mContext: Context, private val direction: DuiConfigs.Direction = DuiConfigs.Direction.DOWN) {
        private var bubbleView: View? = null
        private var paddingHorizontal: Int = 0
        private var paddingVertical: Int = 0
        private var cornerRadius: Int = 0
        private var arrowOffsetLeft: Int = 0
        private var arrowOffsetRight: Int = 0
        private var arrowOffsetTop: Int = 0
        private var arrowOffsetBottom: Int = 0
        private var colorResId: Int = R.color.colorAccent

        fun setBubbleView(bubbleView: View): Builder {
            this.bubbleView = bubbleView
            return this
        }

        fun setBubbleView(bubbleView: View, paddingHorizontal: Int = dp2Pixel(8), paddingVertical: Int = dp2Pixel(6)): Builder {
            this.bubbleView = bubbleView
            this.paddingHorizontal = paddingHorizontal
            this.paddingVertical = paddingVertical
            return this
        }

        fun setBubbleCornerRadius(cornerRadius: Int): Builder {
            this.cornerRadius = cornerRadius
            return this
        }

        /**
         * 箭头水平方向偏移，只能设置一个值，左偏移量，或右偏移量，默认居中
         * [arrowLeftOffset] [arrowRightOffset] 只能设置一个，默认居中
         */
        fun setBubbleArrowHorizontalOffset(arrowLeftOffset: Int = 0, arrowRightOffset: Int = 0): Builder {
            this.arrowOffsetLeft = arrowLeftOffset
            this.arrowOffsetRight = arrowRightOffset
            return this
        }

        /**
         * 箭头竖直方向偏移，只能设置一个值，上偏移量，或下偏移量，默认居中
         * [arrowTopOffset] [arrowBottomOffset] 只能设置一个，默认居中
         */
        fun setBubbleArrowVerticalOffset(arrowTopOffset: Int = 0, arrowBottomOffset: Int = 0): Builder {
            this.arrowOffsetTop = arrowTopOffset
            this.arrowOffsetBottom = arrowBottomOffset
            return this
        }

        fun setBubbleColorRes(@ColorRes color: Int): Builder {
            this.colorResId = color
            return this
        }

        fun build(): BubbleLayout {
            val bubbleArrowId = View.generateViewId()
            val bubbleArrow = TriangleView(mContext, direction, colorResId).id(bubbleArrowId)
            val lpBubbleArrow = if (direction.isHorizontal())
                LayoutParams(dp2Pixel(10), dp2Pixel(20))
            else LayoutParams(dp2Pixel(20), dp2Pixel(10))

            val lpBubbleLayout = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            if (direction.isHorizontal()) {
                lpBubbleLayout.topBottomParent()
            } else {
                lpBubbleLayout.startEndParent()
            }

            val bubbleLayoutId = View.generateViewId()
            val bubbleLayout = ConstraintLayout(mContext)
            bubbleLayout.paddingByDirection(paddingHorizontal, topBottom = paddingVertical)
                .bgShapeRectangleDrawableColorRes(colorResId, cornerRadius)
                .id(bubbleLayoutId)
            val bubbleViewLp = bubbleView.startConstraintWrapContent().topBottomParent()
                .startEndParent()
            bubbleLayout.addView(bubbleView, bubbleViewLp)

            Log.d(LynConstants.LOG_TAG, "BubbleLayout direction: ${direction.name}")
            when (direction) {
                DuiConfigs.Direction.DOWN -> {
                    lpBubbleLayout.topParent()
                    lpBubbleArrow.topId(bubbleLayoutId)
                    if(arrowOffsetLeft > 0) {
                        lpBubbleArrow.startParent()
                            .marginsStartEnd(arrowOffsetLeft, 0)
                    } else if(arrowOffsetRight > 0) {
                        lpBubbleArrow.endParent()
                            .marginsStartEnd(0, arrowOffsetRight)
                    } else {
                        lpBubbleArrow.startEndParent()
                    }
                }
                DuiConfigs.Direction.LEFT -> {
                    lpBubbleArrow.startParent()
                    lpBubbleLayout.startId(bubbleArrowId)
                    if(arrowOffsetTop > 0) {
                        lpBubbleArrow.topParent()
                            .marginsTopBottom(arrowOffsetTop, 0)
                    } else if(arrowOffsetBottom > 0) {
                        lpBubbleArrow.bottomParent()
                            .marginsTopBottom(0, arrowOffsetBottom)
                    } else {
                        lpBubbleArrow.topBottomParent()
                    }
                }
                DuiConfigs.Direction.UP -> {
                    lpBubbleArrow.topParent()
                    lpBubbleLayout.topId(bubbleArrowId)
                    if(arrowOffsetLeft > 0) {
                        lpBubbleArrow.startParent()
                            .marginsStartEnd(arrowOffsetLeft, 0)
                    } else if(arrowOffsetRight > 0) {
                        lpBubbleArrow.endParent()
                            .marginsStartEnd(0, arrowOffsetRight)
                    } else {
                        lpBubbleArrow.startEndParent()
                    }
                }
                DuiConfigs.Direction.RIGHT -> {
                    lpBubbleLayout.startParent()
                    lpBubbleArrow.startId(bubbleLayoutId)
                    if(arrowOffsetTop > 0) {
                        lpBubbleArrow.topParent()
                            .marginsTopBottom(arrowOffsetTop, 0)
                    } else if(arrowOffsetBottom > 0) {
                        lpBubbleArrow.bottomParent()
                            .marginsTopBottom(0, arrowOffsetBottom)
                    } else {
                        lpBubbleArrow.topBottomParent()
                    }
                }
            }

            return BubbleLayout(mContext).apply {
                addView(bubbleLayout, lpBubbleLayout)
                addView(bubbleArrow, lpBubbleArrow)
            }
        }

        private fun dp2Pixel(dp: Int): Int {
            return mContext.dp2Pixel(dp)
        }
    }
}