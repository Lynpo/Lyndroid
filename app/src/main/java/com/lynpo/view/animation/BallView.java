package com.lynpo.view.animation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;

import com.lynpo.R;

import androidx.annotation.Nullable;


/**
 * BallView
 * *
 * Create by fujw on 2019-05-15.
 */
public class BallView extends View {

    public static final float RADIUS = 50f;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private Point mPoint;
    private Paint mPaint;

    private int mOrientation;

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

        obtainAttributes(context, attrs);
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BallView);
        mOrientation = typedArray.getInt(R.styleable.BallView_orientation, 0);

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPoint == null) {
            int width = getWidth();
            int height = getHeight();
            if (mOrientation == VERTICAL) {
                mPoint = new Point(width / 2f, RADIUS);
            } else {
                mPoint = new Point(RADIUS, height / 2f);
            }
            drawCircle(canvas);
        } else {
            drawCircle(canvas);
        }
    }

    public void startAnimation() {
        int width = getWidth();
        int height = getHeight();
        Point startPoint = new Point(RADIUS, height / 2f);
        Point endPoint = new Point(width - RADIUS, height / 2f);
        ValueAnimator anim = ValueAnimator.ofObject(new SinWaveEvaluator(width, height / 2f), startPoint, endPoint);
        anim.addUpdateListener(animation -> {
            mPoint = ((Point) animation.getAnimatedValue());
            invalidate();
        });
        anim.setInterpolator(new AccelerateInterpolator());
        anim.setDuration(5000);
        anim.start();
    }

    public void startFallAnimation() {
        int width = getWidth();
        int height = getHeight();
        Point startPoint = new Point(width / 2f, RADIUS);
        Point endPoint = new Point(width / 2f, height - RADIUS);
        ValueAnimator anim = ValueAnimator.ofObject(new FallEvaluator(), startPoint, endPoint);
        anim.addUpdateListener(animation -> {
            mPoint = ((Point) animation.getAnimatedValue());
            invalidate();
        });
        anim.setInterpolator(new BounceInterpolator());
//        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(5000);
        anim.start();
    }

    private void drawCircle(Canvas canvas) {
        float x = mPoint.getX();
        float y = mPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }
}
