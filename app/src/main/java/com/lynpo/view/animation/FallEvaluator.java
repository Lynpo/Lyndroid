package com.lynpo.view.animation;

import android.animation.TypeEvaluator;


/**
 * PointEvaluator
 * *
 * Create by fujw on 2019-05-15.
 */
public class FallEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());
        return new Point(x, y);
    }
}
