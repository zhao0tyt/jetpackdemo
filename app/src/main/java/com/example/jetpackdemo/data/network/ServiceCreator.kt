package com.example.jetpackdemo.data.network

import com.example.jetpackdemo.data.network.logginginterceptor.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object ServiceCreator {

    private const val BASE_URL = "https://www.wanandroid.com"

    val loggingInterceptor = LoggingInterceptor.Builder()
        .loggable(true) // TODO: 发布到生产环境需要改成false
        .request()
        .requestTag("Request")
        .response()
        .responseTag("Response")
        //.hideVerticalLine()// 隐藏竖线边框
        .build()

    private val httpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())


    private val retrofit = builder.build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

}