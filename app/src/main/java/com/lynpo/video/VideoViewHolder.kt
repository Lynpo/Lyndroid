package com.lynpo.video

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.lynpo.R
import com.lynpo.video.model.ItemBean

class VideoViewHolder(itemView: View) : BaseViewHolder(itemView) {

    private var mContext : Context

    private var mFromTV: TextView
    private var mTitleTV:TextView
    private var mContentTV:TextView
    private var mPostVideo: JZVideoPlayerStandard
    private var mAvatarImg:ImageView

    companion object {
        fun newInstance(parent: ViewGroup) : VideoViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_new_video, parent, false)
            return VideoViewHolder(view)
        }

        @JvmStatic
        fun getVideoId(): Int {
            return R.id.item_post_video
        }
    }

    init {
        mFromTV = itemView.findViewById<View>(R.id.item_from_tv) as TextView
        mTitleTV = itemView.findViewById<View>(R.id.item_post_title_tv) as TextView
        mContentTV = itemView.findViewById<View>(R.id.item_post_content_tv) as TextView
        mPostVideo = itemView.findViewById<View>(R.id.item_post_video) as JZVideoPlayerStandard
        mAvatarImg = itemView.findViewById<View>(R.id.item_avatar_iv) as ImageView

        mContext = itemView.context
    }

    override fun bindData(data: ItemBean?) {

        data?.let {
            mFromTV.text = it.nickname

            setText(mTitleTV, data.title)

            when {
                it.isMediaVideo() -> {
                    mPostVideo.visibility = View.VISIBLE
                    mPostVideo.setVideoSize(it.getVideoSize())
                    if (it.entityType == PlayerConst.TYPE_OPEN_COURSE) {
                        mPostVideo.setOpenCourseUrl(it.entityId, it.url)
                    }
                    mPostVideo.setUp(it.getVideoUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_LIST)
                    JZVideoPlayer.setScreenMode(ScreenMode.LIST)
                    mPostVideo.setVideoDuration(it.getVideoDurationMs())

                    if (!TextUtils.isEmpty(it.getVideoCoverUrl())) {
                        Glide.with(mContext)
                                .load(it.getVideoCoverUrl())
                                .listener(object : RequestListener<Drawable> {
                                    override fun onLoadFailed(e: GlideException?, o: Any, target: Target<Drawable>, b: Boolean): Boolean {
//                                        mPostVideo.thumbImageView.setBackgroundResource(R.drawable.img_load)
                                        return false
                                    }

                                    override fun onResourceReady(drawable: Drawable, o: Any, target: Target<Drawable>, dataSource: DataSource, b: Boolean): Boolean {
                                        return false
                                    }
                                })
                                .into(mPostVideo.thumbImageView)
                        mPostVideo.showCoverImage(true)
                    } else {
//                        mPostVideo.thumbImageView.setImageResource(R.drawable.img_load)
                        mPostVideo.showCoverImage(true)
                    }

                    mPostVideo.setOnClickListener{
                        val tag = mPostVideo.tag
                        if (tag != null) {
                            mPostVideo.tag = null
                            mPostVideo.clickStart(tag)
                            mPostVideo.mute()
                            mItemListener?.videoPlay(data, true)
                        } else {
                            mPostVideo.fullScreenPlayAndCheckingWifi()
                            mItemListener?.videoPlay(data, false)
                        }
                        mPostVideo.showCoverImage(false)
                    }
                }
            }

            mItemListener?.let {
                val listener = it
                mFromTV.setOnClickListener { listener.clickFrom(data, layoutPosition) }
                mAvatarImg.setOnClickListener{listener.clickFrom(data, layoutPosition) }
                itemView.setOnClickListener { listener.clickContent(data,layoutPosition)}
            }
        }
    }

    private fun setText(textView: TextView, context: String) {
        if (TextUtils.isEmpty(context)) {
            textView.visibility = View.GONE
        } else {
            textView.visibility = View.VISIBLE
            textView.text = context
        }
    }
}