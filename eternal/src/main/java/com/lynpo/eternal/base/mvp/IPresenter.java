package com.lynpo.eternal.base.mvp;

/**
 * BasePresenter
 * *
 * Create by fujw on 2019/1/21.
 */
public interface IPresenter<V extends IView> {

    void attach(V view);
    void detach();
}
