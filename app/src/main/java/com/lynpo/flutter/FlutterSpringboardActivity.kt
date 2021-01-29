package com.lynpo.flutter

import android.os.Bundle
import android.util.Log
import com.lynpo.LynpoApp
import com.lynpo.R
import com.lynpo.base.flutter.BaseFlutterActivity
import com.lynpo.util.ToastUtils
import io.flutter.embedding.android.FlutterActivityLaunchConfigs
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import kotlinx.android.synthetic.main.activity_flutter_springboard.*


/**
 * FlutterSpringboardActivity
 **
 * Create by fujw on 1/29/21.
 */
class FlutterSpringboardActivity: BaseFlutterActivity() {

    private val _CHANNEL = "com.lynpo.lyn_flutter/toast"
    private val DEBUG_INFO = "DEBUG_INFO"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flutter_springboard)

        enter.setOnClickListener {
            // https://flutter.dev/docs/development/add-to-app/android/add-flutter-screen?tab=prewarm-engine-kotlin-tab
            startActivity(
                    // start with new engine
//                    FlutterActivity
//                            .withNewEngine()
//                            .initialRoute("/lyn_route")
//                            .build(this)

                    // start with cached engine
                    // When using the withCachedEngine() factory method, pass the same ID that you
                    // used when caching the desired FlutterEngine.
                    withCachedEngine("1")
                            // To launch your FlutterActivity with a transparent background, pass
                            // the appropriate BackgroundMode to the IntentBuilder:
                            .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
                            .build(this)
            )
        }
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(LynpoApp.getInstance().flutterEngine.dartExecutor.binaryMessenger, _CHANNEL)
                .setMethodCallHandler { call, result ->
                    Log.d(DEBUG_INFO, "MethodCallHandler handling..., method:${call.method}")
//                    toastMessage("methodHandler call invoked.")
                    if (call.method == "toast") {
                        call.arguments?.let {
                            Log.d(DEBUG_INFO, "MethodCallHandler, method:${call.method}, args: $it")
                            toastMessage("$it")
                        }
//                        call.argument<String>("k1")?.let {
//                            toastMessage("methodHandler invoke with param key:$it")
//                        }
                        result.success(1)
                    } else {
                        result.success(0)
                    }
                }
    }

    private fun toastMessage(message: String) {
        ToastUtils.makeToast(this, message)
    }

}