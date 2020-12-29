package com.lynpo

import android.app.Activity
import android.app.Application
import android.content.Context
import com.lynpo.thdlibs.dagger2.component.ActivityComponent
import com.lynpo.thdlibs.dagger2.component.DaggerAppComponent
import com.lynpo.thdlibs.dagger2.component.SwordmanComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * Create by fujw on 2018/4/1.
 * *
 * LynpoApp
 */
class LynpoApp : Application(), HasAndroidInjector {

    var activityComponent: ActivityComponent? = null
    var swordmanComponent: SwordmanComponent? = null

    @JvmField
    @Inject
    var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null
//    var flutterEngine: FlutterEngine? = null

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent //                .builder().appModule(new AppModule()).build().inject(this);
                .create().inject(this)
        //        activityComponent = DaggerActivityComponent.builder().buld();
//        activityComponent = DaggerActivityComponent.builder().buld().swordmanComponent(DaggerSwordmanComponent.builder().build()).build();

//        flutterEngine = FlutterEngine(this)
//        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
    }

    override fun androidInjector(): AndroidInjector<Any>? {
        return null
    }

    companion object {
        @JvmStatic
        operator fun get(context: Context): LynpoApp {
            return context.applicationContext as LynpoApp
        }
    }
}