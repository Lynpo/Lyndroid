package com.lynpo.kotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import com.lynpo.R
import com.lynpo.eternal.LynConstants
import com.lynpo.eternal.base.ui.BaseActivity
import com.lynpo.kotlin.classobject.ClassObjectPresenter
import com.lynpo.kotlin.keywords.KeywordPresenter
import com.lynpo.kotlin.model.Item

class ReturnNullActivity : BaseActivity() {

    private val mCache = SparseArray<Item>(3)
    private var name: String? = null
    private var itemName: String = ""
    private var nullSample: NullSample = NullSample()
    private lateinit var nullSampleLateInit: NullSample

    companion object {
        private const val KEY_ITEM_ID = "k_id"

        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, ReturnNullActivity::class.java))
        }

        /**
         * {@JvmStatic} mark can be removed
         * {#Context.} is necessary
         * Other Activity can launch this Activity through this method with:
         *
         * startActivity(ReturnNullActivity.returnNullIntent(mContext, 1));
         */
        @JvmStatic
        fun Context.returnNullIntent(id: Int): Intent {
            return Intent(this, ReturnNullActivity::class.java).apply {
                putExtra(KEY_ITEM_ID, id)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_return_null)

        if (intent != null) {
            initIntent(intent)
        }
        getItemCouldBeNull(1)

        // call this to generate "java.lang.IllegalStateException: mCache.get(position) must not be null"
        // getItemCannotBeNull(1)

        try {
            NullSample().setData(EmptyListForKotlinSample().data)
        } catch (e: Exception) {
            Log.d(LynConstants.LOG_TAG, e.message)
        }

        // keywords test
        KeywordPresenter().rangeCase("sas")
        ClassObjectPresenter().objectMethodsLinkUse()
    }

    private fun initIntent(intent: Intent) {
        try {
            val itemNameExtra = intent.getStringExtra("itemName")
            val nameExtra = intent.getStringExtra("name")
            Log.d(LynConstants.LOG_TAG, "ReturnNullActivity.initIntent:$itemNameExtra")
            Log.d(LynConstants.LOG_TAG, "ReturnNullActivity.initIntent:$nameExtra")
            itemName = itemNameExtra
            name = nameExtra
        } catch (e: Exception){
            Log.d(LynConstants.LOG_TAG, "ReturnNullActivity.initIntent:${e.message}")
        }

        try {
            val nullSampleExtra = intent.getParcelableExtra<NullSample>("nullSample")
            Log.d(LynConstants.LOG_TAG, "ReturnNullActivity.initIntent:$nullSampleExtra")
            nullSample = nullSampleExtra
        } catch (e: Exception) {
            Log.d(LynConstants.LOG_TAG, "ReturnNullActivity.initIntent:${e.message}")
        }

        try {
            val nullSampleLateInitExtra = intent.getParcelableExtra<NullSample>("nullSampleLateInit")
            Log.d(LynConstants.LOG_TAG, "ReturnNullActivity.initIntent:$nullSampleLateInitExtra")
            nullSampleLateInit = nullSampleLateInitExtra
            Log.d(LynConstants.LOG_TAG, "ReturnNullActivity.initIntent-use-nullSampleLateInit:$nullSampleLateInit")
        } catch (e: Exception) {
            Log.d(LynConstants.LOG_TAG, "ReturnNullActivity.initIntent:${e.message}")
        }
    }

    /**
     * Here if mCache.get() return null,
     * the method return type should be
     * Item?
     * Item?
     * Item?
     */
    private fun getItemCouldBeNull(position: Int): Item? {
        return mCache.get(position)
    }

    /**
     * Here if mCache.get() return null, the method return type should be Item?
     * if the return type is
     * Item
     * Item
     * Item
     * or this method will throw
     * "java.lang.IllegalStateException: mCache.get(position) must not be null"
     * when mCache.get(position) returns null.
     */
    private fun getItemCannotBeNull(position: Int): Item {
        return mCache.get(position)
    }
}
