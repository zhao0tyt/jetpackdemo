package com.example.jetpackdemo.data.dao

import androidx.room.*
import com.example.jetpackdemo.data.model.IntegralResponse

@Dao
interface IntegralDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: IntegralResponse)

    @Query("SELECT * FROM Integral WHERE userId = :userId")
    suspend fun getIntegral(userId: String?): IntegralResponse
}