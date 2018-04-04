package com.lynpo.okhttp;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Create by fujw on 2018/3/31.
 * *
 * OkHttpUtil
 */
public class OkHttpUtil {

    private static final MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/x-text; charset=utf-8");

    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient();
    }

    private void generatePostRequest() {
        RequestBody formBody = new FormBody.Builder()
                .add("ip", "59.108.54.37")
                .build();
        Request request = new Request.Builder()
                .url("http://ip.taobao.com/service/getIpInfo.php")
                .post(formBody)
                .build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    private void generateHttpRequest() {
        Request.Builder builder = new Request.Builder().url("http://hihamlet.com");
        builder.method("GET", null);
        Request request = builder.build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String threadName = Thread.currentThread().getName();
                // threadName not main thread(UI thread).
                ResponseBody body = response.body();
            }
        });
    }

    public static void uploadFileAnsyc() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "note.txt");
        Request request = new Request.Builder()
                .url("")
                .post(RequestBody.create(MEDIA_TYPE_TEXT, file))
                .build();
        getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
