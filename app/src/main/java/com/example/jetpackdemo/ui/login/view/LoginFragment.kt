package com.example.jetpackdemo.ui.login.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.FragmentLoginBinding
import com.example.jetpackdemo.ext.initClose
import com.example.jetpackdemo.ext.showMessage
import com.example.jetpackdemo.ui.base.BaseFragment
import com.example.jetpackdemo.ui.login.viewmodel.LoginViewModel
import com.example.jetpackdemo.util.CacheUtil
import com.example.jetpackdemo.util.InjectorUtil
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.nav
import com.zzq.common.ext.navigateAction
import com.zzq.common.ext.parseState
import com.zzq.common.util.LogUtil
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.et_account
import kotlinx.android.synthetic.main.fragment_login.et_pwd
import kotlinx.android.synthetic.main.toolbar.*

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : BaseFragment<BaseViewModel, FragmentLoginBinding>(), View.OnClickListener {

//    private val loginModel: LoginViewModel by viewModels()

    private val loginModel: LoginViewModel by viewModels {
        InjectorUtil.getLoginViewModelFactory(requireContext())
    }

    override fun layoutId(): Int {
        return R.layout.fragment_login
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.model = loginModel
        toolbar.initClose("注册") {
            nav().navigateUp()
        }
        btn_go_register.setOnClickListener(this)
        btn_login.setOnClickListener(this)
    }

    override fun createObserver() {
        loginModel.loginResult.observe(viewLifecycleOwner,
            Observer { resultState ->
                parseState(
                    resultState, {
                        //登录成功 通知账户数据发生改变鸟
                        CacheUtil.setUser(it)
                        CacheUtil.setIsLogin(true)
                        appViewModel.userinfo.value = it
                        nav().navigateAction(R.id.action_loginFragment_to_mainFrgment)
                    }, {
                        showMessage(it.errorMsg)
                    }
                )
            })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_go_register -> {
                nav().navigateAction(R.id.action_loginFragment_to_registerFrgment)
            }
            R.id.btn_login -> {
                val username = et_account.text.toString()
                val password = et_pwd.text.toString()
                when {
                    username.isEmpty() -> showMessage("请填写账号")
                    password.isEmpty() -> showMessage("请填写密码")
                    else -> loginModel.login(username, password)
                }
            }
        }
    }

}