package com.lynpo.view.animation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;


/**
 * BallView
 * *
 * Create by fujw on 2019-05-15.
 */
public class BallView extends View {

    public static final float RADIUS = 50f;

    private Point mPoint;
    private Paint mPaint;

    public BallView(Context context) {
        this(context, null);
    }

    public BallView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPoint == null) {
            int height = getHeight();
            mPoint = new Point(RADIUS, height / 2f);
            drawCircle(canvas);
//            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    public void startAnimation() {
        int width = getWidth();
        int height = getHeight();
        Point startPoint = new Point(RADIUS, height / 2f);
        Point endPoint = new Point(width - RADIUS, height / 2f);
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(width, height / 2f), startPoint, endPoint);
        anim.addUpdateListener(animation -> {
            mPoint = ((Point) animation.getAnimatedValue());
            invalidate();
        });
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(5000);
        anim.start();
    }

    private void drawCircle(Canvas canvas) {
        float x = mPoint.getX();
        float y = mPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }
}
