package com.lynpo.view.shape;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lynpo.R;


/**
 * TipTextView
 * *
 * Create by fujw on 2019-05-31.
 */
public class TipTextView extends FrameLayout {

    Context mContext;
    TextView mTextView;
//    TriangleShapeView mTriangleShapeView;

    public TipTextView(Context context) {
        this(context, null);
    }

    public TipTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TipTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        inflate(mContext, R.layout.view_tip_text, this);

        mTextView = findViewById(R.id.text);
    }

    public void setText(CharSequence text) {
        mTextView.setText(text);
    }

    public void setTextSize(float spSize) {
        mTextView.setTextSize(spSize);
    }

    public void setTextColor(int color) {
        mTextView.setTextColor(color);
    }

    public void setTextBackground(Drawable drawable) {
        mTextView.setBackground(drawable);
    }

    public void setTextBackgroundColor(int color) {
        mTextView.setBackgroundColor(color);
    }
}
