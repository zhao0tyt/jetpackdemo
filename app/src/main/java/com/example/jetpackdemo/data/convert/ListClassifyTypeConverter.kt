package com.example.jetpackdemo.data.convert

import androidx.room.TypeConverter
import com.example.jetpackdemo.data.bean.ClassifyResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListClassifyTypeConverter {

    @TypeConverter
    fun stringToList(s: String?): List<ClassifyResponse>? =
        Gson().fromJson(s, object : TypeToken<List<ClassifyResponse>>() {}.type)

    @TypeConverter
    fun listToString(list: List<ClassifyResponse>?): String? = Gson().toJson(list)

}