package com.lynpo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.lynpo.R;


/**
 * Create by fujw on 2018/3/28.
 * *
 * StarView
 */
public class CornerShape extends View {

    private int mPathColor;

    public CornerShape(Context context) {
        this(context, null);
    }

    public CornerShape(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CornerShape(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CornerShape, defStyleAttr, 0);
        mPathColor = array.getColor(R.styleable.CornerShape_paint_color, ContextCompat.getColor(context, R.color.colorAccent));
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path p = new Path();
        Paint paintN = new Paint();
        paintN.setAntiAlias(true);
        paintN.setStyle(Paint.Style.FILL_AND_STROKE);
//        paintN.setColor(Color.YELLOW);
        paintN.setColor(mPathColor);
        p.moveTo(40, 60);
        p.quadTo(40, 40, 60, 40);
        p.lineTo(180, 40);
        p.quadTo(200, 40, 200, 60);
        p.lineTo(200, 90);
        p.lineTo(40, 200);
        p.close();
        canvas.drawPath(p, paintN);
        super.onDraw(canvas);
    }
}
