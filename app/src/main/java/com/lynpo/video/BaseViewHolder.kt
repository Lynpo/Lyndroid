package com.lynpo.video

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.lynpo.video.model.ItemBean

abstract class BaseViewHolder (itemView : View): RecyclerView.ViewHolder(itemView) {

    protected lateinit var mPresenter: ItemBeanPresenter

    protected  var  mData : ItemBean? = null
    protected  var mItemListener : ItemClickListener? = null

    protected var mPosition: Int = 0

    abstract fun bindData(data: ItemBean?)

    fun setPresenter(presenter: ItemBeanPresenter) {
        mPresenter = presenter
    }

    fun setListener(listener: ItemClickListener?) {
        mItemListener = listener
    }

    fun setPosition(mPosition: Int) {
        this.mPosition = mPosition
    }
}