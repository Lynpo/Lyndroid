package com.lynpo.kotlin.collection

import android.text.TextUtils
import com.lynpo.kotlin.model.Item


/**
 * CollectionPresenter
 **
 * Create by fujw on 2019-12-26.
 */
class CollectionPresenter {

    fun accessList() {
        val l = getList()
        // 安全的集合元素访问
        l?.elementAtOrNull(3)
    }

    fun filterList() {
        val l = getList()
        l?.filterNotNull()

        l?.filterNot {item ->
            TextUtils.isEmpty(item?.name) && item?.name?.length?: 0 < 0
        }

        l?.filter { item ->
            item?.name?.length?: 0 >= 0
        }
    }

    private fun getList(): ArrayList<Item?>? {
        return arrayListOf()
    }

    private fun getMap(): Map<String, Item?>? {
        return hashMapOf()
    }
}