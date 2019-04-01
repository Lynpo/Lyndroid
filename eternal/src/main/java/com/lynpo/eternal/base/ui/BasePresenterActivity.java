package com.lynpo.eternal.base.ui;

import android.os.Bundle;

import com.lynpo.eternal.base.mvp.BasePresenter;
import com.lynpo.eternal.base.mvp.IView;

import javax.inject.Inject;

import androidx.annotation.Nullable;

/**
 * BasePresenterActivity
 * *
 * Create by fujw on 2019/1/28.
 */
public class BasePresenterActivity<T extends BasePresenter> extends BaseDaggerActivity implements IView {

    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.attach(this);
        }
    }
}
