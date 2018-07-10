package com.lynpo.video

import android.view.View
import com.lynpo.video.model.ItemBean

interface ItemClickListener {
    fun clickFrom(data: ItemBean, layoutPosition: Int)
    fun clickContent(data: ItemBean, layoutPosition: Int)
    fun closeWithWindow(anchorView: View, data: ItemBean, layoutPosition: Int)
    fun clickMain(data: ItemBean, layoutPosition: Int)
    fun closeEvent()
    fun event()
    fun videoPlay(data: ItemBean, auto : Boolean)
}