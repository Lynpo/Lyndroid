### 属性动画

#### 1. 动画概述

方式：渐隐渐现(Alpha)、移动(Translate)、伸缩(Scale)、旋转(Rotate)

相关对象：
* View, Animation(AnimationListener)
* TimeInterpolator, TypeValuator(IntEvaluator, FloatEvaluator, ArgbEvaluator)

##### 2. 动画实现过程

所需知识  
View, 平面坐标系，牛顿第一、第二运动定律

###### 2.1 时间插值器 TimeInterpolator

* 线性插值器（LinearInterpolator）
* 加速减速插值器(AccelerateInterpolator, DecelerateInterpolator)

###### 2.2 动画启动

```
View.startAnimation(Animation animation)
...
dispatchDraw() --> draw()
...
applyLegacyAnimation() {
  // 初始化
  Animation.initialized()
  // 具体实现
  Animation.getTransformation()
  // 子类实现
  Animation.applyTransformation()
}
```

#### 3. 属性动画

相关类：
* ValueAnimator, ObjectAnimator, TimeInterpolator
* TypeValuator, PropertyValuesHolder, KeyframeSet

###### 3.0 View 属性

 * "rotateX", "rotateY", "rotation"
 * "scaleX", "scaleY"
 * "alpha"
 * "translationX", "translationY"

###### 3.1 过程

* 创建
* 设置关键帧
* 启动

```
val colorAnimator = ObjectAnimator.ofInt(target, "scaleX", 0.3f)
colorAnimator.duration = 1000
colorAnimator.start()
```

###### Animator 相关类图

 ![animator](https://github.com/Lynpo/Lyndroid/blob/develop/lynote/note/img/animator-uml.png)


#### 4. 使用示例



E.O.F
