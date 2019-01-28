package com.lynpo.thdlibs.dagger2.sample;

import android.content.SharedPreferences;

import com.lynpo.eternal.base.mvp.BasePresenter;

import javax.inject.Inject;

/**
 * SamplePresenter
 * *
 * Create by fujw on 2019/1/21.
 */
public class SamplePresenter extends BasePresenter<SampleContract.View> implements SampleContract.Presenter {

    private final static String SP_KEY = "_name";

    private final SampleModel model;

    @Inject
    SharedPreferences sp;

    @Inject
    SamplePresenter(SampleModel model) {
        this.model = model;
    }

    @Override
    public void setName(String name) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SP_KEY, name);
        editor.apply();
    }

    @Override
    public String getName() {
        return sp.getString(SP_KEY, "nemo");
    }
}
