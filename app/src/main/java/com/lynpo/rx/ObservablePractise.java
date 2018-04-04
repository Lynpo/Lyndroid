package com.lynpo.rx;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by fujw on 2018/3/30.
 * *
 * ObservablePractise
 */
public class ObservablePractise {

    public void observableInterval() {
        Observable<Long> observable = Observable.interval(1, 3, TimeUnit.SECONDS);
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                    }
                });
    }
}
