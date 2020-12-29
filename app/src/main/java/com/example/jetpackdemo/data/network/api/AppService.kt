package com.example.jetpackdemo.data.network.api

import com.example.jetpackdemo.data.bean.*
import retrofit2.Call
import retrofit2.http.*

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
    fun getIntegral(): Call<ApiResponse<IntegralResponse>>

    /**
     * 获取积分排行榜
     */
    @GET("coin/rank/{page}/json")
    fun getIntegralRank(@Path("page") page: Int): Call<ApiResponse<ApiPagerResponse<ArrayList<IntegralResponse>>>>

    /**
     * 公众号分类
     */
    @GET("wxarticle/chapters/json")
    fun getOfficialAccountTitle(): Call<ApiResponse<ArrayList<ClassifyResponse>>>
}

