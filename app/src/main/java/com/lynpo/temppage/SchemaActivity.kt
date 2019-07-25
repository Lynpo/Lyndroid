package com.lynpo.temppage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import com.lynpo.R
import com.lynpo.eternal.LynConstants
import com.lynpo.eternal.base.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_schema.*

class SchemaActivity : BaseActivity(), View.OnClickListener {

    private val url = "http://lynpo.com/"
    private val url2 = "http://lynpo.com/"
    private val url3 = "http://lynpo.com/"
    private val url4 = "http://lynpo.com/"
    private val url5 = "http://lynpo.com/"

    override fun onClick(v: View?) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW

        var uri: Uri? = null
        when (v?.id) {
            R.id.tv_url -> {
                uri = Uri.parse(url)
            }
            R.id.tv_url2 -> {
                uri = Uri.parse(url2)
            }
            R.id.tvUrl3 -> {
                uri = Uri.parse(url3)
            }
            R.id.tvUrl4 -> {
                uri = Uri.parse(url4)
            }
            R.id.tvUrl5 -> {
                uri = Uri.parse(url5)
            }
            else -> {
            }
        }

        intent.data = uri
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schema)

        tvUrl3.text = url3
        tvUrl4.text = url4
        tvUrl5.text = url5
        tvUrl3.setOnClickListener(this)
        tvUrl4.setOnClickListener(this)
        tvUrl5.setOnClickListener(this)

        tv_url.text = getString(R.string.url_lynpo_app)
        tv_url.text = url
        tv_url.setOnClickListener(this)/*{
            val intent = Intent()
//            val uri = Uri.parse(getString(R.string.url_lynpo_app))
            val uri = Uri.parse(url)
            intent.action = Intent.ACTION_VIEW
            intent.data = uri
            startActivity(intent)
        }*/

        tv_url2.text = getString(R.string.url_lynpo)
        tv_url2.text = url2
        tv_url2.setOnClickListener(this)/*{
            val intent = Intent()
//            val uri = Uri.parse(getString(R.string.url_lynpo))
            val uri = Uri.parse(url2)
            intent.action = Intent.ACTION_VIEW
            intent.data = uri
            startActivity(intent)
        }*/

        val url1 = "http://lynpo.com/path/name/id/121"
        val uri = Uri.parse(url1)
        Log.d(LynConstants.LOG_TAG, "uri:host:" + uri.host)  // lynpo.com
        Log.d(LynConstants.LOG_TAG, "uri:schema:" + uri.scheme)  // http
        Log.d(LynConstants.LOG_TAG, "uri:path:" + uri.path)  // /path/name/id/121
        Log.d(LynConstants.LOG_TAG, "uri:pathSegments:" + uri.pathSegments)  // [path, name, id, 121]
        Log.d(LynConstants.LOG_TAG, "uri:lastPathSegment:" + uri.lastPathSegment)    // 121
    }
}
