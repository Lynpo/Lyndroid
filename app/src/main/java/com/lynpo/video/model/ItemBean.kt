package com.lynpo.video.model

import android.text.TextUtils
import com.lynpo.video.Media

class ItemBean {

    var entityId = ""
    var entityType = 0
    var nickname = ""
    var title = ""
    var content = ""
    var url = ""
    var media: List<Media> = arrayListOf()

    var adapterPosition: Int = 0

    companion object {
        const val MEDIA_TYPE_IMAGE = 1
        const val MEDIA_TYPE_VIDEO = 2
    }

    fun getVideoCoverUrl() : String?{
        var cover : String? = ""
        var image : String? = ""
        if(!media.isEmpty()){
            for (m in media){
                if (m.type == MEDIA_TYPE_VIDEO && !TextUtils.isEmpty(m.url)) {
                    cover = m.url
                    break
                }
                if (m.type == MEDIA_TYPE_IMAGE && !TextUtils.isEmpty(m.url)) {
                    image = m.url
                }
            }
        }
        return if (!TextUtils.isEmpty(cover)) cover else image
    }

    fun isMediaVideo() : Boolean{
        if(!media.isEmpty()){
            for (m in media){
                if (m.type == MEDIA_TYPE_VIDEO && !TextUtils.isEmpty(m.mediaUrl))
                    return true
            }
        }
        return false
    }

    fun getVideoUrl() : String{
        if(!media.isEmpty()){
            for (m in media){
                if (m.type == MEDIA_TYPE_VIDEO && !TextUtils.isEmpty(m.mediaUrl))
                    return m.mediaUrl
            }
        }
        return ""
    }

    fun getVideoSize() : String?{
        if(!media.isEmpty()){
            for (m in media){
                if (m.type == MEDIA_TYPE_VIDEO && !TextUtils.isEmpty(m.mediaUrl)) {
                    val size: Long = m.size / 1024 / 1024
                    return size.toString()
                }
            }
        }
        return ""
    }

    fun getVideoDurationMs() : Long{
        if(!media.isEmpty()){
            for (m in media){
                if (m.type == MEDIA_TYPE_VIDEO && !TextUtils.isEmpty(m.mediaUrl)) {
                    return m.duration * 1000
                }
            }
        }
        return 0
    }
}