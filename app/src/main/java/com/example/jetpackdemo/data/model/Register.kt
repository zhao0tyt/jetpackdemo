package com.example.jetpackdemo.data.model

//1.    {
//        "data": null,
//        "errorCode": -1,
//        "errorMsg": "用户名已经被注册！"
//    }

//2.
//    {
//        "data": {
//        "admin": false,
//        "chapterTops": [],
//        "coinCount": 0,
//        "collectIds": [],
//        "email": "",
//        "icon": "",
//        "id": 72719,
//        "nickname": "zhao0wlz",
//        "password": "",
//        "publicName": "zhao0wlz",
//        "token": "",
//        "type": 0,
//        "username": "zhao0wlz"
//    },
//        "errorCode": 0,
//        "errorMsg": ""
//    }
data class Register(
    val responseData: Data,
    val errorCode: Int,
    val errorMsg: String
)

data class Data(
    val admin: Boolean,
    val chapterTops: List<Any>,
    val coinCount: Int,
    val collectIds: List<Any>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)
