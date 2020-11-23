package com.example.jetpackdemo.ui.login.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.FragmentLoginBinding
import com.example.jetpackdemo.ext.initClose
import com.example.jetpackdemo.ui.login.viewmodel.LoginViewModel
import com.zzq.common.ext.nav
import com.zzq.common.ext.navigateAction
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private val loginModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //方式一
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater,R.layout.fragment_login,container,false)
        //方式二
        // val binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.model = loginModel
        binding.activity = activity
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.initClose("登录") {
            nav().navigateUp()
        }
        btn_go_register.setOnClickListener{
            nav().navigateAction(R.id.action_loginFragment_to_registerFrgment)
        }
    }
}