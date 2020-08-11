package com.example.jetpackdemo.login.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackdemo.BaseApplication
import com.example.jetpackdemo.data.model.Register
import com.example.jetpackdemo.data.repository.AppRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AppRepository) : ViewModel(){
    var name = MutableLiveData("")
    var pwd = MutableLiveData("")
    var repwd = MutableLiveData("")
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

    /**
     * 确认密码的回调函数
     */
    fun onRepwdChanged(s: CharSequence) {
        repwd.value = s.toString()
    }
//    fun login() {
//        if (name.value.equals("zzq") && pwd.value.equals("123456")) {
//            Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show()
//            val intent = Intent(context, MainActivity::class.java)
//            intent.flags = FLAG_ACTIVITY_NEW_TASK
//            context.startActivity(intent)
//        } else {
//            Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show()
//        }
//    }

    fun register(){
        Log.d("zzq","register")
        viewModelScope.launch {
            var registerData:Register = repository.register(name, pwd, repwd)
            if (registerData.errorCode == 0) {
                Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "注册失败", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
