package com.lynpo.temppage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.lynpo.R
import com.lynpo.eternal.base.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_schema.*

class SchemaActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schema)

        tv_url.text = getString(R.string.url_lynpo_app)
        tv_url.setOnClickListener{
            val intent = Intent()
            val uri = Uri.parse(getString(R.string.url_lynpo_app))
            intent.action = Intent.ACTION_VIEW
            intent.data = uri
            startActivity(intent)
        }

        tv_url2.text = getString(R.string.url_lynpo)
        tv_url2.setOnClickListener{
            val intent = Intent()
            val uri = Uri.parse(getString(R.string.url_lynpo))
            intent.action = Intent.ACTION_VIEW
            intent.data = uri
            startActivity(intent)
        }

        val url1 = "http://lynpo.com/path/name/id/121"
        val uri = Uri.parse(url1)
        Log.d("debug_tag", "uri:schema:" + uri.scheme)  // http
        Log.d("debug_tag", "uri:host:" + uri.host)  // lynpo.com
        Log.d("debug_tag", "uri:path:" + uri.path)  // /path/name/id/121
        Log.d("debug_tag", "uri:pathSegments:" + uri.pathSegments)  // [path, name, id, 121]
        Log.d("debug_tag", "uri:lastPathSegment:" + uri.lastPathSegment)    // 121
    }
}
