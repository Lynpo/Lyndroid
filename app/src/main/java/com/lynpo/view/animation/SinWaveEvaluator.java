package com.lynpo.view.animation;

import android.animation.TypeEvaluator;


/**
 * SinWaveEvaluator
 * *
 * Create by fujw on 2019-05-15.
 */
public class SinWaveEvaluator implements TypeEvaluator {

    private float mWidth;
    private float mHeight;

    public SinWaveEvaluator(float width, float height) {
        mWidth = width;
        mHeight = height;
    }

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = (float) ((mHeight - BallView.RADIUS) * Math.sin(4 * Math.PI * x / mWidth)) + mHeight;
        return new Point(x, y);
    }
}
