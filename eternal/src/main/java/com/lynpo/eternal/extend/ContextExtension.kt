package com.lynpo.eternal.extend

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.lynpo.eternal.util.ConvertUtil

/**
 * @Author ls
 * @Date  2021/1/19
 * @Description
 * @Version
 */

fun Context?.aliveActivity(): Activity? {
    return this?.findContextActivity()?.takeUnless {
        it.isFinishing || it.isDestroyed
    }
}

fun Context?.findContextActivity(): Activity? {
    return when (this) {
        is Application -> null
        is Activity -> this
        is ContextWrapper -> this.baseContext?.findContextActivity()
        else -> null
    }
}


fun Activity?.intExtra(key: String, defaultValue: Int = 0): Int {
    return this?.intent?.getIntExtra(key, defaultValue)?: defaultValue
}
fun Activity?.shortExtra(key: String, defaultValue: Short = 0): Short {
    return this?.intent?.getShortExtra(key, defaultValue)?: defaultValue
}
fun Activity?.longExtra(key: String, defaultValue: Long = 0): Long {
    return this?.intent?.getLongExtra(key, defaultValue)?: defaultValue
}
fun Activity?.boolExtra(key: String, defaultValue: Boolean = false): Boolean {
    return this?.intent?.getBooleanExtra(key, defaultValue)?: defaultValue
}
fun Activity?.stringExtra(key: String, defaultValue: String = ""): String {
    return this?.intent?.getStringExtra(key) ?: defaultValue
}
fun Activity?.intListExtra(key: String, defaultValue: ArrayList<Int> = arrayListOf()): ArrayList<Int> {
    return this?.intent?.getIntegerArrayListExtra(key) ?: defaultValue
}
fun Activity?.stringListExtra(key: String, defaultValue: ArrayList<String> = arrayListOf()): ArrayList<String> {
    return this?.intent?.getStringArrayListExtra(key) ?: defaultValue
}
fun <T : Parcelable> Activity?.parcelableExtra(key: String, defaultValue: T? = null): T? {
    return this?.intent?.getParcelableExtra(key)?: defaultValue
}
fun <T : Parcelable> Activity?.arrayListExtra(key: String, defaultValue: ArrayList<T> = arrayListOf()): ArrayList<T> {
    return this?.intent?.getParcelableArrayListExtra<T>(key)?: defaultValue
}

fun Fragment?.intExtra(key: String, defaultValue: Int = 0): Int {
    return this?.arguments?.getInt(key)?: defaultValue
}
fun Fragment?.shortExtra(key: String, defaultValue: Short = 0): Short {
    return this?.arguments?.getShort(key)?: defaultValue
}
fun Fragment?.longExtra(key: String, defaultValue: Long = 0): Long {
    return this?.arguments?.getLong(key, defaultValue)?: defaultValue
}
fun Fragment?.boolExtra(key: String, defaultValue: Boolean = false): Boolean {
    return this?.arguments?.getBoolean(key, defaultValue)?: defaultValue
}
fun Fragment?.stringExtra(key: String, defaultValue: String = ""): String {
    return this?.arguments?.getString(key, defaultValue)?: defaultValue
}
fun Fragment?.intListExtra(key: String, defaultValue: ArrayList<Int> = arrayListOf()): ArrayList<Int> {
    return this?.arguments?.getIntegerArrayList(key) ?: defaultValue
}
fun Fragment?.stringListExtra(key: String, defaultValue: ArrayList<String> = arrayListOf()): ArrayList<String> {
    return this?.arguments?.getStringArrayList(key) ?: defaultValue
}
fun <T : Parcelable> Fragment?.parcelableExtra(key: String, defaultValue: T? = null): T? {
    return this?.arguments?.getParcelable<T>(key)?: defaultValue
}
fun < T : Parcelable> Fragment?.arrayListExtra(key: String, defaultValue: ArrayList<T> = arrayListOf()): ArrayList<T> {
    return this?.arguments?.getParcelableArrayList<T>(key) ?: defaultValue
}


fun Activity?.alive(): Boolean {
    return this?.aliveActivity()?.let { true }?: false
}

fun Activity?.alive(block: () -> Unit, elseBlock: (() -> Unit)? = null) {
    this?.aliveActivity()?.let { block() }?: elseBlock?.invoke()
}

fun Fragment?.alive(): Boolean = this?.let { it.activity != null && it.isAdded && !it.isDetached && it.activity.alive() }?: false

fun Fragment?.alive(block: () -> Unit, elseBlock: (() -> Unit)? = null) {
    this?.takeIf { it.alive() }?.let { block() }?: elseBlock?.invoke()
}


fun Context?.stringRes(@StringRes id: Int): String = this?.getString(id).orEmpty()
fun Context?.color(@ColorRes id: Int): Int = this?.let { ContextCompat.getColor(it, id) }?: 0

fun Activity?.drawable(@DrawableRes drawableResId: Int): Drawable? = this?.let { ContextCompat.getDrawable(it, drawableResId) }
fun Activity?.colorDrawable(@ColorRes colorRes: Int): Drawable? = this?.let { ContextCompat.getDrawable(it, colorRes) }

fun Fragment?.color(@ColorRes id: Int): Int = this?.activity.color(id)
fun Fragment?.drawable(@DrawableRes drawableResId: Int): Drawable? = this?.activity.drawable(drawableResId)
fun Fragment?.colorDrawable(@ColorRes colorRes: Int): Drawable? = this?.activity.colorDrawable(colorRes)

fun View?.dp2Pixel(dp: Int): Int = dp2Pixel(dp.toFloat())
fun View?.dp2Pixel(dp: Float): Int = this?.context?.let { ConvertUtil.dp2px(it, dp) }?: 0
fun Context?.dp2Pixel(dp: Int): Int = dp2Pixel(dp.toFloat())
fun Context?.dp2Pixel(dp: Float): Int = this?.aliveActivity()?.let { ConvertUtil.dp2px(it, dp) }?: 0
fun Context?.sp2Pixel(sp: Int): Int = sp2Pixel(sp.toFloat())
fun Context?.sp2Pixel(sp: Float): Int = this?.aliveActivity()?.let { ConvertUtil.sp2px(it, sp) }?: 0
fun Fragment?.dp2Pixel(dp: Int): Int = dp2Pixel(dp.toFloat())
fun Fragment?.dp2Pixel(dp: Float): Int = this?.activity?.aliveActivity()?.dp2Pixel(dp)?: 0
fun Fragment?.sp2Pixel(sp: Int): Int = sp2Pixel(sp.toFloat())
fun Fragment?.sp2Pixel(sp: Float): Int = this?.activity?.aliveActivity()?.sp2Pixel(sp)?: 0


fun Context?.dp2px(dp: Int): Int = dp2px(dp.toFloat())
fun Context?.dp2px(dp: Float): Int = this?.aliveActivity()?.let { ConvertUtil.dp2px(it, dp) }?: 0
fun Context?.sp2px(sp: Int): Int = sp2px(sp.toFloat())
fun Context?.sp2px(sp: Float): Int = this?.aliveActivity()?.let { ConvertUtil.sp2px(it, sp) }?: 0
fun Fragment?.dp2px(dp: Int): Int = dp2px(dp.toFloat())
fun Fragment?.dp2px(dp: Float): Int = this?.activity?.aliveActivity()?.dp2px(dp)?: 0
fun Fragment?.sp2px(sp: Int): Int = sp2px(sp.toFloat())
fun Fragment?.sp2px(sp: Float): Int = this?.activity?.aliveActivity()?.sp2px(sp)?: 0


fun View?.dpPixelSize(@DimenRes dimenResId: Int): Int = this?.context.dpPixelSize(dimenResId)
fun Fragment?.dpPixelSize(@DimenRes dimenResId: Int): Int = this?.activity.dpPixelSize(dimenResId)
fun Context?.dpPixelSize(@DimenRes dimenResId: Int): Int {
    return this?.aliveActivity()?.resources?.getDimensionPixelSize(dimenResId)?: 0
}


fun Context?.inflateView(@LayoutRes resourceId: Int, root: ViewGroup? = null, attachToRoot: Boolean = false): View?
        = this?.let { LayoutInflater.from(it).inflate(resourceId, root, attachToRoot) }
fun Fragment?.inflateView(@LayoutRes resourceId: Int, root: ViewGroup? = null, attachToRoot: Boolean = false): View?
        = this?.activity?.aliveActivity()?.inflateView(resourceId, root, attachToRoot)


fun Context?.newLinearLayoutParams(
    width: Int = LinearLayout.LayoutParams.MATCH_PARENT,
    height: Int = LinearLayout.LayoutParams.WRAP_CONTENT
): LinearLayout.LayoutParams
        = LinearLayout.LayoutParams(width, height)
fun Fragment?.newLinearLayoutParams(
    width: Int = LinearLayout.LayoutParams.MATCH_PARENT,
    height: Int = LinearLayout.LayoutParams.WRAP_CONTENT
): LinearLayout.LayoutParams?
        = this?.activity?.aliveActivity()?.let { it.newLinearLayoutParams(width, height) }


fun Context?.newRelativeLayoutParams(
    width: Int = RelativeLayout.LayoutParams.MATCH_PARENT,
    height: Int = RelativeLayout.LayoutParams.MATCH_PARENT
): RelativeLayout.LayoutParams
        = RelativeLayout.LayoutParams(width, height)
fun Fragment?.newRelativeLayoutParams(
    width: Int = RelativeLayout.LayoutParams.MATCH_PARENT,
    height: Int = RelativeLayout.LayoutParams.MATCH_PARENT
): RelativeLayout.LayoutParams?
        = this?.activity?.aliveActivity()?.let { it.newRelativeLayoutParams(width, height) }




