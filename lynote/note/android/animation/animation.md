### 属性动画

**API 28**

#### 1. 动画概述

 * 视图静态到动态
 * 分类：帧动画，补间动画，**属性动画**
 * 效果：渐隐渐现(Alpha)、移动(Translate)、伸缩(Scale)、旋转(Rotate) 等
 * 属性动画：背景色等属性，自定义 View 属性修改，理论上任意对象的任意属性

相关类
* View, Animation/Animator
* TimeInterpolator
* TypeEvaluator(IntEvaluator, FloatEvaluator, ArgbEvaluator)

##### 2. 动画实现过程

所需知识：View, 平面坐标系，牛顿第一、第二运动定律

 * 空间维度
 * 时间维度

###### 2.1 动画启动

时间插值器与动画协作工作：动画对象持有时间插值器的引用。

**一系列 View 的方法调用**

**绘制时间参数流向 drawingTime**
```
// 1. startAnimation 方法将动画设置到 View 中，向 ViewGroup 请求刷新视图

View.startAnimation(Animation animation)

...
// 2. ViewGroup 调用 dispatchDraw() 对 View区域重绘
dispatchDraw() --> draw()

...
applyLegacyAnimation() {

  // 初始化
  Animation.initialized()

  // 具体实现
  Animation.getTransformation(long currentTime, Transformation outTransformation){

    // ...
    // 1. 计算当前时间流失百分比
    if (duration != 0) {
            normalizedTime = ((float) (currentTime - (mStartTime + startOffset))) /
                    (float) duration;
    }

    // ...
    // 2. 通过插值器获取动画执行百分比
    final float interpolatedTime = mInterpolator.getInterpolation(normalizedTime);
    // 3. 应用动画
    applyTransformation(interpolatedTime, outTransformation);

    // ...
    // 动画完成或循环
  }
}
```

#### 3. 属性动画

**属性动画是不断重复修改对象（通常是 View 对象）的属性的过程**

**View 的几个属性**

 * "rotationX", "scaleX", "alpha", translationX
 * ...
 * any property

总体设计

![animation-design](https://github.com/Lynpo/Lyndroid/blob/develop/lynote/note/img/animation_design.png)

 * Animator 通过 PropertyValuesHolder 来更新对象的目标属性，如果用户没有设置目标属性的 Property 对象，那么会通过反射的形式调用目标属性的 setter 方法来更新属性值；否则，通过 Property 的 set 方法来设置属性值。
 * 这个属性值则通过 KeyFrameSet 的计算得到（通过动画执行百分比计算关键帧）
 * KeyFrameSet 又通过时间插值器和类型估值器来计算。


核心类：

* ValueAnimator, ObjectAnimator

 ValueAnimator 实现动画处理逻辑，ObjectAnimator 继承自 ValueAnimator, 对象属性动画的操作类

* Property, KeyframeSet

 Property 定义属性的 set/get 方法，KeyFrameSet 存储动画的关键帧集合

* PropertyValuesHolder

 持有目标属性 Property, setter, getter 方法，及关键帧集合的类

* TypeEvaluator, TimeInterpolator

 TypeEvaluator 根据当前属性改变百分比计算改变后的属性值，IntEvaluator, FloatEvaluator, ArgbEvaluator, TimeInterpolator 根据时间流逝百分比计算当前属性改变百分比。

###### 3.1 时间插值器 TimeInterpolator —— 时间维度

 **设计**

 1. 接口 Interpolator
 2. 方法 float getInterpolation(float input);
 3. 参数 input --> 时间流失参数： drawingTime  **draw() 方法提供**

   * 线性插值器（LinearInterpolator）
   * 加速减速插值器(AccelerateInterpolator, DecelerateInterpolator)

 Interpolator 类图

 ![Interpolator](https://github.com/Lynpo/Lyndroid/blob/develop/lynote/note/img/interpolator.png)

###### 3.2 关键过程

* 创建
* 设置关键帧
* 启动

实例：创建 X 轴方向伸缩动画，代码

```
val colorAnimator = ObjectAnimator.ofInt(viewTarget, "scaleX", 0.3f)
colorAnimator.duration = 1000
colorAnimator.start()
```

**源码**

创建，**关键参数流向：values**
```
// class: ObjectAnimator
public static ObjectAnimator ofInt(Object target, String propertyName, int... values) {

    // 1. 构建动画对象
    ObjectAnimator anim = new ObjectAnimator(target, propertyName);

    // 2. 设置属性
    anim.setIntValues(values);
    return anim;
}
```

设置关键帧
```
// class: ObjectAnimator
// setIntValues 方法会将属性值保存到 ValueAnimator 的成员
// HashMap<String, PropertyValuesHolder> mValuesMap 中，
// PropertyValuesHolder 保存了属性名称，setter, getter,
// 以及各时刻对应属性值（mkeyFrameSet)。

@Override
public void setIntValues(int... values) {
    if (mValues == null || mValues.length == 0) {
        // No values yet - this animator is being constructed piecemeal. Init the values with
        // whatever the current propertyName is

        // 1. 构造 IntPropertyValuesHolder
        if (mProperty != null) {
            setValues(PropertyValuesHolder.ofInt(mProperty, values));
        } else {
            setValues(PropertyValuesHolder.ofInt(mPropertyName, values));
        }
    } else {
        super.setIntValues(values);
    }
}


// class: PropertyValuesHolder
// 2. 设置动画目标值
@Override
public void setIntValues(int... values) {

    // 3. 父类方法
    super.setIntValues(values);

    // 4. 获取动画关键帧
    mIntKeyframes = (Keyframes.IntKeyframes) mKeyframes;
}

// 5. 计算当前动画
@Override
void calculateValue(float fraction) {
    mIntAnimatedValue = mIntKeyframes.getIntValue(fraction);
}
```

启动
```
private void start(boolean playBackwards) {
    // ...(略)
}
```


**3.1.1 ValueAnimator 工作流程图**

![ValueAnimator](https://github.com/Lynpo/Lyndroid/blob/develop/lynote/note/img/value_animator_flow.png)

（ObjectAnimator 工作流程图略）

###### Animator 设计图

 ![animator](https://github.com/Lynpo/Lyndroid/blob/develop/lynote/note/img/animator_design.png)

#### 4. View 属性动画的便捷使用 ViewPropertyAnimator

```
View#animate()

public ViewPropertyAnimator animate() {
    if (mAnimator == null) {
        mAnimator = new ViewPropertyAnimator(this);
    }
    return mAnimator;
}
```

上文实例实现：
```
viewTarget.animate().scaleX(0.3f).setDuration(1000);
```

#### 5. 使用示例


### 属性动画的设计

###### 1 角色设计

 * 区分时间维度，空间维度
 * 关键参数流向：动画执行时间，View 绘制时间
 * 职责规划：
  * 插值器：将动画的速率计算封装到一个抽象（Interpolator）中
  * 属性值计算：TypeEvalueator
  * 动画对象 Animator：创建，调度，启动
  * 属性管理 PropertyValuesHolder
  * 关键帧计算保存：KeyFrameSet
 * 动画分类：ScaleAnimation, RotateAnimation, TranslateAnimation,...

##### 2 策略模式

策略模式图示

![strategy pattern](https://github.com/Lynpo/Lyndroid/blob/develop/lynote/note/img/strategy_pattern.png)

属性动画中的时间插值器：TimeInterpolator

---
**附录**

参考：《Android 设计模式解析与实战》


E.O.F
