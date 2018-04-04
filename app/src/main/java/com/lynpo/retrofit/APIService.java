package com.lynpo.retrofit;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Create by fujw on 2018/4/1.
 * *
 * APIService
 */
public interface APIService {

    @GET("https://hihamlet.com")
    Call<JSONObject> getUser(@Query("user_id") int id);
}
