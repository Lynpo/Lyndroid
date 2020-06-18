package com.lynpo.kotlin

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.lynpo.eternal.LynConstants
import kotlinx.android.parcel.Parcelize


/**
 * NullSample
 **
 * Create by fujw on 2019-12-06.
 */
@Parcelize
class NullSample : Parcelable {

    private var name: String = ""
    private var itemName: String? = null
    private var items: ArrayList<String>? = null
    private var names: ArrayList<String?>? = null

    fun setData(data: ArrayList<String>) {
        Log.d(LynConstants.LOG_TAG, "${data?.size}")
    }

    fun addData(data: ArrayList<String>) {
        items = arrayListOf()
//        items?.add(null)  //  item to add cannot be null
        names?.add(null)
        Log.d(LynConstants.LOG_TAG, "${data?.size}")
    }
}