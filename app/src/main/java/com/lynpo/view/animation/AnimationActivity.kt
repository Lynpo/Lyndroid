package com.lynpo.view.animation

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import com.lynpo.R
import com.lynpo.eternal.base.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_animation.*

class AnimationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        btnAnimation.setOnClickListener {
            displayMultiAnimation()
        }

        ballAnimation.setOnClickListener {
            displayBallAnimation()
        }

        fallAnimation.setOnClickListener {
            displayFallAnimation()
        }
    }

    private fun displayBallAnimation() {
        ballView.startAnimation()
    }

    private fun displayFallAnimation() {
        ballViewFall.startFallAnimation()
    }

    override fun onResume() {
        super.onResume()

        displayColorAnimation()
    }

    private fun displayColorAnimation() {
        val target = tvAnimation
        val colorAnimator = ObjectAnimator.ofInt(target, "backgroundColor", 0xFFFF8080.toInt(), 0xFF8080FF.toInt())
        colorAnimator.duration = 3000
        colorAnimator.setEvaluator(ArgbEvaluator())
        colorAnimator.repeatCount = ValueAnimator.INFINITE
        colorAnimator.repeatMode = ValueAnimator.REVERSE
//        colorAnimator.repeatMode = ValueAnimator.RESTART
        colorAnimator.start()
    }

    private fun displayMultiAnimation() {
        val animSet = AnimatorSet()
        animSet.playTogether(
//                ObjectAnimator.ofFloat(tvAnimationSet, "rotateX", 0f, 360f),
//                ObjectAnimator.ofFloat(tvAnimationSet, "rotateY", 0f, 180f),
//                ObjectAnimator.ofFloat(tvAnimationSet, "rotation", 0f, -720f),
//                ObjectAnimator.ofFloat(tvAnimationSet, "translationX", 0f, 90f),
//                ObjectAnimator.ofFloat(tvAnimationSet, "translationY", 0f, 90f),
//                ObjectAnimator.ofFloat(tvAnimationSet, "scaleX", 1f, 1.5f),
//                ObjectAnimator.ofFloat(tvAnimationSet, "scaleY", 1f, 0.5f),
//                ObjectAnimator.ofFloat(tvAnimationSet, "alpha", 1f, 0.25f, 1f),
                ObjectAnimator.ofFloat(tvAnimation, "translationX", 0f, 90f, 0f, 100f, 0f, 150f, 0f, 200f),
                ObjectAnimator.ofFloat(tvAnimation, "translationY", 0f, 400f),
                ObjectAnimator.ofFloat(tvAnimationSet, "translationX", 0f, 90f, 0f, 100f, 0f, 150f, 0f, 200f),
                ObjectAnimator.ofFloat(tvAnimationSet, "translationY", 0f, 400f)
        )

        animSet.setDuration(5 * 1000).start()
    }
}
