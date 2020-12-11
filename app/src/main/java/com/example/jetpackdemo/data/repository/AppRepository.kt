package com.example.jetpackdemo.data.repository

import com.example.jetpackdemo.data.model.ApiResponse
import com.example.jetpackdemo.data.model.UserInfo
import com.example.jetpackdemo.data.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(private val network: Network) {

    suspend fun register(username: String, password: String, repassword: String) =
        withContext(Dispatchers.IO) {
            val response = network.register(username, password, repassword)
            response
        }

    suspend fun login(username: String, password: String) = withContext(Dispatchers.IO) {
        val response = network.login(username, password)
        response
    }

    suspend fun getIntegral() = withContext(Dispatchers.IO) {
        val response = network.getIntegral()
        response
    }

    suspend fun getIntegralRank(page: Int) = withContext(Dispatchers.IO) {
        val response = network.getIntegralRank(page)
        response
    }

    companion object {

        private var repository: AppRepository? = null

        fun getInstance(network: Network): AppRepository {
            if (repository == null) {
                synchronized(AppRepository::class.java) {
                    if (repository == null) {
                        repository = AppRepository(network)
                    }
                }
            }

            return repository!!
        }
    }
}