package com.example.jetpackdemo.data.repository

import com.example.jetpackdemo.data.dao.AppDatabase
import com.example.jetpackdemo.data.bean.IntegralResponse
import com.example.jetpackdemo.data.network.Network
import com.example.jetpackdemo.data.network.RequestStateCallback
import com.zzq.common.ext.util.shouldUpdate
import com.zzq.common.util.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(private val appDatabase: AppDatabase, private val network: Network) {
    private val integraldao = appDatabase.integralDao()

    suspend fun register(username: String, password: String, repassword: String) =
        withContext(Dispatchers.IO) {
            val response = network.register(username, password, repassword)
            response
        }

    suspend fun login(username: String, password: String) = withContext(Dispatchers.IO) {

        val response = network.login(username, password)
        response
    }

    // Integral
    suspend fun getIntegral(userId: String, callback: RequestStateCallback): IntegralResponse {
        var integralResponse = getIntegralFromDb(userId)
        if (integralResponse == null || integralResponse.mLastTime.shouldUpdate()) {
            val response = getIntegralFromNetWork()
            if (response.isSucces()) {
                integralResponse = response.data
                integralResponse.mLastTime = System.currentTimeMillis()
                insertIntegral(integralResponse)
                callback.success()
            } else {
                callback.failed(RequestStateCallback.ErrorType.HTTP)
            }
        }
        return integralResponse
    }
    suspend fun getIntegralFromNetWork() = withContext(Dispatchers.IO) {
        LogUtil.logd("getIntegralFromNetWork")
        var response = network.getIntegral()
        response
    }
    suspend fun getIntegralFromDb(userId: String) = withContext(Dispatchers.IO) {
        LogUtil.logd("getIntegralFromDb")
        var response = appDatabase.integralDao().getIntegral(userId)
        response
    }
    suspend fun insertIntegral(data: IntegralResponse) = withContext(Dispatchers.IO) {
        LogUtil.logd("insertIntegral")
        integraldao.insert(data)
    }
    suspend fun getIntegralRank(page: Int) = withContext(Dispatchers.IO) {
        val response = network.getIntegralRank(page)
        response
    }

    //Official Account




















    companion object {

        private var repository: AppRepository? = null

        fun getInstance(appDatabase: AppDatabase, network: Network): AppRepository {
            if (repository == null) {
                synchronized(AppRepository::class.java) {
                    if (repository == null) {
                        repository = AppRepository(appDatabase, network)
                    }
                }
            }

            return repository!!
        }
    }
}