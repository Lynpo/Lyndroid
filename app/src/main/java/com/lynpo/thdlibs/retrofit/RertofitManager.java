package com.lynpo.thdlibs.retrofit;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create by fujw on 2018/4/1.
 * *
 * RertofitManager
 */
public class RertofitManager {

    Retrofit getRetrofit(String url, OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callFactory(new OkHttpClient())
//                .addCallAdapterFactory()
                .build();
    }
}
