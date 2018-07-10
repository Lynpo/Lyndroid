package com.lynpo.video;

import android.net.Uri;

import java.util.Map;

/**
 * Create by fujw on 2018/7/2.
 * *
 * Media
 */
public abstract class Media {

    public int type;
    public long size;
    public long duration;

    public Uri getUri() {
        return null;
    }

    public String getUrl() {
        return null;
    }

    public abstract String getMediaUrl();
    public abstract Map<String, String> getHeaders();
}
