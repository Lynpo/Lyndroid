package com.lynpo

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.Utils
import com.idlefish.flutterboost.interfaces.INativeRouter
import com.lynpo.flutter.PageRouter
import com.lynpo.flutter.TextPlatformViewFactory
import com.lynpo.thdlibs.dagger2.component.ActivityComponent
import com.lynpo.thdlibs.dagger2.component.DaggerAppComponent
import com.lynpo.thdlibs.dagger2.component.SwordmanComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.flutter.embedding.android.FlutterView
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.StandardMessageCodec
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
//        flutterEngine?.dartExecutor?.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
//        FlutterEngineCache.getInstance().put("lyn_f_engine", flutterEngine)

        val router = INativeRouter { context, url, urlParams, requestCode, exts ->
            val assembleUrl = Utils.assembleUrl(url, urlParams)
            PageRouter.openPageByUrl(context, assembleUrl, urlParams)
        }

        // 生命周期监听
        val boostLifecycleListener = object : FlutterBoost.BoostLifecycleListener {
            override fun beforeCreateEngine() {
            }
            override fun onEngineCreated() {
                // 引擎创建后的操作，比如自定义MethodChannel，PlatformView等

                // 注册MethodChannel，监听flutter侧的getPlatformVersion调用


                // 注册MethodChannel，监听flutter侧的getPlatformVersion调用
                val methodChannel = MethodChannel(FlutterBoost.instance().engineProvider().dartExecutor, "flutter_native_channel")
                methodChannel.setMethodCallHandler { call: MethodCall, result: MethodChannel.Result ->
                    if (call.method == "getPlatformVersion") {
                        result.success(Build.VERSION.RELEASE)
                    } else {
                        result.notImplemented()
                    }
                }

                // 注册PlatformView viewTypeId要和flutter中的viewType对应

                // 注册PlatformView viewTypeId要和flutter中的viewType对应
                FlutterBoost
                        .instance()
                        .engineProvider()
                        .platformViewsController
                        .registry
                        .registerViewFactory("plugins.test/view", TextPlatformViewFactory(StandardMessageCodec.INSTANCE))
            }
            override fun onPluginsRegistered() {
            }
            override fun onEngineDestroy() {
            }
        }

        // 生成Platform配置
        val platform = FlutterBoost
                .ConfigBuilder(this, router)
                .isDebug(true)
                .dartEntrypoint("")//dart入口，默认为main函数，这里可以根据native的环境自动选择Flutter的入口函数来统一Native和Flutter的执行环境，（比如debugMode == true ? "mainDev" : "mainProd"，Flutter的main.dart里也要有这两个对应的入口函数）
                .whenEngineStart(FlutterBoost.ConfigBuilder.ANY_ACTIVITY_CREATED)
                .renderMode(FlutterView.RenderMode.texture)
                .lifecycleListener(boostLifecycleListener)
                .build()

        // 初始化
        FlutterBoost.instance().init(platform)
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