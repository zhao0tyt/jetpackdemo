package com.example.jetpackdemo.data.network

import com.example.jetpackdemo.data.network.api.AppService
import com.zzq.common.util.LogUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class Network {

    private val appService = ServiceCreator.create(AppService::class.java)

    suspend fun register(username: String, password: String, repassword: String) =
        appService.register(username, password, repassword).await()

    suspend fun login(username: String, password: String) =
        appService.login(username, password).await()

    suspend fun getIntegral() = appService.getIntegral().await()

    suspend fun getIntegralRank(page: Int) = appService.getIntegralRank(page).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    LogUtil.logd("coroutines request failed")
//                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
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