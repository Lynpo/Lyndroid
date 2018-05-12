package com.lynpo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lynpo.util.DensityUtils;


/**
 * Create by fujw on 2018/5/11.
 * *
 * DropDownBubble
 */
public class DropDownBubble extends View {

    private static final int MIN_SPACE_IN_DP = 4;

    private Context mContext;

    public DropDownBubble(Context context) {
        this(context, null);
    }

    public DropDownBubble(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropDownBubble(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int arrow_side_len = DensityUtils.dp2px(mContext, 10f);
        float arrow_rate_of_x = 4f / 5;
        int corner_radius = DensityUtils.dp2px(mContext, 5F);

        float min_width = corner_radius * 2
                + arrow_side_len * 1.25f
                + DensityUtils.dp2px(mContext, MIN_SPACE_IN_DP);

        if (width < min_width) {
            throw new IllegalArgumentException("Width less than min_width(in pixel): " + min_width);
        }

        float arrow_left_x = width * arrow_rate_of_x - arrow_side_len;
        float arrow_top_x = width * arrow_rate_of_x - arrow_side_len * 0.5f;
        float arrow_right_x = width * arrow_rate_of_x;

        Paint paint = new Paint();
//        paint.setColor(Color.WHITE);
        paint.setColor(Color.parseColor("#2D8566"));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        Path path = new Path();
        path.moveTo(0, arrow_side_len + corner_radius);
        path.quadTo(0, arrow_side_len, corner_radius, arrow_side_len);
        path.lineTo(arrow_left_x, arrow_side_len);
        path.lineTo(arrow_top_x, 0);
        path.lineTo(arrow_right_x, arrow_side_len);
        path.lineTo(width - corner_radius, arrow_side_len);
        path.quadTo(width, arrow_side_len, width, arrow_side_len + corner_radius);
        path.lineTo(width, height - corner_radius);
        path.quadTo(width, height, width - corner_radius, height);
        path.lineTo(corner_radius, height);
        path.quadTo(0, height, 0, height - corner_radius);
        path.close();

        canvas.drawPath(path, paint);

        super.onDraw(canvas);
    }
}
