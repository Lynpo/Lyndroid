package com.lynpo.framework.platform

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.lynpo.eternal.LynConstants


/**
 * LynIntentService
 **
 * Create by fujw on 2019-12-11.
 */
class LynIntentService: IntentService("LynIntentService") {

    override fun onHandleIntent(intent: Intent?) {
//        startForeground(0, null)
        for (iterator in 0..100) {
            Thread.sleep(1000)
            Log.d(LynConstants.LOG_TAG, "onHandleIntent() : $iterator")
        }

//        Thread {
//            JobIntentService.enqueueWork(this, LynJobIntentService::class.java, 1, Intent(this, LynJobIntentService::class.java))
//        }.start()
    }
}