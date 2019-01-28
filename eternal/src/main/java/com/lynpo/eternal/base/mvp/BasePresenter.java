package com.lynpo.eternal.base.mvp;

/**
 * BasePresenter
 * *
 * Create by fujw on 2019/1/21.
 */
public class BasePresenter<V extends IView> implements IPresenter<V> {

    protected V mView;

    @Override
    public void attach(V view) {
        mView = view;
    }

    @Override
    public void detach() {

    }
}
