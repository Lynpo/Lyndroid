package com.lynpo.thdlibs.dagger2.sample;

import com.lynpo.eternal.base.mvp.IPresenter;
import com.lynpo.eternal.base.mvp.IView;

/**
 * MainContract
 * *
 * Create by fujw on 2019/1/21.
 */
public interface SampleContract {

    interface View extends IView {

    }

    interface Presenter extends IPresenter<View> {
        void setName(String name);
        String getName();
    }

    interface Model {

    }
}
