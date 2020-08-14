package com.example.jetpackdemo.data.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.jetpackdemo.data.network.api.AppService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class Network {

    private val appService = ServiceCreator.create(AppService::class.java)

    suspend fun register(
        username: String,
        password: String,
        repassword: String
    ) =
        appService.register(username, password, repassword).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    Log.d("zzq", "onFailure = " + t.message)
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    Log.d("zzq", "onResponse")
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }

    companion object {

        private var network: Network? = null

        fun getInstance(): Network {
            if (network == null) {
                synchronized(Network::class.java) {
                    if (network == null) {
                        network = Network()
                    }
                }
            }
            return network!!
        }

    }
}