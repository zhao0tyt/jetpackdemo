package com.example.jetpackdemo.ui.login.view

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.FragmentRegisterBinding
import com.example.jetpackdemo.ext.initClose
import com.example.jetpackdemo.ext.showMessage
import com.example.jetpackdemo.ui.base.BaseFragment
import com.example.jetpackdemo.ui.login.viewmodel.RegisterViewModel
import com.example.jetpackdemo.util.InjectorUtil
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.nav
import com.zzq.common.ext.navigateAction
import com.zzq.common.ext.parseState
import kotlinx.android.synthetic.main.fragment_login.et_account
import kotlinx.android.synthetic.main.fragment_login.et_pwd
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_welcome.btn_register
import kotlinx.android.synthetic.main.toolbar.*


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : BaseFragment<BaseViewModel, FragmentRegisterBinding>() {

    private val viewModel: RegisterViewModel by viewModels {
        InjectorUtil.getRegisterViewModelFactory(requireContext())
    }

    override fun layoutId(): Int {
        return R.layout.fragment_register
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.model = viewModel
        toolbar.initClose("注册") {
            nav().navigateUp()
        }
        //注册按钮点击事件
        btn_register.setOnClickListener {
            val username = et_account.text.toString()
            val password = et_pwd.text.toString()
            val repassword = et_repwd.text.toString()
            when {
                username.isEmpty() -> showMessage("请填写账号")
                password.isEmpty() -> showMessage("请填写密码")
                repassword.isEmpty() -> showMessage("请填写确认密码")
                password.length < 6 -> showMessage("密码最少6位")
                password != repassword -> showMessage("密码不一致")
                else -> viewModel.register(username, password, repassword)
            }
        }
    }

    override fun createObserver() {
        viewModel.registerResult.observe(viewLifecycleOwner,
            Observer { resultState ->
                parseState(
                    resultState, {
                        Toast.makeText(context, "注册成功, 请登录", Toast.LENGTH_SHORT).show()
                        nav().navigateAction(R.id.action_to_loginFragment)
                    }, {
                        showMessage(it.errorMsg)
                    }
                )
            })
    }
}