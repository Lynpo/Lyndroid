package com.lynpo.kotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import com.lynpo.R
import com.lynpo.eternal.base.ui.BaseActivity
import com.lynpo.kotlin.model.Item

class ReturnNullActivity : BaseActivity() {

    private val mCache = SparseArray<Item>(3)

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

        getItemCouldBeNull(1)

        // call this to generate "java.lang.IllegalStateException: mCache.get(position) must not be null"
        // getItemCannotBeNull(1)
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
