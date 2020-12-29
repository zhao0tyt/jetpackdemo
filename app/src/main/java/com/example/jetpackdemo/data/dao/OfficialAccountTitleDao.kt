package com.example.jetpackdemo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetpackdemo.data.bean.ListClassifyResponse


@Dao
interface OfficialAccountTitleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: ListClassifyResponse)

    @Query("SELECT * FROM list_data where dataType = :dataType")
    suspend fun getTitle(dataType: String?): ListClassifyResponse
}