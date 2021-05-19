package com.example.jetpackdemo.data.network

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
     * 获取积分历史
     */
    @GET("lg/coin/list/{page}/json")
    fun getIntegralHistory(@Path("page") page: Int): Call<ApiResponse<ApiPagerResponse<ArrayList<IntegralHistoryResponse>>>>

    /**
     * 公众号分类
     */
    @GET("wxarticle/chapters/json")
    fun getOfficialAccountTitle(): Call<ApiResponse<ArrayList<ClassifyResponse>>>

    /**
     * 获取公众号数据
     */
    @GET("wxarticle/list/{id}/{page}/json")
    fun getPublicData(
        @Path("page") pageNo: Int,
        @Path("id") id: Int
    ):Call<ApiResponse<ApiPagerResponse<ArrayList<AriticleResponse>>>>

    /**
     * 收藏文章
     */
    @POST("lg/collect/{id}/json")
    fun collect(@Path("id") id: Int): Call<ApiResponse<Any?>>

    /**
     * 取消收藏文章
     */
    @POST("lg/uncollect_originId/{id}/json")
    fun uncollect(@Path("id") id: Int): Call<ApiResponse<Any?>>

    /**
     * 获取体系数据
     */
    @GET("tree/json")
    fun getSystemData(): Call<ApiResponse<ArrayList<SystemResponse>>>

    /**
     * 知识体系下的文章数据
     */
    @GET("article/list/{page}/json")
    fun getSystemChildData(
        @Path("page") pageNo: Int,
        @Query("cid") cid: Int
    ): Call<ApiResponse<ApiPagerResponse<ArrayList<AriticleResponse>>>>

    /**
     * 获取导航数据
     */
    @GET("navi/json")
    fun getNavigationData(): Call<ApiResponse<ArrayList<NavigationResponse>>>

    /**
     * 广场列表数据
     */
    @GET("user_article/list/{page}/json")
    fun getSquareData(@Path("page") page: Int): Call<ApiResponse<ApiPagerResponse<ArrayList<AriticleResponse>>>>

    /**
     * 每日一问列表数据
     */
    @GET("wenda/list/{page}/json")
    fun getAskData(@Path("page") page: Int): Call<ApiResponse<ApiPagerResponse<ArrayList<AriticleResponse>>>>

    /**
     * 项目分类标题
     */
    @GET("project/tree/json")
    fun getProjecTitle(): Call<ApiResponse<ArrayList<ClassifyResponse>>>

    /**
     * 根据分类id获取项目数据
     */
    @GET("project/list/{page}/json")
    fun getProjecDataByType(
        @Path("page") pageNo: Int,
        @Query("cid") cid: Int
    ): Call<ApiResponse<ApiPagerResponse<ArrayList<AriticleResponse>>>>

    /**
     * 获取热门项目数据
     */
    @GET("article/listproject/{page}/json")
    fun getHotProjecData(@Path("page") pageNo: Int): Call<ApiResponse<ApiPagerResponse<ArrayList<AriticleResponse>>>>

    /**
     * 获取banner数据
     */
    @GET("banner/json")
    fun getBanner(): Call<ApiResponse<ArrayList<BannerResponse>>>


    /**
     * 获取置顶文章集合数据
     */
    @GET("article/top/json")
    fun getTopAritrilList(): Call<ApiResponse<ArrayList<AriticleResponse>>>

    /**
     * 获取首页文章数据
     */
    @GET("article/list/{page}/json")
    fun getAritrilList(@Path("page") pageNo: Int): Call<ApiResponse<ApiPagerResponse<ArrayList<AriticleResponse>>>>
}

