package com.example.jetpackdemo.ui.login.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackdemo.App
import com.example.jetpackdemo.data.model.Register
import com.example.jetpackdemo.data.repository.AppRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AppRepository) : ViewModel() {
    val context = App.context

    fun register(username: String, password: String, repassword: String) {
        viewModelScope.launch {
            var registerData: Register =
                repository.register(username, password, repassword)
            if (registerData.errorCode == 0) {
                Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "注册失败", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
