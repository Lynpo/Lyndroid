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
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import javax.inject.Inject

/**
 * Create by fujw on 2018/4/1.
 * *
 * LynpoApp
 */
class LynpoApp : Application(), HasAndroidInjector {

    companion object {

        private lateinit var mInstance: LynpoApp

        @JvmStatic
        operator fun get(context: Context): LynpoApp {
            return context.applicationContext as LynpoApp
        }

        fun getInstance(): LynpoApp = mInstance
    }

    var activityComponent: ActivityComponent? = null
//    var swordmanComponent: SwordmanComponent? = null

    @JvmField
    @Inject
    var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null

    lateinit var flutterEngine: FlutterEngine

    override fun onCreate() {
        super.onCreate()
        mInstance = this

        DaggerAppComponent //                .builder().appModule(new AppModule()).build().inject(this);
                .create().inject(this)
        //        activityComponent = DaggerActivityComponent.builder().buld();
//        activityComponent = DaggerActivityComponent.builder().buld().swordmanComponent(DaggerSwordmanComponent.builder().build()).build();

        /**
         * https://flutter.dev/docs/development/add-to-app/android/add-flutter-screen?tab=prewarm-engine-kotlin-tab
         *
         * Every FlutterActivity creates its own FlutterEngine by default. Each FlutterEngine has a non-trivial warm-up time.
         * This means that launching a standard FlutterActivity comes with a brief delay before your Flutter experience
         * becomes visible. To minimize this delay, you can warm up a FlutterEngine before arriving at your FlutterActivity,
         * and then you can use your pre-warmed FlutterEngine instead.
         *
         * Note: When using a cached FlutterEngine, that FlutterEngine outlives any FlutterActivity
         * or FlutterFragment that displays it. Keep in mind that Dart code begins executing as soon
         * as you pre-warm the FlutterEngine, and continues executing after the destruction of your
         * FlutterActivity/FlutterFragment. To stop executing and clear resources, obtain your
         * FlutterEngine from the FlutterEngineCache and destroy the FlutterEngine with
         * FlutterEngine.destroy().
         */
        // Instantiate a FlutterEngine.
        flutterEngine = FlutterEngine(this)

        // Configure an initial route.
        flutterEngine.navigationChannel.setInitialRoute("/lyn_route")

        // Start executing Dart code to pre-warm the FlutterEngine.
        flutterEngine.dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault())

        // Cache the FlutterEngine to be used by FlutterActivity.
        FlutterEngineCache.getInstance().put("1", flutterEngine)
        /**
         * The ID passed to the FlutterEngineCache can be whatever you want. Make sure that you pass
         * the same ID to any FlutterActivity or FlutterFragment that should use the cached FlutterEngine.
         * Using FlutterActivity with a cached FlutterEngine is discussed next.
         *
         * Note: Runtime performance isn’t the only reason that you might pre-warm and cache a
         * FlutterEngine. A pre-warmed FlutterEngine executes Dart code independent from a
         * FlutterActivity, which allows such a FlutterEngine to be used to execute arbitrary Dart
         * code at any moment. Non-UI application logic can be executed in a FlutterEngine, like
         * networking and data caching, and in background behavior within a Service or elsewhere.
         * When using a FlutterEngine to execute behavior in the background, be sure to adhere to
         * all Android restrictions on background execution.
         */
    }

    override fun androidInjector(): AndroidInjector<Any>? {
        return null
    }
}