package com.example.jetpackdemo.data.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.jetpackdemo.data.convert.ListClassifyTypeConverter

@Entity(tableName = "list_data")
@TypeConverters(ListClassifyTypeConverter::class)
data class ListClassifyResponse (
    var data: List<ClassifyResponse>?,
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var dataType: String,
    var mLastTime: Long = System.currentTimeMillis()
)
