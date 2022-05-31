package com.lynpo.view.shape;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;


/**
 * TriangleShapeView
 * *
 * Create by fujw on 2019-05-31.
 */
public class TriangleShapeView extends View {

    Path path;
    Paint p;

    public TriangleShapeView(Context context) {
        this(context, null);
    }

    public TriangleShapeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TriangleShapeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        path = new Path();
        p = new Paint();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = getWidth();
        float h = getHeight();
        float maxHeight = w * (float)Math.tan(Math.PI / 4) / 2;
        h = h > maxHeight ? maxHeight : h;

        path.moveTo(0, 0);
        path.lineTo(w , 0);
        path.lineTo(w / 2f , h);
        path.close();

        p.setColor(Color.RED);

        canvas.drawPath(path, p);
    }
}
