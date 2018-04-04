package com.lynpo.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationUtils;

import com.lynpo.R;

public class MoveViewActivity extends AppCompatActivity {

    CustomView customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_view);

        customView = findViewById(R.id.customView);
        customView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.translate));

        // 属性动画
        // 帧动画， View 动画
        // View 动画：AlphaAnimation, RotateAnimation, TranslateAnimation, ScaleAnimation
        // Android 3.0 退出属性动画：

        // Animator 框架中使用最多：AnimatorSet, ObjectAnimator
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(customView, "translationX", 200);
        objectAnimator.setDuration(300);
        objectAnimator.start();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 100);
        valueAnimator.setTarget(customView);
        valueAnimator.setDuration(1000).start();

//        AnimatorSet.Builder builder = new AnimatorSet.Builder()
//                .after(100)
//                .before(valueAnimator)
//                .with(objectAnimator);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animation.getAnimatedValue();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MoveViewActivity.class));
    }
}
