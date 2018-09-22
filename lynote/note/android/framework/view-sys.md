### View 体系与自定义 View

#### 一、坐标系

#### 二、View 的滑动

Layout() 方法

动画

CustomView.setAnimation

#### 三、View 的事件分发机制

Activity.setContentView()—> getWindow().setContentView() —> PhoneWindow.setContentView()

Window 何时和 Activity 建立联系的？

Activity.attach() —> mWindow = new PhoneWindow(this, window, activityConfigCallback);

```
PhoneWindow.setContentView(){
installDecor(){
mDecor = generateDecor(-1);
…
mContentParent = generateLayout(mDecor);
...
};
}
```

DecorView — {TitleView, ContentView}

##### 3.1 View 的事件分发机制

dispatchTouchEvent(MotionEvent ev)
onInterceptMotionEvent(MotionEvent ev), 在 dispatchTouchEvent（）中调用， View 没有提供该方法
onTouchEvent(MotionEvent ev)

点击屏幕：系统将 MotionEvent 传递

1）事件首先传递给当前 Activity, 再到 View 层级
 Activity.dispatchTouchEvent —> PhoneWindow.dispatchTouchEvent
     —> DecorView.dispatchTouchEvent--> ViewGroup.dispatchTouchEvent


##### 3.2 View 的工作流程

入口：DecorView 被加载到 Window 中

```
Activity.startActivity:
ActivityThread.handleLaunchActivity(){
Activity a = performLaunchActivity(r, customIntent);
handleResumeActivity(r.token, false, r.isForward,
        !r.activity.mFinished && !r.startsNotResumed, r.lastProcessedSeq, reason){
...
r.window = r.activity.getWindow();
View decor = r.window.getDecorView();
decor.setVisibility(View.INVISIBLE);
ViewManager wm = a.getWindowManager();
…
if (a.mVisibleFromClient) {
    if (!a.mWindowAdded) {
        a.mWindowAdded = true;
        wm.addView(decor, l); —> WindowManagerImpl.addView() —> WindowManagerGlobal.add(decorView)
    }
}
};
}

WindowManagerGlobal.add(decorView){
...
ViewRootImpl root = new ViewRootImpl(view.getContext(), display);
…
root.setView(view, wparams, panelParentView);
…
}

ViewRootImpl 的 performTraversals() : 使得 ViewTree 开始 View 的工作流程
performTraversals（）{
…
performMeasure(childWidthMeasureSpec, childHeightMeasureSpec); —> View.measuer()
…
performLayout(lp, mWidth, mHeight);—>View.layout()
…
performDraw();—>View.draw()
…

}
```


**MeasureSpec 类**

* View 的 measure 流程

 —> View.onMeasure()
 —> ViewGroup.measureChildren() —> (e.g.: LinearLayout.onMeasure())

* View 的 layout 流程

 —> View.layout() —> setFrame()
 —> LienarLayout.onLayout()—> layoutVertical() —> setChildFrame—> child.layout

* View 的 draw 流程

  1. 绘制背景
  2. 保存 canvas 层
  3. 绘制 View 内容
  4. 绘制子 View —> dispatchDraw() —> drawChild()
  5. 如果需要，绘制边缘，阴影
  6. 绘制装饰，如滚动条


#### 自定义 View
