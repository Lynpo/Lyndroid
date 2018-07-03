package com.lynpo.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.StatFs;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;

/**
 * Created by fujw on 2016/12/20.
 * <p>
 * 用于 Android API level 不同而需要访问不同方法的工具类
 */

public class AndroidAPIUtil {

    public static boolean getApplicationInfo(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                context.getPackageManager().getApplicationInfo(
                        packageName, PackageManager.MATCH_UNINSTALLED_PACKAGES);
                return true;
            } else {
                context.getPackageManager().getApplicationInfo(
                        packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static Spanned fromHtml(String text) {
        Spanned spanned;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            spanned = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            spanned = Html.fromHtml(text);
        }
        return spanned;
    }

    public static void setStatusBarColor(Window window, int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(window.getContext(), colorResId));
        }
    }

    public static void setStatusBarColor(Activity activity, int colorResId) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(window.getContext(), colorResId));
        }
    }

    public static void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置内容布局属性
            ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
            contentLayout.setFitsSystemWindows(true);
            contentLayout.setClipToPadding(true);
            // 设置抽屉布局属性
            ViewGroup vg = (ViewGroup) drawerLayout.getChildAt(1);
            vg.setFitsSystemWindows(false);
            // 设置 DrawerLayout 属性
            drawerLayout.setFitsSystemWindows(false);
        }
    }

    public static long getBlockSize(File file) {
        StatFs stat = new StatFs(file.getPath());
        long blockSize;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
        } else {
            blockSize = stat.getBlockSize();
        }
        return blockSize;
    }

    public static long getAvailableBlocks(File file) {
        StatFs stat = new StatFs(file.getPath());
        long availableBlocks;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            availableBlocks = stat.getAvailableBlocks();
        }
        return availableBlocks;
    }
}
