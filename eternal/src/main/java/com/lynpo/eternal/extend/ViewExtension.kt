package com.lynpo.eternal.extend

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.SystemClock
import android.text.*
import android.text.method.ScrollingMovementMethod
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.lynpo.eternal.R
import com.lynpo.eternal.util.ConvertUtil

/**
 * @Author ls
 * @Date  2020/12/14
 * @Description
 * @Version
 */

fun View?.hideSoftKeyboard() {
    this?.let { view ->
        (view.context?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as? InputMethodManager)
            ?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}


fun View?.dp2px(dp: Int): Int = dp2px(dp.toFloat())
fun View?.dp2px(dp: Float): Int = this?.context?.let { ConvertUtil.dp2px(it, dp) } ?: 0


fun View?.bg(@DrawableRes drawableResId: Int): View? {
    this?.context?.let {
        this.background = if (id != 0) ContextCompat.getDrawable(it, drawableResId) else null
    }
    return this
}

fun View?.bgColor(@ColorRes id: Int): View? {
    this?.context?.let {
        this.setBackgroundColor(if (id != 0)ContextCompat.getColor(it, id) else 0)
    }
    return this
}

fun View?.gone(): View? {
    this?.visibility = View.GONE
    return this
}

fun View?.visible(): View? {
    this?.visibility = View.VISIBLE
    return this
}


fun View?.invisible(): View? {
    this?.visibility = View.INVISIBLE
    return this
}

fun View?.isVisible(): Boolean {
    return this?.visibility == View.VISIBLE
}

fun View?.notVisible(): Boolean {
    return this?.visibility != View.VISIBLE
}

fun View?.isGone(): Boolean {
    return this?.visibility == View.GONE
}

fun View?.show(show: Boolean): View? {
    if (show)this?.visible() else this?.gone()
    return this
}

fun View?.setVisible(show: Boolean): View? {
    if (show) this?.visible() else this?.invisible()
    return this
}

fun View?.showOrInvisible(show: Boolean): View? {
    if (show)this?.visible() else this?.invisible()
    return this
}

inline fun <T: View> T?.visibleThen(predicate: Boolean, block: (T?) -> Unit): T? {
    if (predicate) {
        this.visible()
        block(this)
    } else this.gone()
    return this
}

fun View?.enable(enable: Boolean = true): View? {
    this?.isEnabled = enable
    return this
}

fun View?.clickable(clickable: Boolean = true): View? {
    this?.isClickable = clickable
    return this
}

fun View?.id(id: Int): View? {
    this?.id = id
    return this
}

fun View?.tag(tag: Any? = null): View? {
    this?.tag = tag
    return this
}

fun View?.getTagInt(defaultValue: Int = 0): Int {
    return (this?.tag as? Int)?: defaultValue
}

fun View?.noPadding(): View? {
    this?.setPadding(0, 0, 0, 0)
    return this
}

fun View?.bottomPadding(bottom: Int): View? {
    this?.setPadding(0, 0, 0, bottom)
    return this
}

fun View?.topPadding(top: Int): View? {
    this?.setPadding(0, top, 0, 0)
    return this
}

fun View?.padding(left: Int, top: Int, right: Int, bottom: Int): View? {
    this?.setPadding(left, top, right, bottom)
    return this
}

fun View?.padding(padding: Int): View? {
    this?.setPadding(padding, padding, padding, padding)
    return this
}

fun View?.paddingByDirection(leftRight: Int, topBottom: Int): View? {
    this?.setPadding(leftRight, topBottom, leftRight, topBottom)
    return this
}

fun View?.paddingInDp(left: Float, top: Float, right: Float, bottom: Float): View? {
    this?.context?.aliveActivity()?.let { ctx ->
        val start = ctx.dp2Pixel(left)
        val end = if (right == left) start else ctx.dp2Pixel(right)
        val t = ctx.dp2Pixel(top)
        val b = if (bottom == top) t else ctx.dp2Pixel(bottom)
        padding(start, end, t, b)
    }
    return this
}

fun View?.paddingDpRes(@DimenRes left: Int, @DimenRes top: Int, @DimenRes right: Int, @DimenRes bottom: Int): View? {
    this?.context?.let { ctx ->
        val st = ctx.resources.getDimensionPixelSize(left)
        val en = if (left == right) st else ctx.resources.getDimensionPixelSize(left)
        val tp = ctx.resources.getDimensionPixelSize(top)
        val bt = if (top == bottom) tp else ctx.resources.getDimensionPixelSize(bottom)
        this.padding(st, tp, en, bt)
    }
    return this
}

fun View?.layoutParams(layoutParams: ViewGroup.LayoutParams): View? {
    this?.layoutParams = layoutParams
    return this
}

fun View?.getYOnScreen(): Int {
    return this?.let {
        val outLocation = IntArray(2)
        it.getLocationOnScreen(outLocation)
        outLocation[1]
    }?: 0
}

fun View?.isVisibleInYRangeOfScreen(topY: Float, bottomY: Float): Boolean {
    var y: Int
    return this.getYOnScreen().also { y = it } > topY && y < bottomY
}

fun View?.dispatchTouchEventOnView(action: Int) {
    this?.dispatchTouchEvent(
        MotionEvent.obtain(
            SystemClock.uptimeMillis(),
            SystemClock.uptimeMillis(),
            action,
            0f,
            0f,
            0
        )
    )
}

fun View?.safePost(runnable: Runnable?) {
    safePostDelayed(runnable, 0)
}

fun View?.safePostDelayed(runnable: Runnable?, delayMills: Long) {
    this?.postDelayed({
        this.context?.aliveActivity()?.let { runnable?.run() }
    }, delayMills)
}

fun View?.safePostDelayed(delayMills: Long, runnable: Runnable?) {
    this?.postDelayed({
        this.context?.aliveActivity()?.let { runnable?.run() }
    }, delayMills)
}

fun Fragment?.safePost(view: View?, runnable: Runnable?) {
    safePostDelayed(view, runnable, 0)
}

fun Fragment?.safePostDelayed(view: View?, runnable: Runnable?, delayMills: Long) {
    view?.postDelayed({
        this?.activity?.aliveActivity()?.let { runnable?.run() }
    }, delayMills)
}

fun View?.addOnGlobalLayoutListener(block: () -> Unit){
    val view = this
    val listener = object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            view?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
            block()
        }
    }
    this?.viewTreeObserver?.addOnGlobalLayoutListener(listener)
}


fun View?.showSoftKeyboard() {
    (this?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}


fun TextView?.color(id: Int): TextView? {
    this?.context?.let {
        this.setTextColor(ContextCompat.getColor(it, if (id != 0) id else R.color.colorAccent))
    }
    return this
}

fun TextView?.size(sizeSp: Float): TextView? {
    this?.textSize = sizeSp
    return this
}

// 设置TextView下划线
fun TextView?.underLine(): TextView? {
    return paintFlags(Paint.UNDERLINE_TEXT_FLAG)
}

// 设置TextView中划线
fun TextView?.strikeThrough(): TextView? {
    return paintFlags(Paint.STRIKE_THRU_TEXT_FLAG)
}

fun TextView?.paintFlags(flag: Int): TextView? {
    this?.paintFlags = flag
    return this
}

fun TextView?.includeFontPadding(include: Boolean): TextView? {
    this?.includeFontPadding = include
    return this
}

fun TextView?.gravity(gravity: Int): TextView? {
    this?.gravity = gravity
    return this
}

fun TextView?.maxLines(maxLines: Int): TextView? {
    this?.maxLines = maxLines
    return this
}

fun TextView?.ellipsizeEnd(): TextView? {
    return this?.ellipsize(TextUtils.TruncateAt.END)
}

fun TextView?.ellipsize(truncateAt: TextUtils.TruncateAt): TextView? {
    this?.ellipsize = truncateAt
    return this
}

fun TextView?.movementMethod(): TextView? {
    this?.isVerticalScrollBarEnabled = true
    this?.movementMethod = ScrollingMovementMethod.getInstance()
    return this
}

fun TextView?.topDrawable(id: Int) {
    this?.setCompoundDrawablesWithIntrinsicBounds(0, id, 0, 0)
}

fun TextView?.rightDrawable(id: Int) {
    this?.setCompoundDrawablesWithIntrinsicBounds(0, 0, id, 0)
}

fun TextView?.rightDrawable(drawable: Drawable) {
    this?.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
}

fun TextView?.leftDrawable(id: Int) {
    this?.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
}

fun TextView?.noDrawable() {
    this?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
}

fun TextView?.bgResource(id: Int) {
    this?.setBackgroundResource(id)
}

fun TextView?.text(id: Int): TextView? {
    this?.context?.let {
        text = it.getString(id)
    }
    return this
}

fun TextView?.text(text: String?): TextView? {
    this?.text = text.orEmpty()
    return this
}

fun TextView?.text(text: CharSequence?): TextView? {
    this?.text = text
    return this
}

fun TextView?.span(text: Spanned?): TextView? {
    this?.text = text
    return this
}

/**
 * 设置字体控件文本：如果 [text] 非空且有效，设置文本并显示，否则隐藏控件
 */
fun TextView?.validTextOrGone(text: String?): TextView? {
    this?.takeIf { text.isNullOrBlank().not() }?.let {
        it.text = text.orEmpty()
        it.visible()
    }?: this?.gone()
    return this
}
fun TextView?.validTextOrGone(text: CharSequence?): TextView? {
    this?.takeIf { text.isNullOrBlank().not() }?.let {
        it.text = text
        it.visible()
    }?: this?.gone()
    return this
}

fun TextView?.bold(): TextView? {
    this?.typeface = Typeface.DEFAULT_BOLD
    return this
}

fun TextView?.normal(): TextView? {
    this?.typeface = Typeface.DEFAULT
    return this
}

fun TextView?.bold(bold: Boolean = false): TextView? {
    if (bold) {
        this?.typeface = Typeface.DEFAULT_BOLD
    } else {
        this?.typeface = Typeface.DEFAULT
    }
    return this
}

fun TextView?.typeface(typeFace: Typeface): TextView? {
    this?.typeface = typeFace
    return this
}

fun ImageView.get(url: String?): RequestBuilder<Drawable> = Glide.with(context).load(url)
fun ImageView.get(drawable: Drawable?): RequestBuilder<Drawable> = Glide.with(context).load(drawable)
fun ImageView.get(@DrawableRes resourceId: Int): RequestBuilder<Drawable> = Glide.with(context).load(resourceId)

fun ImageView?.loadGif(url: String?) {
    this?.context?.aliveActivity()?.let {
        Glide.with(it).load(url).into(this)
    }
}

fun ImageView?.loadGif(@DrawableRes resourceId: Int) {
    this?.context?.aliveActivity()?.let {
        Glide.with(it).asGif().load(resourceId).into(this)
    }
}


fun TextView?.drawableStart(id: Int, pad: Int = 0): TextView? {
    this?.compoundDrawables?.let {
//        val left = it.elementAtOrNull(0)
        val top = it.elementAtOrNull(1)
        val right = it.elementAtOrNull(2)
        val bottom = it.elementAtOrNull(3)

        this.context?.aliveActivity()?.let { ctx ->
            this.setCompoundDrawablesWithIntrinsicBounds(
                if (id != 0) ContextCompat.getDrawable(ctx, id) else null, top, right, bottom)
        }
    }

    this?.takeIf { pad > 0 }?.compoundDrawablePadding = pad
    return this
}

fun TextView?.drawableTop(id: Int, pad: Int = 0): TextView? {
    this?.compoundDrawables?.let {
        val left = it.elementAtOrNull(0)
//        val top = it.elementAtOrNull(1)
        val right = it.elementAtOrNull(2)
        val bottom = it.elementAtOrNull(3)

        this.context?.aliveActivity()?.let { ctx ->
            this.setCompoundDrawablesWithIntrinsicBounds(left,
                if (id != 0) ContextCompat.getDrawable(ctx, id) else null, right, bottom)
        }
    }

    this?.takeIf { pad > 0 }?.compoundDrawablePadding = pad
    return this
}


fun TextView?.drawableEnd(id: Int, pad: Int = 0): TextView? {
    this?.compoundDrawables?.let {
        val left = it.elementAtOrNull(0)
        val top = it.elementAtOrNull(1)
//        val right = it.elementAtOrNull(2)
        val bottom = it.elementAtOrNull(3)

        this.context?.aliveActivity()?.let { ctx ->
            this.setCompoundDrawablesWithIntrinsicBounds(left, top,
                if (id != 0) ContextCompat.getDrawable(ctx, id) else null, bottom)
        }
    }
    this?.takeIf { pad > 0 }?.compoundDrawablePadding = pad
    return this
}

fun TextView?.drawableBottom(id: Int, pad: Int = 0): TextView? {
    this?.compoundDrawables?.let {
        val left = it.elementAtOrNull(0)
        val top = it.elementAtOrNull(1)
        val right = it.elementAtOrNull(2)
//        val bottom = it.elementAtOrNull(3)

        this.context?.aliveActivity()?.let { ctx ->
            this.setCompoundDrawablesWithIntrinsicBounds(left, top, right,
                if (id != 0) ContextCompat.getDrawable(ctx, id) else null)
        }
    }

    this?.takeIf { pad > 0 }?.compoundDrawablePadding = pad
    return this
}

fun TextView?.drawables(idStart: Int, idTop: Int, idEnd: Int, idBottom: Int): TextView? {
    this?.setCompoundDrawablesWithIntrinsicBounds(idStart, idTop, idEnd, idBottom)
    return this
}

fun TextView?.drawablePadding(pad: Int): TextView? {
    this?.compoundDrawablePadding = pad
    return this
}

fun TextView?.drawableNone(): TextView? {
    this?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
    return this
}


fun ImageView?.imageSrc(@DrawableRes resourceId: Int): ImageView? {
    this?.setImageResource(resourceId)
    return this
}

fun ImageView?.colorFilter(@ColorRes colorRes: Int): ImageView? {
    this?.setColorFilter(this.context.color(colorRes))
    return this
}

fun EditText?.clearEditTextInputFocus() {
    this?.isFocusable = false
    this?.isClickable = true
    this?.setOnClickListener {
        it.isFocusable = true
        it.isFocusableInTouchMode = true
        it.requestFocus()
        it.showSoftKeyboard()
    }
}


/**
 * 水平方向渐变，字符 color 如：
 * {"#00FAF2E6", "#80FAF2E6", "#FFFAF2E6"}
 */
fun View?.bgGradientDrawableHorizontalColorString(colors: Array<String>, radius: Int = 0): View? {
    this?.context?.let {
        val colorArray = colors.map { Color.parseColor(it) }.toIntArray()
        val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colorArray)
        if (radius > 0) gradientDrawable.cornerRadius = radius.toFloat()
        this.background = gradientDrawable
    }
    return this
}

/**
 * 水平方向渐变
 * [colorInt] {Color.WHITE, Color.BLUE}
 * @param horizontalOrVertical true for horizontal, false otherwise, default true.
 */
/*
fun View?.bgGradientDrawableHorizontalColorInt(colorInt: IntArray, horizontalOrVertical: Boolean = true): View? {
    this?.context?.let {
        val gradientDrawable = GradientDrawable(if (horizontalOrVertical) GradientDrawable.Orientation.LEFT_RIGHT else GradientDrawable.Orientation.TOP_BOTTOM, colorInt)
        this.background = gradientDrawable
    }
    return this
}
*/

/**
 * 矩形背景
 * [colorRes] R.color.color_cccccc
 */
fun View?.bgShapeRectangleDrawableColorRes(@ColorRes colorRes: Int, radius: Int = 0): View? {
    this?.context?.let { ctx ->
        val color = ContextCompat.getColor(ctx, colorRes)
        this.background = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(color, color)).apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = radius.toFloat()
        }
    }
    return this
}

/**
 * 矩形背景
 * [colorRes] R.color.color_cccccc
 */
fun View?.bgShapeRectangleDrawableColorRes(@ColorRes colorRes: Int, cornerRadiusTop: Int = 0, cornerRadiusBottom: Int = 0): View? {
    this?.context?.let { ctx ->
        val color = ContextCompat.getColor(ctx, colorRes)
        this.background = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(color, color)).apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadii = floatArrayOf(
                cornerRadiusTop.toFloat(), cornerRadiusTop.toFloat(),
                cornerRadiusTop.toFloat(), cornerRadiusTop.toFloat(),
                cornerRadiusBottom.toFloat(), cornerRadiusBottom.toFloat(),
                cornerRadiusBottom.toFloat(), cornerRadiusBottom.toFloat())
        }
    }
    return this
}

/**
 * 矩形背景
 * [colorRes] R.color.color_cccccc
 */
fun Activity?.shapeRectangleDrawableColorRes(@ColorRes colorRes: Int, radius: Int = 0): Drawable? {
    this?.let { activity ->
        val color = ContextCompat.getColor(activity, colorRes)
        return GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(color, color)).apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = radius.toFloat()
        }
    }
    return null
}

/**
 * 矩形背景
 * [colorRes] R.color.color_cccccc
 */
fun View?.bgShapeRectangleStrokeDrawableColorRes(@ColorRes colorResStroke: Int, @ColorRes colorRes: Int = 0, radius: Int = 0): View? {
    this?.context?.let { ctx ->
        val color = if (colorRes != 0)ContextCompat.getColor(ctx, colorRes) else 0
        val colorStroke = ContextCompat.getColor(ctx, colorResStroke)
        this.background = if (color > 0)
            GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(color, color))
        else
            GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, null).apply {
                shape = GradientDrawable.RECTANGLE
                setStroke(ctx.dp2Pixel(0.5f), colorStroke)
                cornerRadius = radius.toFloat()
            }
    }
    return this
}

/**
 * 水平 LEFT_RIGHT 或垂直 TOP_BOTTOM 方向渐变
 * [colorRes] {R.color.color_cccccc, R.color.blue}
 * @param horizontalOrVertical true for horizontal TOP_BOTTOM, false otherwise, default true.
 */
fun View?.bgGradientDrawableColorRes(colorRes: IntArray, cornerRadius: Int = 0, horizontalOrVertical: Boolean = true): View? {
    this?.context?.let { ctx ->
        val colors = colorRes.map { ContextCompat.getColor(ctx, it) }.toIntArray()
        val gradientDrawable = GradientDrawable(
            if (horizontalOrVertical) GradientDrawable.Orientation.LEFT_RIGHT
            else GradientDrawable.Orientation.TOP_BOTTOM, colors)
        gradientDrawable.cornerRadius = cornerRadius.toFloat()
        this.background = gradientDrawable
    }
    return this
}

/**
 * 水平 LEFT_RIGHT 或垂直 TOP_BOTTOM 方向渐变
 * [colorRes] {R.color.color_cccccc, R.color.blue}
 * @param horizontalOrVertical true for horizontal TOP_BOTTOM, false otherwise, default true.
 */
fun View?.bgGradientDrawableColorResRadiusTopBottom(@ColorRes colorRes: Int, cornerRadiusTop: Int = 0, cornerRadiusBottom: Int = 0, horizontalOrVertical: Boolean = true): View? {
    this?.context?.let { ctx ->
        val color = ContextCompat.getColor(ctx, colorRes)
        val gradientDrawable = GradientDrawable(
            if (horizontalOrVertical) GradientDrawable.Orientation.LEFT_RIGHT
            else GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(color, color)
        )
        /**
         * Specifies radii for each of the 4 corners. For each corner, the array
         * contains 2 values, <code>[X_radius, Y_radius]</code>. The corners are
         * ordered top-left, top-right, bottom-right, bottom-left. This property
         * is honored only when the shape is of type {@link #RECTANGLE}.
         */
        gradientDrawable.cornerRadii = floatArrayOf(
            cornerRadiusTop.toFloat(), cornerRadiusTop.toFloat(),
            cornerRadiusTop.toFloat(), cornerRadiusTop.toFloat(),
            cornerRadiusBottom.toFloat(), cornerRadiusBottom.toFloat(),
            cornerRadiusBottom.toFloat(), cornerRadiusBottom.toFloat())
        this.background = gradientDrawable
    }
    return this
}

/**
 * 水平 LEFT_RIGHT 或垂直 TOP_BOTTOM 方向渐变
 * [colorRes] {R.color.color_cccccc, R.color.blue}
 * @param horizontalOrVertical true for horizontal TOP_BOTTOM, false otherwise, default true.
 */
fun View?.bgGradientDrawableColorResRadiusTopBottom(@ColorRes colorRes: IntArray, cornerRadiusTop: Int = 0, cornerRadiusBottom: Int = 0, horizontalOrVertical: Boolean = true): View? {
    this?.context?.let { ctx ->
        val colors = colorRes.map { ContextCompat.getColor(ctx, it) }.toIntArray()
        val gradientDrawable = GradientDrawable(
            if (horizontalOrVertical) GradientDrawable.Orientation.LEFT_RIGHT
            else GradientDrawable.Orientation.TOP_BOTTOM,
            colors
        )
        /**
         * Specifies radii for each of the 4 corners. For each corner, the array
         * contains 2 values, <code>[X_radius, Y_radius]</code>. The corners are
         * ordered top-left, top-right, bottom-right, bottom-left. This property
         * is honored only when the shape is of type {@link #RECTANGLE}.
         */
        gradientDrawable.cornerRadii = floatArrayOf(
            cornerRadiusTop.toFloat(), cornerRadiusTop.toFloat(),
            cornerRadiusTop.toFloat(), cornerRadiusTop.toFloat(),
            cornerRadiusBottom.toFloat(), cornerRadiusBottom.toFloat(),
            cornerRadiusBottom.toFloat(), cornerRadiusBottom.toFloat())
        this.background = gradientDrawable
    }
    return this
}

/**
 * 水平方向渐变
 */
fun View?.bgLinearGradientHorizontal(@ColorRes colorRes: Int, @ColorRes colorResEnd: Int): View? {
    this?.context?.let {
//        val color0 = ctx.color(colorRes)
//        val color1 = ctx.color(colorResEnd)
//        val lGradient = LinearGradient(0f, 0f, this.measuredWidth.toFloat(), 0f, color0, color1, Shader.TileMode.CLAMP)
        bgGradientDrawableColorRes(intArrayOf(colorRes, colorResEnd))
    }
    return this
}

/**
 *  水平方向渐变，中线对称
 */
fun View?.bgLinearGradientHorizontalSymmetry(@ColorRes colorRes: Int, @ColorRes colorResCenter: Int, @ColorRes colorResEnd: Int): View? {
    this?.context?.let { _ ->
//        val color0 = ctx.color(colorRes)
//        val color1 = ctx.color(colorResCenter)
//        val color2 = ctx.color(colorResEnd)
//        val gradient = LinearGradient(0f, 0f, this.measuredWidth.toFloat(), 0f, intArrayOf(color0, color1, color2), floatArrayOf(0f, 0.5f, 1f), Shader.TileMode.CLAMP)
        bgGradientDrawableColorRes(intArrayOf(colorRes,  colorResCenter, colorResEnd))
    }
    return this
}

/**
 * 创建约束 layout param 参数
 * 创建约束 layout param 参数
 * 默认：宽 0 约束，高 适应内容
 */
fun newConstraintLayoutParams(): ConstraintLayout.LayoutParams {
    return ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
}

/**
 * 创建约束 layout param 参数
 * 创建约束 layout param 参数
 * 默认：宽 父宽，高设定值
 */
fun newConstraintLayoutParamsWidthParent(height: Int): ConstraintLayout.LayoutParams {
    return ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, height)
}

/**
 * 创建约束 layout param 参数
 * 创建约束 layout param 参数
 * 默认：宽 父宽，高设定值
 */
fun newConstraintLayoutParamsWidthParentHeightWrapContent(): ConstraintLayout.LayoutParams {
    return ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
}

/**
 * 创建约束 layout param 参数
 * 创建约束 layout param 参数
 * 默认：宽 适应内容，高 适应内容
 */
fun newConstraintLayoutParamsWidthHeightWrapContent(): ConstraintLayout.LayoutParams {
    return ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
}

/**
 * 创建约束 layout param 参数
 * 创建约束 layout param 参数
 * 默认：宽设定值，高设定值
 */
fun newConstraintLayoutParams(width: Int, height: Int): ConstraintLayout.LayoutParams {
    return ConstraintLayout.LayoutParams(width, height)
}

/**
 * 创建 View 约束
 * 创建 View 约束
 * 默认：宽 0 约束，高适应内容
 */
fun View?.startConstraint(): ConstraintLayout.LayoutParams {
    return createConstraint()
}

/**
 * 创建 View 约束
 * 创建 View 约束
 * 默认：宽 0 约束，高适应内容
 */
fun View?.startConstraintWrapContent(): ConstraintLayout.LayoutParams {
    return newConstraintLayoutParamsWidthHeightWrapContent().also { this.layoutParams(it) }
}

/**
 * 获取或创建布局参数
 * 创建 View 约束
 * 创建 View 约束
 * 默认：宽 0 约束，高适应内容
 */
fun View?.startOrCreateConstraint(): ConstraintLayout.LayoutParams {
    return (this?.layoutParams as? ConstraintLayout.LayoutParams)?: createConstraint()
}

/**
 * 创建 View 约束
 * 创建 View 约束
 * 默认：宽 0 约束，高适应内容
 */
fun View?.createConstraint(): ConstraintLayout.LayoutParams {
    return newConstraintLayoutParams().let {
        this.layoutParams(it)
        it
    }
}

/**
 * 创建 View 约束
 * 默认：宽  父宽，高 设定值
 */
fun ConstraintLayout.LayoutParams?.widthHeight(width: Int, height: Int): ConstraintLayout.LayoutParams? {
    this?.width = width
    this?.height = height
    return this
}

/**
 * 创建 View 约束
 * 默认：宽  父宽，高 设定值
 */
fun View?.createConstraintWidthParent(height: Int): ConstraintLayout.LayoutParams {
    return newConstraintLayoutParamsWidthParent(height).let {
        this.layoutParams(it)
        it
    }
}

/**
 * 创建 View 约束
 * 默认：宽  父宽，高 适应内容
 */
fun View?.createConstraintWidthParentHeightWrapContent(): ConstraintLayout.LayoutParams {
    return newConstraintLayoutParamsWidthParentHeightWrapContent().let {
        this.layoutParams(it)
        it
    }
}

/**
 * 创建 View 约束
 * 默认：宽 设定值，高 适应内容
 */
fun View?.createConstraintWidth(width: Int): ConstraintLayout.LayoutParams {
    return newConstraintLayoutParams(width, ConstraintLayout.LayoutParams.WRAP_CONTENT).let {
        this.layoutParams(it)
        it
    }
}

/**
 * 创建 View 约束
 * 默认：宽 0 约束，高 设定值
 */
fun View?.createConstraintHeight(height: Int): ConstraintLayout.LayoutParams {
    return newConstraintLayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, height).let {
        this.layoutParams(it)
        it
    }
}

/**
 * 创建 View 约束
 * 默认：宽 0 约束，高设定值(dp)
 */
fun View?.createConstraintHeightInDp(heightDp: Float): ConstraintLayout.LayoutParams? {
    return this?.context?.aliveActivity()?.let {
        createConstraintHeight(it.dp2Pixel(heightDp))
    }
}

/**
 * 创建 View 约束
 * 默认：宽 0 约束，高设定值(dimen resource)
 */
fun View?.createConstraintHeightInDpRes(@DimenRes heightDimenRes: Int): ConstraintLayout.LayoutParams? {
    return this?.context?.aliveActivity()?.let {
        createConstraintHeight(it.resources.getDimensionPixelSize(heightDimenRes))
    }
}

/**
 * 创建 View 约束
 * 默认：宽设定值，高设定值
 */
fun View?.createConstraint(width: Int, height: Int): ConstraintLayout.LayoutParams {
    return newConstraintLayoutParams(width, height).let {
        this.layoutParams(it)
        it
    }
}


/**
 * 设置（修改）约束 layout param 参数
 * 设置（修改）约束 layout param 参数
 * 左起对齐父布局
 */
fun ConstraintLayout.LayoutParams?.startParent(): ConstraintLayout.LayoutParams? {
    this?.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
    return this
}

/**
 * 设置（修改）约束 layout param 参数
 * 右侧对齐父布局
 */
fun ConstraintLayout.LayoutParams?.endParent(): ConstraintLayout.LayoutParams? {
    this?.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
    return this
}

/**
 * 设置（修改）约束 layout param 参数
 * 左右对齐父布局
 */
fun ConstraintLayout.LayoutParams?.startEndParent(): ConstraintLayout.LayoutParams? {
    this?.startParent()
    this?.endParent()
    return this
}

/**
 * 设置（修改）约束 layout param 参数
 * 左右上下起对齐父布局
 */
fun ConstraintLayout.LayoutParams?.constraintParent(): ConstraintLayout.LayoutParams? {
    return this?.startEndParent().topBottomParent()
}

/**
 * 设置（修改）约束 layout param 参数
 * 左起 VIEW [id]
 */
fun ConstraintLayout.LayoutParams?.startId(id: Int): ConstraintLayout.LayoutParams? {
    this?.startToEnd = id
    return this
}

/**
 * 设置（修改）约束 layout param 参数
 * 右对齐 VIEW [id]
 */
fun ConstraintLayout.LayoutParams?.endId(id: Int): ConstraintLayout.LayoutParams? {
    this?.endToStart = id
    return this
}

/**
 * 设置（修改）约束 layout param 参数
 * 左起对齐父布局
 * 右对齐 VIEW [idEnd]
 */
fun ConstraintLayout.LayoutParams?.startParentEndId(idEnd: Int): ConstraintLayout.LayoutParams? {
    this?.startParent()
    this?.endId(idEnd)
    return this
}

/**
 * 设置（修改）约束 layout param 参数
 * 左起 VIEW [idStart]
 * 右对齐父布局
 */
fun ConstraintLayout.LayoutParams?.startIdEndParent(idStart: Int): ConstraintLayout.LayoutParams? {
    this?.startId(idStart)
    this?.endParent()
    return this
}

/**
 * 设置（修改）约束 layout param 参数
 * 左起 VIEW [idStart]
 * 右对齐 VIEW [idEnd]
 */
fun ConstraintLayout.LayoutParams?.startEndId(idStart: Int, idEnd: Int): ConstraintLayout.LayoutParams? {
    this?.startId(idStart)
    this?.endId(idEnd)
    return this
}

/**
 * 设置（修改）约束 layout param 参数
 * 上自 父布局
 */
fun ConstraintLayout.LayoutParams?.topParent(): ConstraintLayout.LayoutParams? {
    this?.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
    return this
}

/**
 * 设置（修改）约束 layout param 参数
 * 下至 父布局
 */
fun ConstraintLayout.LayoutParams?.bottomParent(): ConstraintLayout.LayoutParams? {
    this?.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
    return this
}

/**
 * 设置（修改）约束 layout param 参数
 * 上下起对齐父布局
 */
fun ConstraintLayout.LayoutParams?.topBottomParent(): ConstraintLayout.LayoutParams? {
    this?.topParent()
    this?.bottomParent()
    return this
}

/**
 * 设置（修改）约束 layout param 参数
 * 上自 VIEW [id]
 */
fun ConstraintLayout.LayoutParams?.topId(id: Int): ConstraintLayout.LayoutParams? {
    this?.topToBottom = id
    return this
}

/**
 * 设置（修改）约束 layout param 参数
 * 上自 VIEW [id]
 */
fun ConstraintLayout.LayoutParams?.topTopId(id: Int): ConstraintLayout.LayoutParams? {
    this?.topToTop = id
    return this
}

/**
 * 设置（修改）约束 layout param 参数
 * 下至 VIEW [id]
 */
fun ConstraintLayout.LayoutParams?.bottomId(id: Int): ConstraintLayout.LayoutParams? {
    this?.bottomToTop = id
    return this
}

/**
 * 设置（修改）约束 layout param 参数
 * 下至 VIEW [id]
 */
fun ConstraintLayout.LayoutParams?.bottomBottomId(id: Int): ConstraintLayout.LayoutParams? {
    this?.bottomToBottom = id
    return this
}

/**
 * 设置（修改）约束 layout param 参数
 * 上自 父布局
 * 下至 VIEW [idBottom]
 */
fun ConstraintLayout.LayoutParams?.topParentBottomId(idBottom: Int): ConstraintLayout.LayoutParams? {
    this?.topParent()
    this?.bottomId(idBottom)
    return this
}

/**
 * 设置（修改）约束 layout param 参数
 * 上自 VIEW [idTop]
 * 下至父布局
 */
fun ConstraintLayout.LayoutParams?.topIdBottomParent(idTop: Int): ConstraintLayout.LayoutParams? {
    this?.topId(idTop)
    this?.bottomParent()
    return this
}

/**
 * 设置（修改）约束 layout param 参数
 * 上自 VIEW [idTop]
 * 下至 VIEW [idBottom]
 */
fun ConstraintLayout.LayoutParams?.topBottomId(idTop: Int, idBottom: Int): ConstraintLayout.LayoutParams? {
    this?.topId(idTop)
    this?.bottomId(idBottom)
    return this
}

/**
 * 设置（修改）约束 layout param 参数 margins
 */
fun ConstraintLayout.LayoutParams?.margins(marginStart: Int = 0, topMargin: Int = 0, marginEnd: Int = 0, bottomMargin: Int = 0): ConstraintLayout.LayoutParams? {
    this?.marginStart = marginStart
    this?.topMargin = topMargin
    this?.marginEnd = marginEnd
    this?.bottomMargin = bottomMargin
    return this
}

/**
 * 设置（修改）约束 layout param 参数 margins
 */
fun ConstraintLayout.LayoutParams?.marginsStartEnd(marginStart: Int = 0, marginEnd: Int = 0): ConstraintLayout.LayoutParams? {
    margins(marginStart, 0, marginEnd, 0)
    return this
}

/**
 * 设置（修改）约束 layout param 参数 margins
 */
fun ConstraintLayout.LayoutParams?.marginsStartEndSame(marginStartEnd: Int = 0): ConstraintLayout.LayoutParams? {
    margins(marginStartEnd, 0, marginStartEnd, 0)
    return this
}

/**
 * 设置（修改）约束 layout param 参数 margins
 */
fun ConstraintLayout.LayoutParams?.marginsTopBottom(topMargin: Int = 0, bottomMargin: Int = 0): ConstraintLayout.LayoutParams? {
    margins(0, topMargin, 0, bottomMargin)
    return this
}

/**
 * 设置（修改）约束 layout param 参数 margins
 */
fun ConstraintLayout.LayoutParams?.marginsTopBottomSame(topBottomMargin: Int = 0): ConstraintLayout.LayoutParams? {
    margins(0, topBottomMargin, 0, topBottomMargin)
    return this
}

/**
 * 设置（修改）约束 layout param 参数 margins
 */
fun ConstraintLayout.LayoutParams?.marginsInDp(context: Context?, marginStart: Float = 0f, topMargin: Float = 0f, marginEnd: Float = 0f, bottomMargin: Float = 0f): ConstraintLayout.LayoutParams? {
    context?.let { ctx ->
        margins(
            ctx.dp2Pixel(marginStart),
            ctx.dp2Pixel(topMargin),
            ctx.dp2Pixel(marginEnd),
            ctx.dp2Pixel(bottomMargin))
    }
    return this
}

/**
 * 设置（修改）约束 layout param 参数 gone margins
 */
fun ConstraintLayout.LayoutParams?.goneMargins(marginStart: Int = 0, topMargin: Int = 0, marginEnd: Int = 0, bottomMargin: Int = 0): ConstraintLayout.LayoutParams? {
    this?.goneStartMargin = marginStart
    this?.goneTopMargin = topMargin
    this?.goneEndMargin = marginEnd
    this?.goneBottomMargin = bottomMargin
    return this
}

/**
 * 设置（修改）约束 layout param 参数 gone margins
 */
fun ConstraintLayout.LayoutParams?.goneMarginsInDp(context: Context?, marginStart: Float = 0f, topMargin: Float = 0f, marginEnd: Float = 0f, bottomMargin: Float = 0f): ConstraintLayout.LayoutParams? {
    context?.let { ctx ->
        this?.goneMargins(
            ctx.dp2Pixel(marginStart),
            ctx.dp2Pixel(topMargin),
            ctx.dp2Pixel(marginEnd),
            ctx.dp2Pixel(bottomMargin))
    }
    return this
}

/**
 * 设置（修改）约束 layout param 参数 宽高比
 */
fun ConstraintLayout.LayoutParams?.dimensionRatio(ratio: String): ConstraintLayout.LayoutParams? {
    this?.dimensionRatio = ratio
    return this
}


/**
 * 设置（修改）约束布局布局参数 layout param
 */
fun ConstraintLayout?.layout(width: Int, height: Int): ConstraintLayout? {
    this?.layoutParams?.let {
        it.width = width
        it.height = height
    }?: kotlin.run {
        this?.layoutParams = ViewGroup.LayoutParams(width, height)
    }
    return this
}

/**
 * 设置（修改）约束布局布局参数 layout param
 */
fun ConstraintLayout?.layoutWidthParent(height: Int): ConstraintLayout? {
    this?.layoutParams?.let {
        it.width = width
        it.height = height
    }?: kotlin.run {
        this?.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
    }
    return this
}

fun ConstraintLayout?.layoutInDp(widthDp: Float, heightDp: Float): ConstraintLayout? {
    this?.context?.aliveActivity()?.let { ctx ->
        layout(ctx.dp2Pixel(widthDp), ctx.dp2Pixel(heightDp))
    }
    return this
}

fun ConstraintLayout?.addTextView(textView: TextView?, constraintLP: ConstraintLayout.LayoutParams?): ConstraintLayout? {
    this?.addView(textView, constraintLP)
    return this
}




/**
 * 动画相关
 */
inline fun animateTogether(duration: Long, block: (ArrayList<Animator>) -> Unit) {
    val animators = ArrayList<Animator>()
    block(animators)
    val animatorSet = AnimatorSet().setDuration(duration)
    animatorSet.playTogether(animators)
    animatorSet.start()
}

/**
 * 添加或创建一个逐渐可见 alpha 动画
 */
fun View?.addShowAlpha(animatorList: ArrayList<Animator>? = null): Animator? {
    return this.notNull {
        val animator = ObjectAnimator.ofFloat(it, View.ALPHA, 0f, 1f)
        animatorList?.add(animator)
        animator
    }
}

/**
 * 添加或创建一个 Y 方向移动动画
 */
fun View?.addTranslation(animatorList: ArrayList<Animator>? = null,
                         startY: Float = -(this?.measuredHeight?.toFloat()?: 0f),
                         endY: Float = 0f): Animator? {
    return this.notNull {
        val animator = ObjectAnimator.ofFloat(it, View.TRANSLATION_Y, startY, endY)
        animatorList?.add(animator)
        animator
    }
}




