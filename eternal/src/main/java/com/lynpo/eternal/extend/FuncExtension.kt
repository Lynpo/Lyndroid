package com.lynpo.eternal.extend

import android.database.Cursor
import android.text.TextUtils
import android.util.SparseIntArray
import com.google.gson.JsonObject
import com.lynpo.eternal.util.GsonUtil
import io.reactivex.disposables.Disposable
import java.util.*


/**
 * FunctionExtend
 **
 * Create by fujw on 12/2/20.
 */
inline fun Any?.asBool(default: Boolean = false): Boolean {
    return if (this is String) this.toBoolean() else (this as? Boolean) ?: default
}

inline fun Any?.asString(default: String = ""): String {
    return if (this is String) this.emptyOr(default) else this?.toString().emptyOr(default)
}

inline fun Any?.asInt(default: Int = 0): Int {
    return if (this is String) this.toIntOrNull().orNull(default) else if (this is Number) this.toInt() else default
}

inline fun Any?.asLong(default: Long = 0L): Long {
    return if (this is String) this.toLongOrNull().orNull(default) else if (this is Number) this.toLong() else default
}

inline fun <K, V, M : Map<out K, V>> M.onEach(action: (key: K, value: V) -> Unit): M {
    return apply { for (element in this) action(element.key, element.value) }
}

fun <T> T?.notNull(): Boolean {
    return this != null
}

inline fun <T, R> T?.notNull(notNull: (t: T) -> R): R? {
    return if (this != null) {
        notNull(this)
    } else null
}

inline fun <T, T1, T2, R> T?.notNulls(t1: T1?, t2: T2?, notNull: (T1, T2) -> R): R? {
    return if (this != null && t1 != null && t2 != null) {
        notNull(t1, t2)
    } else null
}

inline fun <T, T1, T2, T3, R> T?.notNulls(
    t1: T1?,
    t2: T2?,
    t3: T3?,
    notNull: (T1, T2, T3) -> R
): R? {
    return if (this != null && t1 != null && t2 != null && t3 != null) {
        notNull(t1, t2, t3)
    } else null
}

inline fun <T1, T2> ifNotNull(value1: T1?, value2: T2?, bothNotNull: (t1: T1, t2: T2) -> Unit) {
    if (value1 != null && value2 != null) {
        bothNotNull(value1, value2)
    }
}

inline fun ktTry(block: () -> Unit) {
    try {
        block()
    } catch (e: Exception) {

    }
}

inline fun ktTryOnException(block: () -> Unit, exceptionBlock: (t: Throwable) -> Unit) {
    try {
        block()
    } catch (e: Exception) {
        exceptionBlock(e)
    }
}

fun Disposable?.dispose() {
    this?.takeIf { it.isDisposed.not() }?.dispose()
}

/**
 * 数字等值判断
 */
inline fun numberEqual(number: Int, other: Int, block: () -> Unit) {
    if (number == other) block()
}

fun numberEqual(number: Int, other: Int): Boolean = number == other

fun numberNotEqual(number: Long, other: Long): Boolean = number != other


/**
 * Int, Float, Long number 扩展
 */
fun Int?.positive(): Boolean = this != null && this > 0
fun Int?.negative(): Boolean = this != null && this < 0
fun Int?.notZero(): Boolean = this != null && this != 0
fun Int?.valueExcludeOr(excludeValue: Int, orValue: Int): Int =
    if (this == null || this == excludeValue) orValue else this
// 如果为 null，返回默认值，否则返回自身
fun Int?.nullOr(defaultValue: Int = 0): Int = orNull(defaultValue)
fun Int?.orNull(defaultValue: Int = 0): Int = this ?: defaultValue

// 正数，返回本身，负数，返回默认值
fun Int?.positiveOr(default: Int): Int =
    if (this != null && this <= 0) default else (this ?: default)

fun Int?.positiveOrEmpty(): String = if (this != null && this > 0) this.toString() else ""
fun Int?.equalTo(other: Int) = this == other
fun Int?.positive(block: (Int) -> Unit) = positiveThen(block)
fun Int?.positiveThen(block: (Int) -> Unit) {
    if (this != null && this > 0) block(this)
}

//fun Int.notZero(default: Int) = if (this == 0) default else this
fun Int.divide(other: Int) = this.toFloat().divide(other.toFloat())
fun Float.notZero(default: Float) = if (this == 0F) default else this
fun Float.divide(other: Float) = this.div(other.notZero(1F))
fun Float?.orNull(defaultValue: Float = 0F): Float = this ?: defaultValue
fun Long?.positive(): Boolean = this != null && this > 0
fun Long?.negative(): Boolean = this != null && this < 0
// 如果为 null，返回默认值，否则返回自身
fun Long?.nullOr(defaultValue: Long = 0L): Long = orNull(defaultValue)
fun Long?.orNull(defaultValue: Long = 0L): Long = this ?: defaultValue
fun Long?.positiveOr(default: Long): Long =
    if (this != null && this <= 0) default else (this ?: default)
fun Long?.positive(block: (Long) -> Unit) = positiveThen(block)
fun Long?.positiveThen(block: (Long) -> Unit) {
    if (this != null && this > 0) block(this)
}


/**
 * 小于判断：
 * 如果小于等于 [maximumValue]，返回本身，否则使用给定值
 */
fun Int?.maxAtMostOr(maximumValue: Int, default: String): String =
    if (this != null) (if (this > maximumValue) default else "$this") else "0"


/**
 * Boolean 扩展
 */
// 判断当前可空 Boolean 值是否为真（true）
fun Boolean?.isTrue(): Boolean = this == true
fun Boolean?.no(): Boolean = this == false
fun Boolean?.notNullTrue(): Boolean = this != null && this == true
fun Boolean?.isTrueOrNullDefault(default: Boolean): Boolean = this ?: default
fun Boolean?.trueAlso(block: () -> Unit) = this.yesAlso(block)
fun Boolean?.yesAlso(block: () -> Unit): Boolean = if (this == true) {
    block()
    true
} else false

fun Boolean?.notTrue(): Boolean = this != false
fun Boolean?.notTrueAlso(block: () -> Unit): Boolean = if (this != true) {
    block()
    false
} else true


/**
 * 字符 Sting 扩展
 */
fun String?.notEmpty(): Boolean {
    return this != null && this.isNotEmpty()
}

inline fun String?.empty(block: () -> Unit) {
    if (this == null || this.isEmpty()) block()
}

inline fun String?.notEmpty(block: (String) -> Unit) {
    if (this != null && this.isNotEmpty()) block(this)
}

inline fun String?.notNullNotEmpty(block: (String) -> Unit) {
    if (this != null && this.isNotEmpty() && this.notEqual("null")) block(this)
}

inline fun <R> String?.notEmptyLet(block: (String) -> R?): R? {
    return if (this != null && this.isNotEmpty()) block(this) else null
}

fun String?.emptyOr(defaultValue: String = ""): String {
    return if (this != null && this.isNotEmpty()) this else defaultValue
}

fun String?.notEmptyPrefix(prefix: String): String {
    return if (this != null && this.isNotEmpty()) "$prefix$this" else ""
}

fun String?.equal(other: String?): Boolean = TextUtils.equals(this, other)
fun String?.notEqual(other: String?): Boolean = !TextUtils.equals(this, other)
fun String?.notEmptyNotEqual(other: String?): Boolean =
    !TextUtils.isEmpty(this) && !TextUtils.equals(this, other)

fun String?.equalNonNull(other: String?): Boolean =
    this.isNullOrEmpty().not() && TextUtils.equals(this, other)

fun String?.subStringBefore(index: Int): String {
    return this.notEmptyLet {
        val end = (it.length).coerceAtMost(index)
        it.substring(0, end)
    }.orEmpty()
}


/**
 *  数组扩展
 */
inline fun <T> Array<T>?.notEmptyEach(block: (T) -> Unit) {
    if (this != null && this.isNotEmpty()) this.forEach { block(it) }
}


/**
 *  列表扩展
 */
fun <T> ArrayList<T>?.notEmpty(): Boolean {
    return this != null && this.isNotEmpty()
}

inline fun <T, C : Collection<T>> C?.notEmpty(block: (C) -> Unit) {
    if (this != null && this.isNotEmpty()) block(this)
}

fun <T> ArrayList<T>?.cleanAdd(item: Collection<T>?) {
    item?.notEmpty {
        this?.clear()
        this?.addAll(it)
    }
}


inline fun <T> ArrayList<T>?.notEmpty(block: (ArrayList<T>) -> Unit) {
    if (this != null && this.isNotEmpty()) block(this)
}

fun <T, R> ArrayList<T>?.notEmptyLet(block: (ArrayList<T>) -> R): R? {
    return if (this != null && this.isNotEmpty()) block(this) else null
}

inline fun <T> ArrayList<T>?.notEmptyEach(block: (T) -> Unit) {
    if (this != null && this.isNotEmpty()) this.forEach { block(it) }
}

inline fun <T> ArrayList<T>?.notEmptyEachIndexed(block: (Int, T) -> Unit) {
    if (this != null && this.isNotEmpty()) this.forEachIndexed { index, t -> block(index, t) }
}

inline fun <T> ArrayList<T>?.takeIfEmpty(block: () -> Unit): ArrayList<T>? {
    return if (this.isNullOrEmpty()) {
        block()
        this
    } else null
}

inline fun <T> ArrayList<T>?.takeIfNotEmpty(block: (ArrayList<T>) -> Unit): ArrayList<T>? {
    return if (this != null && this.isNotEmpty()) {
        block(this)
        this
    } else null
}

inline fun <T> ArrayList<T>?.joinId(delimiter: String? = ",", block: (T) -> String): String {
    return this.joinItemProperty(delimiter, block)
}

inline fun <T> List<T>?.joinItemProperty(delimiter: String? = ",", block: (T) -> String): String {
    if (this.isNullOrEmpty()) return ""

    val sb = StringBuilder()
    var firstTime = true
    for (item in this) {
        if (firstTime) {
            firstTime = false
        } else {
            sb.append(delimiter)
        }
        val id = block(item)
        sb.append(id)
    }
    return sb.toString()
}


/**
 *  SparseIntArray 扩展
 */
inline fun SparseIntArray?.notEmptyEach(action: (key: Int, value: Int) -> Unit) {
    this?.takeIf { it.size() > 0 }?.let {
        for (index in 0 until it.size()) {
            action(it.keyAt(index), it.valueAt(index))
        }
    }
}


/**
 *  Map 扩展
 */
fun <K, V> Map<K, V>?.getIntOr(key: K, default: Int = 0): Int {
    return if (this != null) (this[key].asInt(default)) else default
}

fun <K, V> Map<K, V>?.getLongOr(key: K, default: Long = 0): Long {
    return if (this != null) (this[key].asLong(default)) else default
}

fun <K, V> Map<K, V>?.getBoolOr(key: K, default: Boolean = false): Boolean {
    return if (this != null) (this[key].asBool(default)) else default
}

fun <K, V> Map<K, V>?.getStringOr(key: K, default: String = ""): String {
    return if (this != null) (this[key].asString(default)) else default
}

fun <K, V> Map<K, V>?.getExtMapOr(
    key: K,
    default: Map<String, Any> = hashMapOf()
): Map<String, Any> {
    return if (this != null) ((this[key] as? Map<String, Any>) ?: default) else default
}

fun <K, V> MutableMap<K, V>?.addValue(key: K, value: V): MutableMap<K, V>? {
    this?.put(key, value)
    return this
}


/**
 * 对 JsonObject 扩展，对 json 类型解析
 */
fun JsonObject?.getBoolean(key: String, default: Boolean = false): Boolean {
    return this?.let { GsonUtil.getBoolean(it, key, default) } ?: default
}

fun JsonObject?.getInt(key: String, default: Int = 0): Int {
    return this?.let { GsonUtil.getInt(it, key, default) } ?: default
}

fun JsonObject?.getString(key: String, default: String = ""): String {
    return this?.let { GsonUtil.getString(it, key, default) } ?: default
}

fun JsonObject?.getJsonObject(key: String, default: JsonObject = JsonObject()): JsonObject {
    return this?.let { GsonUtil.getJsonObject(it, key, default) } ?: default
}

//inline fun <reified T> JsonObject?.getList(
//    key: String,
//    default: ArrayList<T> = arrayListOf()
//): ArrayList<T> {
//    return this?.let { GsonUtil.getJsonArray(it, key, T::class.java) } ?: default
//}

/**
 * 对 String 扩展，解析 json 字符串中相应类型的值
 */
fun String?.asJsonObject(): JsonObject? {
    return this?.let { GsonUtil.fromJson(it, JsonObject::class.java) }
}

fun String?.getBoolean(key: String, default: Boolean = false): Boolean {
    return this.asJsonObject().getBoolean(key, default)
}

fun String?.getInt(key: String, default: Int = 0): Int {
    return this.asJsonObject().getInt(key, default)
}

fun String?.getString(key: String, default: String = ""): String {
    return this.asJsonObject().getString(key, default)
}

fun String?.getJsonObject(key: String, default: JsonObject = JsonObject()): JsonObject {
    return this.asJsonObject().getJsonObject(key, default)
}

//inline fun <reified T> String?.getList(
//    key: String,
//    default: ArrayList<T> = arrayListOf()
//): ArrayList<T> {
//    return this?.let { GsonUtil.getJsonArray(it, key, T::class.java) } ?: default
//}
//
//inline fun <reified T> String?.toList(default: ArrayList<T> = arrayListOf()): ArrayList<T> {
//    return this?.notEmptyLet { GsonUtil.toList(it, T::class.java) } ?: default
//}


/**
 * [Cursor] 扩展
 */
fun Cursor?.string(columnIndex: Int, default: String = ""): String {
    ktTry {
        return this?.getString(columnIndex) ?: default
    }
    return default
}

fun Cursor?.string(columnName: String, default: String = ""): String {
    ktTry {
        return this?.takeIf { it.count > 0 }?.getString(this.getColumnIndex(columnName).positiveOr(0)) ?: default
    }
    return default
}


/**
 * Returns the first element matching the given [predicate] after position [start], or `null` if element was not found.
 */
inline fun <T> MutableList<T>.firstOrNullAfter(start: Int, predicate: (T) -> Boolean): T? {
    if (isEmpty()) return null

    val size = this.size
    if (start > size - 1) return null

    (start until size).forEach {
        val element = this[it]
        if (predicate(element)) return element
    }

    return null
}

/**
 * Returns the first element matching the given [predicate] after position [start] index, or -1 if element was not found.
 */
inline fun <T> MutableList<T>.firstIndexOfAfter(start: Int, predicate: (T) -> Boolean): Int {
    if (isEmpty()) return -1

    val size = this.size
    if (start > size - 1) return -1

    (start until size).forEach {
        val element = this[it]
        if (predicate(element)) return it
    }

    return -1
}

/**
 * replace element at position of 'this' list on the element matching the given [predicate],
 *
 * 替换位置 [position] 元素
 *
 * 1. 如果该位置元素满足条件，替换
 * 2. 如果该位置元素不满足条件，将元素 [t] 插入该位置，且保证此插入位置之后的元素中满足给定条件[predicate] 的元素位置不动
 * （应用场景，首页 feed 流中，cms 广告插入或替换为档期广告）
 */
inline fun <T> MutableList<T>.replaceItemOnPredicate(
    position: Int,
    t: T,
    predicate: (T) -> Boolean
): Boolean {
    val size = this.size
    if (position < 0 || position >= size) {
        return false
    }

    // 该位置满足替换条件，替换
    this.elementAtOrNull(position)?.takeIf { predicate(it) }?.let {
        this[position] = t
        return true
    }

    return false
}

/**
 * insert element [t] at position if no-less-than size items, or add to the last
 * but skip those element which matching the given [skipPredicate]
 *
 * 插入元素 到位置 [position] ，或元素不足时，添加到列表末尾
 *
 * 将元素 [t] 插入该位置，且保证此插入位置之后的元素中满足给定条件 [skipPredicate] 的元素位置不动
 * （应用场景，首页 feed 流中，cms 广告插入或替换为档期广告）
 */
inline fun <T> MutableList<T>.insertAfterPositionOrTailSkipItems(
    position: Int,
    t: T,
    skipPredicate: (T) -> Boolean
): MutableList<T> {
    val size = this.size

    if (position < 0) return this
    if (position >= size) {
        this.add(t)
        return this
    }

    // 插入
    this.add(position, t)

    // 从 position + 1 位置开始查找满足条件元素，将其和其前一位置元素替换
    this.forEachIndexed { index, item ->
        if (index > position + 1 && skipPredicate(item)) Collections.swap(this, index, index - 1)
    }

    return this
}

private var mClickTimeMillis: Long = 0
fun isDoubleClickSafe(milliSeconds: Long = 800, block: () -> Unit): Boolean {
    if (System.currentTimeMillis() - mClickTimeMillis < milliSeconds) return false
    mClickTimeMillis = System.currentTimeMillis()
    block()
    return false
}
