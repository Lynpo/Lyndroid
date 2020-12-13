package com.lynpo.kotlin.generic


/**
 * GenericSample
 **
 * Create by fujw on 2020/6/18.
 *
 * https://www.kotlincn.net/docs/reference/generics.html
 */
class GenericSample {

    fun copy(from: Array<Any>, to: Array<Any>) {
        copyNotChangeFrom(from, to)
    }

    /**
     * 这就是我们的使用处型变的用法，并且是对应于 Java 的 Array<? extends Object>、 但使用更简单些的方式。
     */
    fun copyNotChangeFrom(from: Array<out Any>, to: Array<Any>) {
//        assert(from.size == to.size)
        for (i in from.indices) {
            to[i] = from[i]
        }
    }

    /**
     * Array<in String> 对应于 Java 的 Array<? super String>，
     * 也就是说，你可以传递一个 CharSequence 数组或一个 Object 数组给 fill() 函数。
     */
    fun fill(dest: Array<in String>, value: String) {

    }

    fun copyTest() {
        val fromAny: Array<Any> = arrayOf(1, 2, 3)
        val ints: Array<Int> = arrayOf(1, 2, 3)
        val any = Array<Any>(3){ "" }
        copy(fromAny, any) // working
//        copy(ints, any) // not working
        copyNotChangeFrom(ints, any)    // ok to use

        val strArray: Array<String> = arrayOf("1", "2", "3")
        val charSequenceArray: Array<CharSequence> = arrayOf("1", "2", "3")
        fill(strArray, "4")
        fill(charSequenceArray, "4")
        fill(any, "4")
    }
}