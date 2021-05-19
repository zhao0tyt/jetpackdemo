package com.example.jetpackdemo.data.network

import com.example.jetpackdemo.App
import com.example.jetpackdemo.Constant
import com.zzq.common.network.ServiceCreator
import com.zzq.common.util.LogUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class Network {

    private val appService = ServiceCreator(App.context,Constant.BASE_URL).create(AppService::class.java)

    suspend fun register(username: String, password: String, repassword: String) =
        appService.register(username, password, repassword).await()

    suspend fun login(username: String, password: String) =
        appService.login(username, password).await()

    suspend fun getIntegral() = appService.getIntegral().await()

    suspend fun getIntegralRank(page: Int) = appService.getIntegralRank(page).await()

    suspend fun getIntegralHistory(page: Int) = appService.getIntegralHistory(page).await()

    suspend fun getOfficialAccountTitle() = appService.getOfficialAccountTitle().await()

    suspend fun getPublicData(page: Int, id: Int) = appService.getPublicData(page, id).await()

    suspend fun collect(id: Int) = appService.collect(id).await()

    suspend fun unCollect(id: Int) = appService.uncollect(id).await()

    suspend fun getSystemData() = appService.getSystemData().await()

    suspend fun getSystemChildData(pageNo: Int, cid: Int) = appService.getSystemChildData(pageNo, cid).await()

    suspend fun getNavigationData() = appService.getNavigationData().await()

    suspend fun getSquareData(page: Int) = appService.getSquareData(page).await()

    suspend fun getAskData(page: Int) = appService.getAskData(page).await()

    suspend fun getProjecTitle() = appService.getProjecTitle().await()

    suspend fun getProjecDataByType(pageNo: Int, cid: Int) = appService.getProjecDataByType(pageNo, cid).await()

    suspend fun getHotProjecData(page: Int) = appService.getHotProjecData(page).await()

    suspend fun getBanner() = appService.getBanner().await()

    suspend fun getTopAritrilList() = appService.getTopAritrilList().await()

    suspend fun getAritrilList(page: Int) = appService.getAritrilList(page).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    LogUtil.logd("coroutines request failed")
                    continuation.resumeWithException(t)
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