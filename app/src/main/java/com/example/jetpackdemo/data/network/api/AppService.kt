package com.example.jetpackdemo.data.network.api

import com.example.jetpackdemo.data.model.ApiResponse
import com.example.jetpackdemo.data.model.Integral
import com.example.jetpackdemo.data.model.UserInfo
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AppService {

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("username") username: String,
        @Field("password") pwd: String
    ): Call<ApiResponse<UserInfo>>

    /**
     * 注册
     */
    @POST("user/register")
    @FormUrlEncoded
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") rePassword: String
    ): Call<ApiResponse<Any>>

    /**
     * 获取当前账户的个人积分
     */
    @GET("lg/coin/userinfo/json")
    fun getIntegral(): Call<ApiResponse<Integral>>
}

