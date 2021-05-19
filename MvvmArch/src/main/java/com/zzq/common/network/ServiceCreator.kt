package com.zzq.common.network

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.zzq.common.network.interceptor.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ServiceCreator(context: Context, baseUrl:String) {

    private val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))
    }

    val loggingInterceptor = LoggingInterceptor.Builder()
        .loggable(true) // TODO: 发布到生产环境需要改成false
        .request()
        .requestTag("Request")
        .response()
        .responseTag("Response")
        //.hideVerticalLine()// 隐藏竖线边框
        .build()

    private val httpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).cookieJar(
        cookieJar
    ).build()


    private val builder = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())


    private val retrofit = builder.build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
}