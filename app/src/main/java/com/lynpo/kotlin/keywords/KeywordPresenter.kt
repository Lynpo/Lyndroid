package com.lynpo.kotlin.keywords

import com.lynpo.kotlin.model.Jack


/**
 * KeywordPresenter
 **
 * Create by fujw on 2019-12-16.
 */
class KeywordPresenter {

    fun ifElse(name: String) {
        if(name.length > 10){
            println(name)
        } else if (name.length > 5) {
            println(name)
        }
    }

    fun ifElseWhen(name: String) {
        when {
            name.length > 10 -> {
                println(name)
            }
            name.length > 5 -> {
                println(name)
            }
            name.length > 3 -> {
                println(name)
            }
            else -> {
                println(name)
            }
        }
    }

    fun rangeCase(name: String) {
        for (i in 1..10) {}  // closed range: includes 10
        for (i in 1 until 10) {} // half-open range: does not include 10

        println("2..10 step 2")
        for (x in 2..10 step 2) {
            println(x)
        }
        println("10 downTo 1")
        for (x in 10 downTo 1) {
            println(x)
        }

        val x = 0
        val y = 1
        val z = 10
        val valueX = if (x in 1..10) "$x in 1..10" else "$x not in 1..10"
        val valueY = if (y in 1..10) "$y in 1..10" else "$y not in 1..10"
        val valueZ = if (z in 1..10) "$z in 1..10" else "$z not in 1..10"
        println("valueX:$valueX")
        println("valueY:$valueY")
        println("valueZ:$valueZ")
    }

    fun whenWithFunction(num: Int): Int = when {
        (num > 100) -> 200
        (num > 50) -> 100
        (num > 10) -> 30
        else -> 0
    }

    fun letDiffRun(num: Int) {
        val jack = Jack()
        jack.let {
            println(it.weight)
            0
        }
        jack.run {
            println(weight)
        }
    }
}