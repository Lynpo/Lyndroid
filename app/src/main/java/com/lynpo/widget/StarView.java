package com.lynpo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.lynpo.R;
import com.lynpo.util.DensityUtils;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


/**
 * Create by fujw on 2018/3/28.
 * *
 * StarView
 */
public class StarView extends View {

    private Context mContet;
    private int mColor;

    public StarView(Context context) {
        this(context, null);
    }

    public StarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContet = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StarView, defStyleAttr, 0);
        mColor = array.getColor(R.styleable.StarView_path_color, ContextCompat.getColor(context, R.color.colorAccent));
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(mColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

        Path path = new Path();   float mid = getWidth() / 2;
        float min = Math.min(getWidth(), getHeight());
        float fat = min / 17;
        float half = min / 2;
        float rad = half - fat;
        mid = mid - half;

        paint.setStrokeWidth(fat);
        paint.setStyle(Paint.Style.STROKE);
        paint.setShadowLayer(DensityUtils.dp2px(mContet, 2f),
                DensityUtils.dp2px(mContet, 4f),
                DensityUtils.dp2px(mContet, 4f),
                Color.RED);

        canvas.drawCircle(mid + half, half, rad, paint);

        path.reset();

        paint.setStyle(Paint.Style.FILL);


        // top left
        path.moveTo(mid + half * 0.5f, half * 0.84f);
        // top right
        path.lineTo(mid + half * 1.5f, half * 0.84f);
        // bottom left
        path.lineTo(mid + half * 0.68f, half * 1.45f);
        // top tip
        path.lineTo(mid + half * 1.0f, half * 0.5f);
        // bottom right
        path.lineTo(mid + half * 1.32f, half * 1.45f);
        // top left
        path.lineTo(mid + half * 0.5f, half * 0.84f);

        path.close();
        canvas.drawPath(path, paint);
        super.onDraw(canvas);
    }
}
