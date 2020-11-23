package com.example.jetpackdemo.ui.login.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.FragmentRegisterBinding
import com.example.jetpackdemo.ext.initClose
import com.example.jetpackdemo.ui.base.BaseFragment
import com.example.jetpackdemo.ui.login.viewmodel.RegisterViewModel
import com.example.jetpackdemo.util.InjectorUtil
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.nav
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
        InjectorUtil.getRegisterViewModelFactory()
    }

    override fun layoutId(): Int {
        return R.layout.fragment_register
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.model = viewModel
        toolbar.initClose("注册") {
            nav().navigateUp()
        }
        btn_register.setOnClickListener{
            val username = et_account.text.toString()
            val password = et_pwd.text.toString()
            val repassword = et_repwd.text.toString()
            viewModel.register(username, password, repassword)
        }
    }
}