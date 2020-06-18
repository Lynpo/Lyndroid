package com.lynpo.framework.platform

import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import com.lynpo.eternal.LynConstants


/**
 * LynJobIntentService
 **
 * Create by fujw on 2019-12-11.
 */
class LynJobIntentService: JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        for (iterator in 0..9) {
            Thread.sleep(1000)
            Log.d(LynConstants.LOG_TAG, "onHandleIntent() : $iterator")
        }

//        startService(Intent(this, LynIntentService::class.java))
    }
}