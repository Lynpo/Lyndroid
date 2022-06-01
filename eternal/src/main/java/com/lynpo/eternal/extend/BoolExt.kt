package com.lynpo.eternal.extend


/**
 * BooleanExt, WithData, Otherwise
 *
 * 密封类：https://www.kotlincn.net/docs/reference/sealed-classes.html
 *
 * 密封类用来表示受限的类继承结构：
 * 当一个值为有限几种的类型、而不能有任何其他类型时。在某种意义上，他们是枚举类的扩展：
 * 枚举类型的值集合也是受限的，但每个枚举常量只存在一个实例，而密封类的一个子类可以有可包含状态的多个实例。
 *
 * 一个密封类是自身抽象的，它不能直接实例化并可以有抽象（abstract）成员。
 *
 * 密封类不允许有非-private 构造函数（其构造函数默认为 private）。
 *
 * 请注意，扩展密封类子类的类（间接继承者）可以放在任何位置，而无需在同一个文件中。
 *
 * 使用密封类的关键好处在于使用 when 表达式 的时候，如果能够验证语句覆盖了所有情况，就不需要为该语句再添加一个 else 子句了。
 **
 * Create by fujw on 3/11/21.
 */
sealed class BooleanExt<out T>

/**
 * 子类型 1 ——单例
 * 在条件分支语句中，所有不满足当前条件，均返回此类型
 */
object Otherwise : BooleanExt<Nothing>()

/**
 * 子类型 2
 * 在条件分支语句中，当前条件满足，执行相应代码块，返回此类型
 */
class OnCondition<T>(val data: T) : BooleanExt<T>()

inline fun <T> Boolean.yes(block: () -> T): BooleanExt<T> =
        when {
            this -> OnCondition(block())
            else -> Otherwise
        }

inline fun <T> Boolean.no(block: () -> T): BooleanExt<T> =
        when {
            this -> Otherwise
            else -> OnCondition(block())
        }

inline fun <T> BooleanExt<T>.otherwise(block: () -> T): T =
        when (this) {
            is Otherwise -> block()
            is OnCondition -> this.data
        }

inline fun <T> BooleanExt<T>.elseIf(predicate: Boolean, block: () -> T): BooleanExt<T> =
        when (this) {
            is Otherwise -> if (predicate) OnCondition(block()) else Otherwise
            is OnCondition -> this
        }