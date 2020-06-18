package com.lynpo.kotlin.classobject

import com.lynpo.kotlin.model.Jack


/**
 * ClassObjectPresenter
 **
 * Create by fujw on 2019-12-23.
 */
class ClassObjectPresenter {

    fun objectMethodsLinkUse() {
        val jack = Jack()
        println("name:${jack.name}, age: ${jack.age}, weight: ${jack.weight}, job: ${jack.work}")
        jack.changeName("John")
        with(jack) {
            addAge(2)
            growWeight(10)
            changeWork("Codding")
        }
        println("Jack new name:${jack.name}, new age: ${jack.age}, weight: ${jack.weight}, new job: ${jack.work}")

//        jack.addAge(2)
        with(jack) {
//            addAge(3)
            growHeight(5)
            changeWork("Cooking")
            growWeight2(10)
        }
//        jack.age = 22
        println("Jack new name:${jack.name}, new age: ${jack.age}, height: ${jack.height}, weight: ${jack.weight}, new job: ${jack.work}")

        jack.doSomethingWith("Lily")
    }

    private fun Jack.doSomethingWith(otherName: String) {
        println("$name visited $otherName last night")
    }
}