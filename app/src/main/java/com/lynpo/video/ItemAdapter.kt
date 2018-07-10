package com.lynpo.video

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.lynpo.video.model.ItemBean

class ItemAdapter(itemBeanPresenter: ItemBeanPresenter) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    companion object {
        private const val VIEW_TYPE_DEFAULT = 0X15
        private const val VIEW_TYPE_EMPTY = 0X17
        private const val VIEW_TYPE_VIDEO = 0x1b
    }

    private val mPresenter = itemBeanPresenter
    private var mListener : ItemClickListener? = null

    private var mCurrentPlayingItem : ItemBean? = null

    private var mPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): RecyclerView.ViewHolder {
        return VideoViewHolder.newInstance(parent)
    }

    fun getVideoViewId(): Int {
//        return R.id.item_post_video
        return VideoViewHolder.getVideoId()
    }

    fun setCurrentPlayingItem(item : ItemBean?) {
        mCurrentPlayingItem = item
    }

    fun getCurrentPlayingItem(): ItemBean? {
        return mCurrentPlayingItem
    }

    fun getItem(position: Int): ItemBean? {
        return mPresenter.getItem(position)
    }

    override fun getItemViewType(position: Int): Int {

        var index = position

        val data = mPresenter.getItem(index)

        data?.let {
            if (it.entityType == 1) {
                return if (it.isMediaVideo()) {
                    VIEW_TYPE_VIDEO
                } else {
                    VIEW_TYPE_DEFAULT
                }
            } else if (it.entityType == VIEW_TYPE_VIDEO && it.isMediaVideo()) {
                return VIEW_TYPE_VIDEO
            } else {
                return VIEW_TYPE_DEFAULT
            }

        }
        return VIEW_TYPE_EMPTY
    }

    override fun getItemCount(): Int = mPresenter.getItemCount()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var index = position

        if (holder is BaseViewHolder){
            holder.setListener(mListener)
            holder.position = index
            holder.setPresenter(mPresenter)
            holder.bindData(mPresenter.getItem(index))
        }
    }

    fun setPosition(mPosition: Int) {
        this.mPosition = mPosition
    }

    fun setListener(listener: ItemClickListener) {
        this.mListener = listener
    }


}