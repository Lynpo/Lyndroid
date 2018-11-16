package com.lynpo.thdlibs.imageload;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ImageLoader {

    public static void loadCircleImage(Activity activity , String url , ImageView view) {
        Glide.with(activity).load(url).apply(RequestOptions.circleCropTransform()).into(view);
    }

    public static void loadCircleImage(android.support.v4.app.Fragment fragment , String url , ImageView view) {
        Glide.with(fragment).load(url).apply(RequestOptions.circleCropTransform()).into(view);
    }

    public static void loadCircleImage(Context context , String url , ImageView view) {
        Glide.with(context).load(url).apply(RequestOptions.circleCropTransform()).into(view);
    }

    public static void loadImage(Fragment fragment , String url , ImageView view) {
        Glide.with(fragment).load(url).into(view);
    }

    public static void loadImage(Fragment fragment , String url , ImageView view, @DrawableRes int placeHolder ) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(placeHolder);
        Glide.with(fragment).load(url).apply(requestOptions).into(view);
    }

    public static void loadImage(Context context , String url , ImageView view) {
        Glide.with(context).load(url).into(view);
    }

    public static void loadRoundImage(Context context , String url , ImageView view , int radius) {
        RequestOptions myOptions = new RequestOptions().transform(new GlideRoundTransform(context,radius));
        Glide.with(context).asBitmap().load(url).apply(myOptions).into(view);

    }

    public static void loadImageAsBitmap(Context context , String url , ImageView view) {
        Glide.with(context).asBitmap().load(url).into(view);
    }

}
