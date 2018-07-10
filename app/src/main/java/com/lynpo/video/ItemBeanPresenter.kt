package com.lynpo.video

import com.lynpo.video.model.ItemBean
import java.util.*

class ItemBeanPresenter {

    private val mData = ArrayList<ItemBean>()
    internal var isLoadMore = false

    fun loadData(refreshing : Boolean,loadMore : Boolean) {
        isLoadMore = loadMore

        val videoBeans = ArrayList<VideoBean>()
        val bean1 = VideoBean()
        val bean2 = VideoBean()
        val bean3 = VideoBean()
        val bean4 = VideoBean()
        videoBeans.add(bean1)
        videoBeans.add(bean2)
        videoBeans.add(bean3)
        videoBeans.add(bean4)
    }

    fun getItem(position: Int): ItemBean? {
        return if (position>=0 && position < mData.size) {
            mData[position]
        } else null
    }

    fun getItemCount(): Int {
        return mData.size
    }

}