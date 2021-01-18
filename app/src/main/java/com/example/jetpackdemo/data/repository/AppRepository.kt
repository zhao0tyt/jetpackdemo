package com.example.jetpackdemo.data.repository

import com.example.jetpackdemo.data.bean.ClassifyResponse
import com.example.jetpackdemo.data.dao.AppDatabase
import com.example.jetpackdemo.data.bean.IntegralResponse
import com.example.jetpackdemo.data.bean.ListClassifyResponse
import com.example.jetpackdemo.data.network.Network
import com.zzq.common.ext.util.shouldUpdate
import com.zzq.common.util.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(appDatabase: AppDatabase, private val network: Network) {
    private val integraldao = appDatabase.integralDao()
    private val officialAccountTitleDao = appDatabase.officialAccountTitleDao()

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
    suspend fun getIntegral(userId: String): IntegralResponse {
        var response = getIntegralFromDb(userId)
        if (response == null || response.mLastTime.shouldUpdate()) {
            response = getIntegralFromNetWork().data
            response.mLastTime = System.currentTimeMillis()
            insertIntegral(response)
        }
        return response
    }
    suspend fun getIntegralFromNetWork() = withContext(Dispatchers.IO) {
        LogUtil.logd("getIntegralFromNetWork")
        var response = network.getIntegral()
        response
    }
    suspend fun getIntegralFromDb(userId: String) = withContext(Dispatchers.IO) {
        LogUtil.logd("getIntegralFromDb")
        var response = integraldao.getIntegral(userId)
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
    suspend fun getIntegralHistory(page: Int) = withContext(Dispatchers.IO) {
        val response = network.getIntegralHistory(page)
        response
    }

    //Official Account
    suspend fun getOfficialAccountTitle(): ArrayList<ClassifyResponse> {
        var listClassifyResponse = getOfficialAccountTitleFromDb()
        var response:ArrayList<ClassifyResponse> = arrayListOf()
        if (listClassifyResponse != null){
            response = listClassifyResponse?.data as ArrayList<ClassifyResponse>
        }
        if (listClassifyResponse == null || listClassifyResponse.mLastTime.shouldUpdate()) {
            response = getOfficialAccountTitleFromNetWork().data
            //创建一个listDataResponse并插入数据库
            listClassifyResponse = ListClassifyResponse(response, 1, AppDatabase.OFFICIAL_ACCOUNT,
                System.currentTimeMillis())
            insertOfficialAccountTitle(listClassifyResponse)
        }
        return response
    }
    suspend fun getOfficialAccountTitleFromNetWork() = withContext(Dispatchers.IO) {
        LogUtil.logd("getOfficialAccountTitleFromNetWork")
        var response = network.getOfficialAccountTitle()
        response
    }
    suspend fun getOfficialAccountTitleFromDb() = withContext(Dispatchers.IO) {
        LogUtil.logd("getOfficialAccountTitleFromNetDb")
        var response = officialAccountTitleDao.getData(AppDatabase.OFFICIAL_ACCOUNT)
        response
    }
    suspend fun insertOfficialAccountTitle(data: ListClassifyResponse) = withContext(Dispatchers.IO) {
        LogUtil.logd("insertOfficialAccountTitle")
        officialAccountTitleDao.insert(data)
    }

    //获取公众号数据
    suspend fun getPublicDataFromNet(page: Int, id: Int) = withContext(Dispatchers.IO) {
        LogUtil.logd("getPublicDataFromNet")
        var response = network.getPublicData(page, id)
        response
    }


    //收藏
    suspend fun collect(id: Int) = withContext(Dispatchers.IO) {
        LogUtil.logd("collect")
        var response = network.collect(id)
        response
    }

    //取消收藏
    suspend fun unCollect(id: Int) = withContext(Dispatchers.IO) {
        LogUtil.logd("unCollect")
        var response = network.unCollect(id)
        response
    }













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