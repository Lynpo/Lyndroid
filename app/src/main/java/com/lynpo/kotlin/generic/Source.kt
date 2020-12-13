package com.lynpo.kotlin.generic


/**
 * Source
 **
 * Create by fujw on 2020/6/18.
 */
interface Source<out T, in P> {
    fun next(): T   // T is for out
//    fun compute(t: T) // not allowed: Type parameter T is declared as 'out' but occurs in 'in' position in type T
    fun compute(t: P)   // P is for in, consume
}