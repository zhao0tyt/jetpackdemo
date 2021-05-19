package com.example.jetpackdemo.data.repository

import com.example.jetpackdemo.data.bean.*
import com.example.jetpackdemo.data.dao.AppDatabase
import com.example.jetpackdemo.data.network.Network
import com.example.jetpackdemo.util.CacheUtil
import com.zzq.common.ext.util.shouldUpdate
import com.zzq.common.util.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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

    //获取体系数据
    suspend fun getSystemData() = withContext(Dispatchers.IO) {
        LogUtil.logd("getSystemData")
        var response = network.getSystemData()
        response
    }

    //获取知识体系下的文章数据
    suspend fun getSystemChildData(pageNo: Int, cid: Int) = withContext(Dispatchers.IO) {
        LogUtil.logd("getSystemChildData")
        var response = network.getSystemChildData(pageNo, cid)
        response
    }

    //获取导航数据
    suspend fun getNavigationData() = withContext(Dispatchers.IO) {
        LogUtil.logd("getNavigationData")
        var response = network.getNavigationData()
        response
    }

    //获取广场列表数据
    suspend fun getSquareData(page: Int) = withContext(Dispatchers.IO) {
        LogUtil.logd("getSquareData")
        var response = network.getSquareData(page)
        response
    }

    //获取每日一问数据
    suspend fun getAskData(page: Int) = withContext(Dispatchers.IO) {
        LogUtil.logd("getAskData")
        var response = network.getAskData(page)
        response
    }

    //获取项目分类标题
    suspend fun getProjecTitle() = withContext(Dispatchers.IO) {
        LogUtil.logd("getProjecTitle")
        var response = network.getProjecTitle()
        response
    }

    //根据分类id获取项目数据
    suspend fun getProjecDataByType(pageNo: Int, cid: Int) = withContext(Dispatchers.IO) {
        LogUtil.logd("getProjecDataByType")
        var response = network.getProjecDataByType(pageNo, cid)
        response
    }

    //获取热门项目数据
    suspend fun getHotProjecData(page: Int) = withContext(Dispatchers.IO) {
        LogUtil.logd("getHotProjecData")
        var response = network.getHotProjecData(page)
        response
    }

    /**
     * 获取项目标题数据
     */
    suspend fun getProjectData(
        pageNo: Int,
        cid: Int = 0,
        isNew: Boolean = false
    ): ApiResponse<ApiPagerResponse<ArrayList<AriticleResponse>>> {
        return if (isNew) {
            getHotProjecData(pageNo)
        } else {
            getProjecDataByType(pageNo, cid)
        }
    }

    suspend fun getBanner() = withContext(Dispatchers.IO) {
        LogUtil.logd("getBanner")
        var response = network.getBanner()
        response
    }

    suspend fun getTopAritrilList() = withContext(Dispatchers.IO) {
        LogUtil.logd("getTopAritrilList")
        var response = network.getTopAritrilList()
        response
    }

    suspend fun getAritrilList(page: Int) = withContext(Dispatchers.IO) {
        LogUtil.logd("getAritrilList")
        var response = network.getAritrilList(page)
        response
    }

    /**
     * 获取首页文章数据
     */
    suspend fun getHomeData(pageNo: Int): ApiResponse<ApiPagerResponse<ArrayList<AriticleResponse>>> {
        //同时异步请求2个接口，请求完成后合并数据
        return withContext(Dispatchers.IO) {
            val data = async { getAritrilList(pageNo) }
            //如果App配置打开了首页请求置顶文章，且是第一页
            if (pageNo == 0) {
                val topData = async { getTopAritrilList() }
                data.await().data.datas.addAll(0, topData.await().data)
                data.await()
            } else {
                data.await()
            }
        }
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