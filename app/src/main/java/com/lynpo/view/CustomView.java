package com.lynpo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;


/**
 * Create by fujw on 2018/4/1.
 * *
 * CustomView
 */
public class CustomView extends View {

    private int lastX;
    private int lastY;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void move() {
        offsetTopAndBottom(100);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offX = x - lastX;
                int offY = y - lastY;
                // 移动View
                // 方案一
//                layout(getLeft() + offX, getTop() + offY, getRight() + offX, getBottom() + offY);
                // 方案二
                offsetLeftAndRight(offX);
                offsetTopAndBottom(offY);
                // 方案三  // 测试没生效
//                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
//                layoutParams.leftMargin = getLeft() + offX;
//                layoutParams.topMargin = getTop() + offY;
                break;
        }
        return true;
    }
}
