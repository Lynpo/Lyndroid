package com.lynpo.video.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class SPUtil {

    private static SharedPreferences sp;
    private static final String SP_NAME = "video_sp";

    /**
     * 写入 int 类型 value
     *
     * @param key 键
     * @param value 值
     */
    public static void putInt(Context context, String key, int value) {
        if(sp == null) {
            sp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        SharedPreferencesUtil.putInt(sp,key,value);
    }

    /**
     * 读取 int
     *
     * @param key 键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值
     */
    public static int getInt(Context context, String key, int defaultValue) {
        if(sp == null) {
            sp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return SharedPreferencesUtil.getInt(sp,key, defaultValue);
    }

    /**
     * 获取所有键值对
     *
     * @return Map对象
     */
    public static Map<String, ?> getAll(Context context) {
        if(sp == null) {
            sp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getAll();
    }

    /**
     * 移除某个 key
     *
     * @param key 键
     */
    public static void remove(Context context, String key) {
        if(sp == null) {
            sp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        SharedPreferencesUtil.remove(sp,key);
    }

    /**
     * 是否存在某个 key
     *
     * @param key 键
     * @return 是否存在
     */
    public static boolean contains(Context context, String key) {
        if(sp == null) {
            sp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return SharedPreferencesUtil.contains(sp,key);
    }

    /**
     * 清除所有数据
     */
    public static void clear(Context context) {
        if(sp == null) {
            sp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        SharedPreferencesUtil.clearConfig(sp);
    }

}
