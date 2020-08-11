package com.example.jetpackdemo.login.viewmodel

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jetpackdemo.BaseApplication
import com.example.jetpackdemo.MainActivity

class LoginViewModel : ViewModel(){
    var name = MutableLiveData("")
    var pwd = MutableLiveData("")
    val context = BaseApplication.context
    /**
     * 用户名改变的回调函数
     */

    fun onNameChanged(s: CharSequence) {
        name.value = s.toString()
    }

    /**
     * 密码改变的回调函数
     */
    fun onPwdChanged(s: CharSequence) {
        pwd.value = s.toString()
    }

    fun login() {
        if (name.value.equals("zzq") && pwd.value.equals("123456")) {
            Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show()
        }
    }

}
