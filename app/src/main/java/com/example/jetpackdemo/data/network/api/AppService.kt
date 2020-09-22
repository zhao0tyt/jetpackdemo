package com.example.jetpackdemo.data.network.api

import androidx.lifecycle.MutableLiveData
import com.example.jetpackdemo.data.model.Register
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AppService {
    @POST("user/register")
    @FormUrlEncoded
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") rePassword: String
    ): Call<Register>


}

