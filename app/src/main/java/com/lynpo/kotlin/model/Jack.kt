package com.lynpo.kotlin.model


/**
 * Jack
 **
 * Create by fujw on 2019-12-23.
 */
class Jack {

    var name: String? = "Jack"
    var age: Int = 18
    var height: Int = 180
    var weight: Int? = 80
    var work: String? = "Dancing"

    fun changeName(name: String) {
        this.name = name
    }

    fun addAge(num: Int) {
        val old = age
        age = old + num
    }

    fun growHeight(inch: Int) {
        height += inch
    }

    fun growWeight(pounds: Int) {
        weight = weight?: 80 + pounds
    }

    fun growWeight2(pounds: Int) {
        weight = weight?.let {
            var value = 0
            for (n in 0..pounds) {
                value++
            }
            it + value
        }
    }

    fun changeWork(job: String) {
        work = job
    }
}